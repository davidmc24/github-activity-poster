package app

import groovy.transform.Canonical
import org.eclipse.egit.github.core.Repository
import org.eclipse.egit.github.core.event.Event

import static org.eclipse.egit.github.core.event.Event.*

@Canonical
class ProjectActivity implements Comparable<ProjectActivity> {
    String name
    String url
    SortedSet<Contributor> contributors = new TreeSet<>()
    int issuesOpenedCount = 0
    int issuesClosedCount = 0
    int commentCount = 0
    int pullRequestOpenedCount = 0
    int pullRequestClosedCount = 0
    int commitPushedCount = 0

    ProjectActivity(Repository repository) {
        this.name = repository.name
        this.url = repository.source?.htmlUrl ?: repository.htmlUrl
    }

    ProjectActivity(String name) {
        this.name = name
    }

    void addEvent(Contributor contributor, Event event) {
        this.contributors << contributor
        switch (event.type) {
            case TYPE_ISSUES:
                switch (event.payload.action) {
                    case "opened":
                        issuesOpenedCount++
                        break
                    case "closed":
                        issuesClosedCount++
                        break
                }
                break
            case TYPE_PULL_REQUEST:
                switch (event.payload.action) {
                    case "opened":
                        pullRequestOpenedCount++
                        break
                    case "closed":
                        pullRequestClosedCount++
                        break
                }
                break
            case TYPE_PUSH:
                commitPushedCount += event.payload.size as Integer
                break
            case TYPE_COMMIT_COMMENT:
            case TYPE_ISSUE_COMMENT:
            case TYPE_PULL_REQUEST_REVIEW_COMMENT:
                commentCount++
                break
        }
    }

    @Override
    int compareTo(ProjectActivity o) {
        return name.compareToIgnoreCase(o.name)
    }
}
