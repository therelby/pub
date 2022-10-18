package all.util

import above.RunWeb

/**
 * Utility methods for Double
 * jreumont
 */
class StateInfoUtil {

    RunWeb r = run()

    /**
     * @input Accepts a state name (i.e. 'Maryland'
     * @return Returns a state code (i.e. 'MD' for Maryland)
     */
    String getStateCode(def stateName) {
        def stateCodesQuery = """
        USE [Clarkitbiz]
        
        Select state_province as StateCode
        From dbo.tblStateProvince Where
        extended = '$stateName'"""

        def results = r.dbSelect(stateCodesQuery, "wss-ro")
        if (results.isEmpty()) {
            r.log("Could not get state code for $stateName")
            return ""
        }
        results = results.get(0)
        return results.get("StateCode")
    }

    /**
     * @input Accepts a state code (i.e. 'MD'
     * @return Returns a state name (i.e. 'Maryland' for MD)
     */
    String getStateName(def stateCode) {
        def stateCodesQuery = """
        USE [Clarkitbiz]

        Select extended as StateName
        From dbo.tblStateProvince Where
        state_province = '$stateCode'"""

        def results = r.dbSelect(stateCodesQuery, "wss-ro")
        if (results.isEmpty()) {
            r.log("Could not get state name for $stateCode")
            return ""
        }
        results = results.get(0)
        return results.get("StateName")
    }
}
