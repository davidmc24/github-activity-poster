import app.ActivityReportLoader
import app.AppModule
import app.DateRange
import ratpack.groovy.Groovy
import ratpack.thymeleaf.Template
import ratpack.thymeleaf.ThymeleafModule

Groovy.ratpack {
    modules {
        register new ThymeleafModule()
        register new AppModule()
    }
    handlers {
        get {
            // TODO: remove
            render Groovy.groovyTemplate("index.html", title: "My Ratpack App")
        }

        get("recentActivity") { ActivityReportLoader reportLoader ->
            def month = request.queryParams["month"]
            def dateRange = DateRange.forMonthIncluding(month ? Date.parse("yyyy-MM", month) : new Date())
            def report = reportLoader.get(dateRange)
            render Template.thymeleafTemplate("recentActivity", report: report, month: month)
        }
        
        assets "public"
    }
}
