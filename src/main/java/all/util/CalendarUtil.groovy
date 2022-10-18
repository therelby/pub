package all.util
import java.time.DayOfWeek

/**
 * Created by @mwestacott
 *
 * Utility methods pertaining to the calendar
 *
 */
class CalendarUtil {

    static boolean isNonBusinessDay(Date date, boolean isCommercial)
    {
        return (isStandardNonBusinessDay(date, isCommercial)
                || isNewYearsDay(date)
                || isMemorialDay(date)
                || isFourthOfJuly(date)
                || isLaborDay(date)
                || isThanksgiving(date)
                || isChristmas(date))
    }

    static boolean isStandardNonBusinessDay(Date date, boolean isCommercial)
    {
        DayOfWeek dayOfWeek = date.toDayOfWeek()
        return ((
                    isCommercial && dayOfWeek in [DayOfWeek.SATURDAY, DayOfWeek.SUNDAY]
               ) 
            ||
               (
                   !isCommercial && dayOfWeek in [DayOfWeek.SUNDAY, DayOfWeek.MONDAY]
               ));
    }

    static boolean isMemorialDay(Date date)
    {
        return (date.toMonth() == 5
                && date.toDayOfWeek() == DayOfWeek.MONDAY
                && date.toMonthDay() >= 25)
    }

    static boolean isFourthOfJuly(Date date)
    {
        return (date.toMonth() == 7
                && date.toMonthDay() == 4)
    }

    static boolean isLaborDay(Date date)
    {
        return (date.toMonth() == 9
                && date.toDayOfWeek() == DayOfWeek.MONDAY
                && date.toMonthDay() <= 7)
    }

    static boolean isThanksgiving(Date date)
    {
        return (date.toMonth() == 11
                && date.toDayOfWeek() == DayOfWeek.THURSDAY
                && date.toMonthDay() >= 22)
    }

    static boolean isChristmas(Date date)
    {
        return (date.toMonth() == 12
                && date.toMonthDay() == 25)
    }

    static boolean isNewYearsDay(Date date)
    {
        return date.toLocalDate() == 1;
    }

    static Date addBusinessDays(int daysToAdd, Date date, boolean isCommercial)
    {
        Date pendingDate
        while(isNonBusinessDay(date.plus(daysToAdd), isCommercial)){
            daysToAdd++;
        }
        pendingDate = date.plus(daysToAdd)

        return pendingDate;
    }

    static Date shippingCalculatorAddBusinessDays(int daysToAdd, Date date, boolean isCommercial)
    {
        def step = daysToAdd < 0 ? -1 : 1;
        while (true)
        {
            if (isNonBusinessDay(date, isCommercial))
            {
                date = date.plus(step);
            }
            else if (daysToAdd-- < 1)
            {
                break;
            }
            else
            {
                date = date.plus(step);
            }
        }
        return date;
    }
}

