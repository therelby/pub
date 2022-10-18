package above.types

import above.Run

/**
 *      Issue Categories for the preparation verify() calls
 *      -- the numbers must be stable because they involved in our statistics
 *      -- (!) do not change the class without the team manager or a framework person agreement
 */

enum IssueCategory {
    /**
     * WebDriver creating fails and other issues; mostly framework usage
     */
    BROWSER (101),

    /**
     * URL fails to load unexpectedly
     */
    PAGE_LOADING (201),
    /**
     * page element not present or not reachable
     */
    PAGE_ELEMENT (202),
    /**
     * other page related issues like with alerts, cookies, etc. that are not allowing to continue the test
     */
    PAGE_OTHER (299),

    /**
     * database connection fails; mostly framework usage
     */
    DB_CONNECTION (301),
    /**
     * SQL query execution fails with the correct query
     */
    DB_EXECUTION (302),
    /**
     * other database related fails that are not allowing to continue the test
     */
    DB_OTHER (399),

    /**
     * API response code != 200
     */
    API_CONNECTION (401),
    /**
     * empty/null/wrong API data came
     */
    API_DATA (402),
    /**
     * other API related issues that are not allowing to continue the test
     */
    API_OTHER (499),

    /**
     * product user login failed or working unexpectedly
     */
    PRODUCT_USER (501),
    /**
     * needed product item does not exist or wrong product item data found
     */
    PRODUCT_ITEM (502),
    /**
     * other product related issues that are not allowing to continue the test
     */
    PRODUCT_OTHER (599),

    /**
     * DO NOT USE!! - framework usage only
     */
    EMERGENCY_TEST_STOP (601),

    /**
     * DO NOT USE!! - framework usage only
     */
    NOT_PROVIDED (0)

    int code

    IssueCategory(int value) {
        code = value
    }

    int getValue() {
        return code
    }


    /** Sync with the DB table StatsIssueCategories */
    synchronized static void updateDatabase() {
        Run r = run()
        List<IssueCategory> data = IssueCategory.values()

        // removing renamed for the db table
        String where = ''
        for (item in data)
            where += " OR (id = ${item.value} AND name <> '${item.name()}') OR (id <> ${item.value} AND name = '${item.name()}')"
        r.dbExecute("DELETE FROM [statistic].[IssueCategory] WHERE ${where.replaceFirst(' OR ', '')}", 'qa')

        // adding new to the db table
        data.each {
            r.dbInsertIgnore("INSERT INTO [statistic].[IssueCategory] (id, name) VALUES (${it.value}, '${it.name()}')", 'qa')
        }
    }

}
