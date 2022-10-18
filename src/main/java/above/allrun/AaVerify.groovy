package above.allrun

import above.allrun.helpers.RunAuthorId
import above.allrun.helpers.StorageScreenshots
import above.types.IssueCategory
import all.Json

abstract class AaVerify extends AbRunIssueCheck {

    private boolean lastCheckTitleLinkedObsolete = false
    private final int verifyParamsTitleLength = 120

    /**
     * Test point handling
     *
     * -- please use Ctrl+J in IntelliJ and select one of the verify() templates for quick inserting with UUID generated
     *
     * @param result: true - passed, false - failed
     *
     * @param params: Map like [
     *      id: 'String UUID value hardcoded',  // mandatory - unique string UUID
     *                                                         (!) must be stable and never change
     *      title: 'What we are verifying',     // mandatory - short test point goal explanation,80 symbols max
     *
     *      PBIs: [123, 456, ...]               // OPTIONAL  - list of PBI ids for this particular test point
     *                                                         when the ids different to provided
     *                                                         in the setup() call for this test
     *      author: 'YOUR_LOGIN',               // OPTIONAL  - provide when this test point added by different
     *                                                         author to provided in the setup() call
     *      details: Map like [                 // mandatory - all variables/values related to the test point
     *          varName1: varName1,                            the variables should explain the test point condition
     *          varName2: varName2,                            confirming there is no issue or for the issue reproducing
     *          ...                                            also it should not contain any extra data
     *      ]
     * ]
     *
     * @return - result as is
     */
    boolean verify(boolean result, Map params, IssueCategory issue = null) {
        return xVerify(result, params, issue, false)
    }


    /** Framework Use Only */
    boolean xVerifyReport(boolean result, Map params, IssueCategory issue = null) {
        params.title = 'Framework - ' + params.title
        if (params.title.length() > verifyParamsTitleLength)
            params.title = params.title.substring(0, verifyParamsTitleLength - 3) + '...'
        params.id = UUID.randomUUID().toString()
        return xVerify(result, params, issue, false, true)
    }


    /**
     * The verify() calls should not be removed but replaced with obsoleteVerify() calls
     * -- in case we need to 'remove' a verify() call from an old test class
     */
    boolean obsoleteVerify(boolean result, Map params, IssueCategory issue = null) {
        return xVerify(result, params, issue, true)
    }


    /** Real verify() */
    private boolean xVerify(boolean result,
                            Map params,
                            IssueCategory issue = null,
                            boolean obsolete = false,
                            boolean isFramework = false) {
        runVerifyBased = true

        // setup called?
        if (!testAuthor)
            throw new Exception('setup() must be called before any framework stuff usage')

        // checking the params
        xVerifyParams(params)

        // checking blocking issue
        if (issue == IssueCategory.NOT_PROVIDED)
            throw new Exception('The blockingIssue param must be one of: not provided/null; ' +
                    'or defined a value except BlockingIssue.NOT_PROVIDED')
        if (issue == null)
            issue = IssueCategory.NOT_PROVIDED

        // printing console log
        log '--'
        if (result && !obsolete) {
            log "PASSED Verify: ${params.title}", console_color_green
            if (verifyAlwaysPrintDetails)
                log params.details, console_color_green
        } else if (!obsolete) {
            log "FAILED Verify: ${params.title}", console_color_red
            if (issue != IssueCategory.NOT_PROVIDED)
                log "IssueCategory: $issue", console_color_yellow
            if (params.author)
                log "Author: ${params.author}", console_color_yellow
            if (params.authors)
                log "Authors: ${params.authors}", console_color_yellow
            if (params.PBIs)
                log "PBIs:   ${params.PBIs.join(', ')}", console_color_yellow
            log params.details, console_color_yellow
        } else {
            log "OBSOLETE Verify: ${params.title}", console_color_yellow
        }

        // storing old style stats
        if (!obsolete) {
            if (!result && issue != IssueCategory.NOT_PROVIDED)
                runVerifyStats.reports += 1
            if (result)
                runVerifyStats.passed += 1
            else
                runVerifyStats.failed += 1
        }

        // saving results
        if (!isRunDebug()) {
            dbInfoLoggingHide()
            if (obsolete) {
                xObsoleteVerify(params)
            } else {
                String screenshot = null
                if (!result && xTestConcept() == 'web' && driverStorage.get() && !isAlertPresentRunStuffOnly())
                    screenshot = takeScreenshot()
                if (!xVerifySave(result, params, screenshot, issue.value, isFramework)) {
                    dbInfoLoggingNormal()
                    xStop('verify(): Cannot save test point statistics')
                }
            }
            dbInfoLoggingNormal()
        }

        // returning the result as is
        return result
    }


