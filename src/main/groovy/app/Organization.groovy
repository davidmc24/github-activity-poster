package app

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import org.eclipse.egit.github.core.User

@Canonical
@EqualsAndHashCode(includes = ["id"])
class Organization {
    int id
    String avatarUrl
    String url
    String login
    String name

    Organization(User user) {
        this.id = user.id
        this.avatarUrl = user.avatarUrl
        this.url = user.htmlUrl
        this.login = user.login
        this.name = user.name
    }
}
