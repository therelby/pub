package above.report

import all.DbTools

/**
 *      WebToolMonitor DB staff
 */

class WebToolMonitorDb {

    /** Tests for day */
    static getTests(Date day) {
        return DbTools.selectAll("""SELECT test_class FROM Stat_Test_Runs
                                        WHERE date_done >= '${day.minus(1).format('yyyy-MM-dd')} 07:00:00' AND
                                              date_done < '${day.format('yyyy-MM-dd')} 07:00:00' AND stats_involved = 1
                                    GROUP BY test_class ORDER BY test_class;""", 'qa').collect{it.test_class}.sort()
    }

    /** Tests count */
    static Integer getTestsCount(Date day, String and = '') {
        def res = DbTools.selectAll("""SELECT count(test_class) cnt FROM (
                                            SELECT test_class FROM Stat_Test_Runs
                                                WHERE date_done >= '${day.minus(1).format('yyyy-MM-dd')} 07:00:00' AND
                                                      date_done < '${day.format('yyyy-MM-dd')} 07:00:00' AND stats_involved = 1
                                                      $and
                                            GROUP BY test_class, browser) a;""", 'qa')
        if (res) {
            return res[0].cnt
        }
        return null
    }


    /** Exec time */
    static Integer getDayTotalExecutionTimeSeconds(Date day, String and) {
        def res = DbTools.selectAll("""SELECT sum(exec_time_sec) total_sec FROM Stat_Test_Runs
                                            WHERE date_done >= '${day.minus(1).format('yyyy-MM-dd')} 07:00:00' AND
                                                  date_done < '${day.format('yyyy-MM-dd')} 07:00:00' $and""", 'qa')
        if (res) {
            return res[0].total_sec
        }
        return null
    }


    /** Last test time */
    static Date getLastTestDateDone(Date day) {
        def res = DbTools.selectAll("""SELECT TOP 1 date_done FROM Stat_Test_Runs
                                           WHERE date_done >= '${day.minus(1).format('yyyy-MM-dd')} 07:00:00' AND
                                                 date_done < '${day.format('yyyy-MM-dd')} 07:00:00' AND stats_involved = 1
                                       ORDER BY id DESC;""", 'qa')
        if (res) {
            return res[0].date_done
        }
        return null
    }

    /** Concepts list */
    static getConcepts(Date day) {
        def res = DbTools.selectAll("""SELECT concept FROM Stat_Test_Runs
                                           WHERE concept <> '' AND concept <> 'none' AND concept <> 'base'
                                           GROUP BY concept ORDER BY concept;""", 'qa').collect{it.concept}
    }

    /** */
    static getDevOpsServers(Date day) {
        def res = DbTools.selectAll("""SELECT count(a.id), a.host FROM (
                                            SELECT id, JSON_VALUE(test_info, '\$.run.runHost') as host
                                            FROM [QA_Automation].[dbo].[Stat_Test_Runs]
                                            WHERE date_done >= '${day.minus(1).format('yyyy-MM-dd')} 07:00:00'  
                                                  AND date_done < '${day.format('yyyy-MM-dd')} 07:00:00'
                                                  AND concept = 'web'
                                            ) a
                                        WHERE host like 'l-cidocker%' 
                                        GROUP BY host
                                        ORDER BY host;""", 'qa').collect{it.host}
    }

}
