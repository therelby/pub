package above.allrun.helpers

import above.Run

class RunAuthorId {

    Run r = run()

    /**
     * Get author id
     */
    int xAuthorId(String login, boolean returnZeroForNotExisting = false) {

        login = login.trim()
        if (!login)
            throw new Exception('Login must be not empty string')

        if (r.xTestUsers() && r.xTestUsers()[login]) return r.xTestUsers()[login]

        r.dbInfoLoggingHide()
        List<Map> res = r.dbSelect("SELECT UserId FROM [user].[User] WHERE Username = '$login'", 'qa', true)
        r.dbInfoLoggingNormal()
        if (res == null)
            r.xStop('Can not access [QA_Automation].[user].[User] table')

        if (res.size() > 0)
            return res[0].UserId
        else
            if (!returnZeroForNotExisting)
                throw new Exception("Username [$login] must be present in [QA_Automation].[user].[User]")
            else
                return 0
    }

}
