package app

import com.google.common.cache.CacheLoader
import org.eclipse.egit.github.core.service.UserService

import javax.inject.Inject

class ContributorCacheLoader extends CacheLoader<String, Contributor> {
    @Inject
    UserService userService

    @Override
    Contributor load(String login) throws Exception {
        return new Contributor(userService.getUser(login))
    }
}
