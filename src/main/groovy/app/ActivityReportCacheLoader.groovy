package app

import com.google.common.cache.CacheLoader

import javax.inject.Inject
import javax.inject.Provider

import static org.eclipse.egit.github.core.event.Event.*

class ActivityReportCacheLoader extends CacheLoader<DateRange, ActivityReport> {
    @Inject
    Provider<ActivityReportBuilder> activityReportBuilderProvider

    String getOrgName() {
        return System.getenv("ORGANIZATION_NAME")
    }

    @Override
    ActivityReport load(DateRange dateRange) {
        // TODO: change to include events rather than exclude?
        return activityReportBuilderProvider.get()
                .dateRange(dateRange)
                .organization(orgName)
                .excludes(TYPE_DOWNLOAD, TYPE_WATCH, TYPE_FORK, TYPE_DELETE, TYPE_CREATE, TYPE_GOLLUM)
                .build()
    }
}
