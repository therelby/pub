package framework.all.util

import above.RunWeb
import all.util.CalendarUtil

import java.time.Duration

class UtCalendarUtilTest extends RunWeb{

    static void main(String[] args) {
        new UtCalendarUtilTest().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
//                browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {

        setup('mwestacott', 'Calendar Util Unit Test',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test calendar util',
                 "tfsTcIds:0", 'logLevel:info'])

        Date startDate = new Date()
        Date newDate = CalendarUtil.addBusinessDays(2, startDate, true)
        //long daysBetweenDates = Duration.between(startDate, newDate).toDays()
        def daysBetweenDates = startDate.minus(newDate)
        assert daysBetweenDates <= -2

        def firstDay = CalendarUtil.shippingCalculatorAddBusinessDays(3, startDate, true);
        def lastDay = CalendarUtil.shippingCalculatorAddBusinessDays(2, firstDay, true);

        println(firstDay)
        println(lastDay)
    }
}
