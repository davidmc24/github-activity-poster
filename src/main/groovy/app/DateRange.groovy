package app

import groovy.time.TimeCategory
import groovy.transform.Canonical

@Canonical
class DateRange {
    Date startDate
    Date endDate

    static DateRange forMonthIncluding(Date date) {
        def startDate = startOfMonth(date)
        return new DateRange(startDate, nextMonth(startDate))
    }

    private static Date startOfMonth(Date input) {
        def cal = input.toCalendar()
        cal.clearTime()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        return cal.time
    }

    private static Date nextMonth(Date input) {
        use (TimeCategory) {
            return input + 1.month
        }
    }
}
