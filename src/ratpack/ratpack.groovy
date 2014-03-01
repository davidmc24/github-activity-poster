import app.ActivityReportLoader
import app.AppModule
import app.DateRange
import ratpack.groovy.Groovy
import ratpack.thymeleaf.Template
import ratpack.thymeleaf.ThymeleafModule

final String MONTH_FORMAT = "yyyy-MM"

Groovy.ratpack {
    modules {
        register new ThymeleafModule()
        register new AppModule()
    }
    handlers {
        get { ActivityReportLoader reportLoader ->
            def month = request.queryParams["month"] ?: new Date().format(MONTH_FORMAT)
            def dateRange = DateRange.forMonthIncluding(Date.parse(MONTH_FORMAT, month))
            def report = reportLoader.get(dateRange)
            render Template.thymeleafTemplate("index", report: report, month: month)
        }
        
        assets "public"
    }
}
