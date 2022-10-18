package above.allrun.helpers

import above.Run

class RunAuthors {

    Run r = run()
    List<Map> currentUsers
    final String tableTestUser = '[statistic].[TestUser]'

    /** Update the authors list */
    boolean xAuthorListUpdate(List<String> authors) {

        // checking list
        if (!authors) throw new Exception('setup() author list can not be empty')
        if (authors.findAll{it == ''}.size() > 0)
            throw new Exception('setup() author login can not be an empty string')

        r.xSetRunLastIssue('UNKNOWN')

        // checking for duplicates
        List dups = authors.countBy{it}.grep{it.value > 1}.collect{it.key}
        if (dups)
            return r.xSetRunLastIssue("setup() authors should not be dublicated: $dups")

        // set current owner author
        r.testAuthor = authors[0]

        // Do not make any updates for Debug runs
        if (r.isRunDebug()) return true

        // ------------------------------------------------------------------------
        // ALL THE NEXT ACTIONS FOR REGULAR RUNS ONLY

        // Getting author IDs
        RunAuthorId aid = new RunAuthorId()
        List<Integer> aIds = []
        for (author in authors) {
            int id = aid.xAuthorId(author, true)
            if (!id) throw new Exception("Could not get author id for [$author]. Please correct the login and try again")
            aIds << id
        }

        // hide db queries
        r.dbInfoLoggingHide()

        // check/update the owner
        if (!trySetupOwner(aIds[0], authors)) {
            r.dbInfoLoggingNormal()
            return false
        }

        // check/update contributors
        if (!trySetupContributors(aIds)) {
            r.dbInfoLoggingNormal()
            return false
        }

        // back db queries printing mode to normal
        r.dbInfoLoggingNormal()

        // set current owner author id
        r.testAuthorId = aIds[0]

        // saving test users
        Map users = [:]
        for (int i = 0; i < authors.size(); i++) users.put(authors[i], aIds[i])
        r.xSetTestUsers(users)

        return true
    }


    /** Setup Class Contributors */
    private boolean trySetupContributors(List<Integer> aIds) {
        int attempts = 3
        boolean result = false
        while (attempts > 0) {
            attempts--
            result = setupContributors(aIds)
            if (result) return true
            if (attempts > 0) sleep(Math.abs(new Random().nextInt() % 3000) + 1)
        }
        return result
    }
    private boolean setupContributors(List<Integer> aIds) {

        // no contributors
        if (aIds.size() < 2) return true

        // adding contributors
        for (int i = 1; i < aIds.size(); i++) {
            Map alreadyPresent = currentUsers.find { it.user_id == aIds[i] }
            if (!alreadyPresent) {
                if (!r.dbInsert("INSERT INTO [statistic].[TestUser] " +
                        "(class_id, user_id, is_original, is_current) VALUES " +
                        "(${r.testClassId}, ${aIds[i]}, 0, 0)", 'qa')) {
                    return r.xSetRunLastIssue('Can not add a contributor user for the class')
                }
            }
        }

        return true
    }


    /** Setup Class Owner */
    private boolean trySetupOwner(int aId, List<String> authors) {
        int attempts = 3
        boolean result = false
        while (attempts > 0) {
            attempts--
            result = setupOwner(aId, authors)
            if (result) return true
            if (attempts > 0) sleep(Math.abs(new Random().nextInt() % 3000) + 1)
        }
        return result
    }
    private boolean setupOwner(int userId, List<String> authors) {

        // getting user list for the class -- current user is going to be first one in the list
        currentUsers = r.dbSelectSafe("SELECT id, user_id, is_current FROM [statistic].[TestUser] " +
                "WHERE class_id = ${r.testClassId} ORDER BY is_current DESC", 'qa')
        if (currentUsers == null) {
            return r.xSetRunLastIssue('Can not check the class owner')
        }

        if (currentUsers) {  // user(s) present

            // set current
            int currentUserIdx = currentUsers.findIndexOf{it.user_id == userId }
            if (currentUserIdx < 0) {

                // adding current owner
                if (!r.dbInsertData(tableTestUser,
                            [ class_id: r.testClassId, user_id: userId, is_original: 0, is_current: 1], 'qa')) {
                    return r.xSetRunLastIssue('Can not add current owner user for the class')
                }

            } else {

                // updating current owner
                if (currentUsers.find {  it.user_id == userId && !it.is_current })
                    if (!r.dbUpdateDataAffected(tableTestUser,
                            "class_id = ${r.testClassId} and user_id = $userId",
                            [ is_current: 1, date_current_changed: '=getdate()' ], 'qa')) {
                        return r.xSetRunLastIssue('Can not update new current owner user for the class')
                    }

            }

            // updating old owner(s) if present
            if (currentUsers.find {  it.user_id != userId && it.is_current })
                r.dbUpdateDataAffected(tableTestUser,
                        "class_id = ${r.testClassId} and user_id <> $userId and is_current = 1",
                        [ is_current: 0, date_current_changed: '=getdate()' ], 'qa')

        } else {  // no users for the class

            // adding original owner
            if (!r.dbInsertData(tableTestUser,
                    [ class_id: r.testClassId, user_id: userId, is_original: 1, is_current: 1], 'qa')) {
                return r.xSetRunLastIssue('Can not add original owner user for the class')
            }

        }

        return xUpdateClassCurrentOwner(authors[0], userId)
    }


    /**
     * COMPATIBILITY: Update the author column for ServerRuns_Classes to current class owner
     * -- gets WebTool working correctly with Server Runs stuff
     */
    protected boolean xUpdateClassCurrentOwner(String author, int autorId) {
        if (!r.dbUpdateData('ServerRuns_Classes', "id = ${r.testClassId}", [ author: author, author_id: autorId ], 'qa')) {
            return r.xSetRunLastIssue('Can not update current owner in ServerRuns_Classes for the class')
        }
        return true
    }

}
