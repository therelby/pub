package above.web

import above.RunWeb

/**
 *      URL Stuff to handle URL parmeters
 * @author akudin vdiachuk
 */
class FuncUrlParam {

    RunWeb r = run()


    // Read first param occurrence value from URL
    // returns '' if there is no defined param name
    String getParamValueFromUrl(String url, String paramName) {
        try {
            if (!url.contains('?')) {
                return ''
            }
            def params = url.split('\\?')
            if (params.size() < 2) {
                return ''
            }
            def value = params[1].split(paramName + '=')
            if (value.size() < 2) {
                return ''
            }
            return value[1].split('\\&')[0]
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get Parameter Value from Url - Exception", e)
            return ''
        }
    }

    // getting all params from url and return it as a List of Maps
    // in no parameters returns []
    // ex. url https://www.dev.webstaurantstore.com/search/table.html?category=42715&filter=fuel-type:liquid-propane:natural-gas&multi=true&filter=power-type:liquid-propane&filter=width:43-inches:55-inches:67-inches&order=rating_desc
    // return   [category=42715, filter=fuel-type:liquid-propane:natural-gas, multi=true, filter=power-type:liquid-propane, filter=width:43-inches:55-inches:67-inches, order=rating_desc]
    List getAllParamsFromUrl(String url) {
        r.logDebug "Getting all parameters from url: $url"
        List params = []
        if (!url || !url.contains("?")) {
            return params
        }
        params = url.split("[?&]")
        // 1st element in the list- main part of the url. Need to be removed
        if (params.size() < 2) {
            return []
        }
        params.remove(0)
        return params
    }
    // Add param to URL - can be 2 params with same name (for filter)
    // ex.  addParamToUrl(url,'category=42715')
    // does not check if param already exists in url)
    String addParamToUrl(String url, String param) {
        if (!url) {
            r.addIssueTrackerEvent("Can not add Parameter: $param, to url: [$url]")
            return null
        }
        if (url.contains('?')) {
            return "$url&$param"
        } else {
            return "$url?$param"
        }
    }

    // Remove all occurrences of parameter name from Url (filter parameter can be used many times)
    //
    String removeParamFromUrl(String url, String paramName) {
        if (!url) {
            r.addIssueTrackerEvent("Can not remove Parameter: $paramName, from url: [$url]")
            return null
        }
        if (!url.contains('?')) {
            r.log "url: $url, does not contains any parameters."
            return url
        }
        if (!paramName) {
            r.log "Can not remove parameter: $paramName, from url: $url"
            return url
        }
        List parameters = getAllParamsFromUrl(url)
        String urlUpdated = removeAllParamsFromUrl(url)
        List parametersUpdated = parameters.findAll() { parameter -> r.getParamNameFromUrlParam(parameter) != paramName }
        return addAllParamsToUrl(urlUpdated, parametersUpdated)
    }

    //
    // Change first occurrence of parameter in url(can be many occurrences of filter)
    //
    String changeParamValueToUrl(String url, String paramName, String paramValue) {
        r.logDebug "Change first occurrence parameter: $paramName, to $paramValue in url: $url"
        if (!url) {
            r.addIssueTrackerEvent("Can not change Parameter: $paramName, $paramValue, from url: [$url]")
            return null
        }
        if (url.contains('?')) {
            String updatedUrl = r.removeAllParamsFromUrl(url)
            List parameters = r.getAllParamsFromUrl(url)
            def parametersUpdated = []
            for (def parameter in parameters) {
                if (paramName && r.getParamNameFromUrlParam(parameter) == paramName) {
                    parametersUpdated.add("$paramName=$paramValue")
                    paramName = null
                } else {
                    parametersUpdated.add(parameter)
                }
            }
            updatedUrl = r.addAllParamsToUrl(updatedUrl, parametersUpdated)
            return updatedUrl
        } else {
            r.logDebug "Url contains no parameters: $url"
            return url
        }
    }

    // if parameter does not exists - Add it, if exists - Change first occurrence of parameter in url(can be many
    // occurrences of filter)
    //
    String changeOrAddParamToUrl(String url, String paramName, String paramValue) {
        r.logDebug "Change Or Add parameter: $paramName, to $paramValue in url: $url"
        if (!url) {
            r.addIssueTrackerEvent("Can not Change or Add Parameter: $paramName, $paramValue, from url: [$url]")
            return null
        }
        List parameters = r.getAllParamsFromUrl(url)
        if (parameters.any { parameter -> parameter.contains(paramName) }) {
            return changeParamValueToUrl(url, paramName, paramValue)
        } else {
            return addParamToUrl(url, paramName + "=" + paramValue)
        }
    }
    // Add params to URL from list
    // addAllParamsToUrl(url, ['category=42715','order=relevancy_desc'])
    String addAllParamsToUrl(String url, List params) {
        r.logDebug "Adding parameters: $params, to url: $url"
        if (!url) {
            r.addIssueTrackerEvent("Can not add All Parameters: $params, from url: [$url]")
            return null
        }
        //check if no parameters in params Map
        if (!params) {
            r.log "NO parameter in parameter map: $params, return same url.."
            return url
        }
        String paramStr = params.inject('') { result, element ->
            result + URLDecoder.decode(element) + "&"
        }
        if (paramStr[-1] == "&") {
            paramStr = paramStr.substring(0, paramStr.size() - 1)
        }
        if (url.contains('?')) {
            return "$url&$paramStr"
        } else {
            return "$url?$paramStr"
        }
    }

    // remove all parameters from URL
    String removeAllParamsFromUrl(String url) {
        if (!url) {
            r.addIssueTrackerEvent("Can not remove All Parameters from url: [$url]")
            return null
        }
        r.logDebug "Removing all parameters from: $url"
        if (url && url.contains('?')) {
            return url.substring(0, url.indexOf('?'))
        } else {
            return url
        }
    }


    // get parameter value from url parameter
    // ex. getParamValue('order=price_asc') returns 'price_asc'
    String getParamValueFromUrlParam(String urlParameter) {
        if (!urlParameter) {
            r.addIssueTrackerEvent("Can not value from Url Parameter: $urlParameter")
            return null
        }
        def value = urlParameter.split('=')
        if (value.size() < 2 || !value[1]) {
            return ''
        } else {
            return value[1]
        }
    }


    // get parameter name from url parameter
    // ex. getParamValue('order=price_asc') returns 'order'
    String getParamNameFromUrlParam(String urlParameter) {
        def value = urlParameter.split('=')
        return value[0]
    }
}