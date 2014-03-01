package app

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import org.eclipse.egit.github.core.User

@Canonical
@EqualsAndHashCode(includes = ["login"])
class Contributor implements Comparable<Contributor> {
    String login
    String avatarUrl
    String name
    String url

    Contributor(User user) {
        this.login = user.login
        this.avatarUrl = user.avatarUrl
        this.name = user.name
        this.url = user.htmlUrl
    }

    String getDisplayName() {
        if (name) {
            return "${login} (${name})"
        } else {
            return login
        }
    }

    @Override
    int compareTo(Contributor o) {
        return login.compareToIgnoreCase(o.login)
    }
}