    /** Obsolete verify() handling */
    private void xObsoleteVerify(Map params) {
        // getting linked title id
        int testPointId = xTryTestPointId(params, true, false)
        if (testPointId == 0)
            throw new Exception("obsoleteVerify() UNKNOWN TEST POINT: params.id must be already present in our statistics")
        // needs to be updated?
        if (!lastCheckTitleLinkedObsolete)
            dbExecute("UPDATE [statistic].[VerifyTestPoint] SET obsolete = 1, date_obsolete_changed = getdate() " +
                    "WHERE id = $testPointId", 'qa')
    }


    /** verify() Statistics Save */
    private boolean xVerifySave(boolean result, Map params, String screenshot, int issueCategoryId, boolean isFramework) {
        // getting linked title id
        int testPointId = xTryTestPointId(params, false)
        if (!testPointId) return false

        // saving screenshot
        long screenshotId = 0
        if (screenshot) screenshotId = new StorageScreenshots().save(screenshot)
        if (screenshotId) log "Screenshot: ${StorageScreenshots.getDevImageUrl(screenshotId)}"

        // saving current url
        String url = ''
        if (xTestConcept() == 'web' && !isFramework && driverStorage.get() && !isAlertPresentRunStuffOnly()) {
            url = getCurrentUrl()
            if (!(url instanceof String) || !url.startsWith('http'))
                url = ''
        }

        // saving verify
        int verifyId = dbInsert("INSERT INTO [statistic].[Verify] (run_id, screenshot_id, passed, test_point_id, details, url, issue_code, is_framework) " +
                "VALUES ($runRecordId, $screenshotId, ${result ? 1 : 0}, $testPointId, " +
                "'${params.details.toPrettyString().replace("'", "''")}', '${url.replace("'", "''")}', $issueCategoryId, ${isFramework ? 1 : 0})", 'qa')
        if (verifyId == 0) return false

        // saving old style details - fails only
        // TODO: remove when all new stuff is ready
        if (!result && !dbInsert("""INSERT INTO Stat_Result_Details 
            (run_record_id, 
             tfs_item_id, 
             tfs_project, 
             is_check, 
             result, 
             time, 
             description, 
             url, 
             screenshot, 
             details,
             smoke_test_id,
             check_id,
             uuid,
             verify,
             issue_category_id) 
            VALUES 
            (${runRecordId}, 
             ${testCases[0]?.tcId ?: 0}, 
             '${testTfsProject}', 
             1, 
             0, 
             ${getRealTimeStamp()}, 
             '${params.title.replace("'", "''")}', 
             '$url', 
             $screenshotId, 
             '${Json.getJson(params.details).replace("'", "''").replaceAll("\\p{Cntrl}", "")}',
             0,
             '',
             '${UUID.randomUUID().toString()}',
             1,
             $issueCategoryId);""", 'qa')) {
            return false
        }

        // adding and updating the test point PBIs
        List<Integer> pbis = []
        if (params.PBIs)
            pbis = params.PBIs
        else
            pbis = xTestBPIs()
        // updating removed PBIs
        dbExecute("UPDATE [statistic].[VerifyTestPointPBI] SET date_removed = getdate() " +
                "WHERE test_point_id = $testPointId AND date_removed IS NULL " +
                "AND pbi_id <> ${pbis.join(' AND pbi_id <> ')}", 'qa')
        // restoring from removed PBIs
        dbExecute("UPDATE [statistic].[VerifyTestPointPBI] SET date_removed = null WHERE test_point_id = $testPointId " +
                "AND (pbi_id = ${pbis.join(' OR pbi_id = ')})", 'qa')
        // adding the test point PBIs -- ignoring issues
        for (pbi in pbis)
            dbInsertIgnore("INSERT INTO [statistic].[VerifyTestPointPBI] (test_point_id, pbi_id) " +
                    "VALUES ($testPointId, $pbi)", 'qa')

        return true
    }


