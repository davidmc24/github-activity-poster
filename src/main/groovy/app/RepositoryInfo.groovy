package app

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import org.eclipse.egit.github.core.Repository

@Canonical
@EqualsAndHashCode(includes = ["id"])
class RepositoryInfo {
    long id
    String owner
    String name
    String url
    String description
    String homepage
    RepositoryInfo source
    RepositoryInfo parent

    RepositoryInfo(Repository repository) {
        this.id = repository.id
        this.owner = repository.owner
        this.name = repository.name
        this.url = repository.htmlUrl
        this.description = repository.description
        this.homepage = repository.homepage
        this.source = repository.source ? new RepositoryInfo(repository.source) : null
        this.parent = repository.parent ? new RepositoryInfo(repository.parent) : null
    }
}
