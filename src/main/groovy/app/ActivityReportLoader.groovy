package app

import com.google.common.cache.CacheBuilder
import com.google.common.cache.LoadingCache

import javax.inject.Inject
import java.util.concurrent.TimeUnit

class ActivityReportLoader {
    private final LoadingCache<DateRange, ActivityReport> cache

    @Inject
    ActivityReportLoader(ActivityReportCacheLoader cacheLoader) {
        cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build(cacheLoader)
    }

    ActivityReport get(DateRange dateRange) {
        return cache.get(dateRange)
    }
}
