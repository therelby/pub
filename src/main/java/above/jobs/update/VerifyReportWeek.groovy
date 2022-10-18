package above.jobs.update

import above.Run

class VerifyReportWeek extends Run {

    static void main(String[] args) {
        new VerifyReportWeek().testExecute()
    }

    void test() {

        setup([ author: 'akudin', title: 'Verify Weekly Report data preparation' ])

        dbExecute("""
                    DROP TABLE IF EXISTS ##VerifyReportWeekNewData;

                    set DATEFIRST 1;

                    SELECT date_added, author_id, count(date_added) total_tps 
                    INTO ##VerifyReportWeekNewData
                    FROM (
                      SELECT DATEADD(DD, 1 - DATEPART(DW, CONVERT(date, tp.date_added)), CONVERT(date, tp.date_added)) date_added, a.author_id,
                          ROW_NUMBER() OVER (PARTITION BY tp.uuid ORDER BY tp.id DESC) as rn
                      FROM statistic.VerifyTestPoint tp
                        INNER JOIN statistic.VerifyTestPointAuthor a
                          ON tp.id = a.test_point_id
                        INNER JOIN statistic.Verify v
                          ON v.test_point_id = tp.id
                      WHERE v.is_framework = 0
                      ) r
                    WHERE rn = 1
                    GROUP BY date_added, author_id;

                    TRUNCATE TABLE [statistic].[VerifyReportWeek];

                    INSERT INTO [statistic].[VerifyReportWeek] 
                    SELECT * FROM ##VerifyReportWeekNewData;

                    DROP TABLE IF EXISTS ##VerifyReportWeekNewData;
        """, 'qa')

    }

}
