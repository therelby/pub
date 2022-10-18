package all.util

import above.RunWeb
import org.openqa.selenium.WebElement

/**
 *     Utility methods for Lists
 *      Refactored from Katalon common.CompareList.groovy
 * @kyilmaz @vdiachuk
 *
 */
class ListUtil {


    //check is List or Strings contains String
    synchronized static boolean isListOfStringsContainsString(List<String> list, String givenStr) {
        RunWeb r = run()
        try {
            for (String element : list) {
                if (element.contains(givenStr)) {
                    return true
                }
            }
        } catch (e) {
            r.log("Can not check is list of Strings contains given String:$e")
        }
        return false
    }

    //check is List or WebElemnts contains String(text of webelement)
    synchronized static boolean isListOfWebelementsContainsString(List<WebElement> list, String givenStr) {
        RunWeb r = run()
        try {
            for (String element : list) {
                if (element.getText().contains(givenStr)) {
                    return true
                }
            }
        } catch (Exception e) {
            r.log("Can not check is list of WebElements contains given String:$e")
        }
        return false
    }


    // Refactored method from Katalon common.Data
    // Any complex or simple data comparing
    synchronized static isEqual(value1, value2) {
        if (value1 instanceof List) {
            return sort(value1.flatten()) == sort(value2.flatten())
        } else {
            return sort(value1) == sort(value2)
        }
    }

    // All Lists sorting in any complex data
    // (!) recursion
    synchronized static sort(data, rev = false) {
        if (data instanceof List) {
            // sorting list
            if (!rev) {
                data.sort()
            } else {
                data.reverse()
            }
        } else if (data instanceof Map) {
            for (k in data.keySet()) {
                sort(data[k])
            }
        }
        return data
    }

    static getRandomElement(List data) {
        if (!data) {
            return null
        }
        def rng = new Random()
        return data[rng.nextInt(data.size())]
    }

    // Refactored method from Katalon common.CompareList.groovy
    // Returns a map of [l1 index of mismatch : [l1Item, l2Item] ]
    static <T> Map<Integer, List<T>> getMismatchedElements(List<T> l1, List<T> l2) {
        RunWeb r = run()
        def mismatchedResults = [:]
        if (l1.size() != l2.size()) {
            r.log("Sizes much match to use this method")
            mismatchedResults.put(Integer.MAX_VALUE, [null, null])
        }
        for (def i = 0; i < l1.size(); i++) {
            if (l1[i] != l2[i]) {
                mismatchedResults.put(i, [l1[i], l2[i]])
            }
        }
        return mismatchedResults
    }

    // Refactored method from Katalon common.CompareList.groovy
    static <T> List<T> getDifference(List<T> l1, List<T> l2) {
        RunWeb r = run()
        if (l1 == null) {
            r.log("l1 is null")
            return l2
        } else if (l2 == null) {
            r.log("l2 is null")
            return l1
        } else {
            return ((l1 - l2) + (l2 - l1))
        }
    }

    /**
     * @param l1 first list
     * @param l2 - second list
     * @param sort - defaults to false. If sort, it will sort the lists first
     * @param l1name - name of first list, defaults to "l1"
     * @param l2name - name of second list, defaults to "l2"
     * @param elementName - defaults to "element", but if you're working with items, use "item"
     * @param messagePrefix - use to add a prefix to the message
     * @return Map with keys [result (Boolean), message (String)]
     *
     */
    static Map checkListsMatch(l1, l2, sort = false, l1Name = 'list1', l2Name = 'list2',
                               elementName = 'element', messagePrefix = '') {
        RunWeb r = run()
        if (sort) {
            l1 = l1.sort()
            l2 = l2.sort()
        }
        def listsMatch = l1 == l2
        if (messagePrefix) {
            messagePrefix += '. '
        }
        String elementNameCap = elementName.substring(0, 1).toUpperCase() + elementName.substring(1)
        def message
        if (!listsMatch) {
            def difference = getDifference(l1, l2)
            r.log("Difference: " + difference)
            if (l1.size() == l2.size() && difference.isEmpty()) {
                message = messagePrefix + "${elementNameCap}s are out of order.\n${l1Name}:\n" +
                        l1.toPrettyString() + "\n${l2Name}\n:" + l2.toPrettyString()
            } else {
                def l1Unique = l1 - l2
                def l2Unique = l2 - l1
                def l1Extra = ''
                def l2Extra = ''
                if (l1Unique.size() > 10) {
                    l1Extra = "\n... and ${l1Unique.size() - 10} more not shown."
                    l1Unique = l1Unique.take(10)
                }
                if (l2Unique.size() > 10) {
                    l2Extra = "\n... and ${l2Unique.size() - 10} more not shown."
                    l2Unique = l2Unique.take(10)
                }
                message = messagePrefix + "${elementNameCap}s from ${l1Name} (${l1.size()}) did not match " +
                        "${l2Name} (${l2.size()}). " +
                        "Difference: " + difference.toPrettyString() +
                        "\nUnique to ${l1Name}:\n" + l1Unique.toPrettyString() + l1Extra +
                        "\nUnique to ${l2Name}:\n" + l2Unique.toPrettyString() + l2Extra
            }
        } else {
            message = "${elementNameCap} in ${l2Name} match ${l1Name}."
        }
        return [result: listsMatch, message: message]
    }


    /**
     * Getting elements from the list by parameter
     * @param List
     * @param parameter - "first", "last", "random", "all"
     * @return List
     */
    static List getElementsByParameter(List list, String parameter) {
        List allowedParameters = ["first",//return list with first element
                                  "last", //return list with last element
                                  "random", //return list with 1 random element
                                  "all"]//return list
        if(!allowedParameters.contains(parameter)) {
            throw new Exception("Can not get Elements by Parameter: $parameter, is not known value")
        }
        List result = []
        if(!list){
            return result
        }
        if (parameter == "first") {
            result.add(list.first())
        } else if (parameter == "last") {
            result.add(list.last())
        } else if (parameter == "random") {
            result << getRandomElement(list)
        } else if(parameter=="all"){
            result = list
        } else {
            throw new Exception("Parameter: $parameter, is not known value")
        }
        return result
    }
}
