package app

import com.google.common.cache.CacheBuilder
import com.google.common.cache.LoadingCache
import org.eclipse.egit.github.core.User

import javax.inject.Inject
import java.util.concurrent.TimeUnit

class ContributorLoader {
    private final LoadingCache<String, Contributor> cache

    @Inject
    ContributorLoader(ContributorCacheLoader cacheLoader) {
        cache = CacheBuilder.newBuilder().expireAfterWrite(4, TimeUnit.HOURS).build(cacheLoader)
    }

    Contributor get(User user) {
        return get(user.login)
    }

    Contributor get(String login) {
        return cache.get(login)
    }
}