    /**
     * Try to Get Test Point ID
     * -- avoiding database conflicts in parallel
     */
    private int xTryTestPointId(Map params, boolean obsolete = false, boolean createNew = true) {
        int attempts = 3
        int tpId = 0
        while (attempts > 0) {
            attempts--
            tpId = xTestPointId(params, obsolete, createNew)
            if (tpId) break
            if (attempts > 0) sleep(Math.abs(new Random().nextInt() % 3000) + 1)
        }
        return tpId
    }
    /** Get Test Point ID */
    private int xTestPointId(Map params, boolean obsolete = false, boolean createNew = true) {

        // checking test point id
        int testPointId
        List<Map> res = dbSelectSafe("SELECT id, title_id, obsolete FROM [statistic].[VerifyTestPoint] " +
                "WHERE uuid = '${params.id}' AND class_id = ${testClassId}", 'qa')
        if (res == null) return 0

        int titleId
        if (res.size() == 0) {
            // NEW test point
            if (!createNew) return 0

            // checking title id
            titleId = xTryTitleId(params.title)
            if (titleId == 0) return 0

            // checking call source
            int isHelper = 0
            String path = xCalledMethod('AaVerify$verify')
            if (testClass != path[0..path.lastIndexOf('.')-1]) isHelper = 1

            // creating test point
            testPointId = dbInsert("INSERT INTO [statistic].[VerifyTestPoint] (title_id, uuid, class_id, is_helper) VALUES " +
                    "($titleId, '${params.id}', $testClassId, $isHelper)", 'qa')
            if (testPointId == 0) return 0

        } else {
            // EXISTING test point
            titleId = res[0].title_id

            testPointId = res[0].id
            lastCheckTitleLinkedObsolete = res[0].obsolete
            // obsolete back to normal
            if (!obsolete && lastCheckTitleLinkedObsolete)
                dbExecute("UPDATE [statistic].[VerifyTestPoint] SET obsolete = 0, date_obsolete_changed = getdate() " +
                        "WHERE title_id = $titleId", 'qa')

            // title renamed?
            int titleNewId = xTryTitleId(params.title)
            if (titleId == 0) return 0
            if (titleNewId != titleId)
                dbUpdate("UPDATE [statistic].[VerifyTestPoint] SET title_id = $titleNewId WHERE id = $testPointId", 'qa')
        }

        // author(s)
        List<Integer> authorIds = []
        if (params.author)
            authorIds << new RunAuthorId().xAuthorId(params.author)
        else if (params.authors)
            params.authors.each { authorIds << new RunAuthorId().xAuthorId(it) }
        else
            authorIds << testAuthorId

        for (authorId in authorIds.reverse()) {
            res = dbSelectSafe("SELECT id, author_id, is_current FROM [statistic].[VerifyTestPointAuthor] " +
                    "WHERE test_point_id = $testPointId ORDER BY id DESC", 'qa')
            if (res == null) return 0

            if (res.size() > 0 && res[0].author_id != authorId)
                if (!dbUpdate("UPDATE [statistic].[VerifyTestPointAuthor] SET is_current = 0 " +
                        "WHERE test_point_id = $testPointId", 'qa'))
                    return 0

            if (res.size() == 0 || res[0].author_id != authorId)
                if (!dbInsert("INSERT INTO [statistic].[VerifyTestPointAuthor] (author_id, test_point_id) " +
                        "VALUES ($authorId, $testPointId)", 'qa'))
                    return 0
        }

        return testPointId
    }


    /**
     * Try to Get Title ID
     * -- avoiding database conflicts in parallel
     */
    private int xTryTitleId(String title) {
        int attempts = 3
        int tId = 0
        while (attempts > 0) {
            attempts--
            tId = xTitleId(title)
            if (tId) break
            if (attempts > 0) sleep(Math.abs(new Random().nextInt() % 3000) + 1)
        }
        return tId
    }
    /** Getting title id for String title */
    private int xTitleId(String title) {
        int titleId
        List<Map> ttl = dbSelectSafe("SELECT id FROM [statistic].[VerifyTestPointTitle] WHERE " +
                "title = '${title.replace("'", "''")}'", 'qa')
        if (ttl == null) return 0
        if (ttl.size() == 0) {
            // new title
            titleId = dbInsert("INSERT INTO [statistic].[VerifyTestPointTitle] (title) VALUES " +
                    "('${title.replace("'", "''")}')", 'qa')
            if (titleId == 0) return 0
        } else {
            // existing title
            titleId = ttl[0].id
        }
        return titleId
    }


    /** verify() params check */
    private void xVerifyParams(Map params) {
        // params
        if (!(params instanceof Map) || params.size() == 0)
            throw new Exception('verify() params must be not empty Map')

        // uuid
        if (params.id && params.id instanceof String)
            params.id = params.id.trim()
        if (!params.id || !(params.id instanceof String) || params.id.size() != 36)
            throw new Exception('verify() params.id must be UUID string value')

        // params.title
        if (params.title && params.title instanceof String)
            params.title = params.title.trim()
        if (!params.title || !(params.title instanceof String) || params.title.size() > verifyParamsTitleLength)
            throw new Exception("verify() params.title must be not empty String with up to $verifyParamsTitleLength symbols")

        // params.author
        if ((params.author && !(params.author instanceof String) || params.author == ''))
            throw new Exception('verify() params.author must be not empty String')

        // params.authors
        if (params.authors && !(params.authors instanceof List<String>))
            throw new Exception('verify() params.authors must be not empty List<String>')

        // params.PBIs
        if (params.PBIs && !(params.PBIs instanceof List<Integer>))
            throw new Exception('verify() params.PBIs must be List<Integer> and the id(s) provided must be existing PBI id(s)')
        if (testAuthor && params.PBIs && params.PBIs.contains(testCases[0].tcId))
            throw new Exception("The test PBI id [${testCases[0].tcId}] should not be duplicated in a test point PBIs list")

        // params.details
        if (!params.details || !(params.details instanceof Map))
            throw new Exception('verify() params.details must be not empty Map')
    }

}
