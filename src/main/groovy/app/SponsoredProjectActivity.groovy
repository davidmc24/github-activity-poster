package app

import org.eclipse.egit.github.core.Repository
import org.eclipse.egit.github.core.event.Event

import static org.eclipse.egit.github.core.event.Event.*

class SponsoredProjectActivity {
    String name
    String description
    String url
    String homepage
    SortedSet<Contributor> contributors = new TreeSet<>()
    int issuesOpenedCount
    int issuesClosedCount
    int commentCount
    int pullRequestOpenedCount
    int pullRequestClosedCount
    int commitPushedCount

    SponsoredProjectActivity(Repository repo, List<Event> events, ContributorLoader contributorLoader) {
        this.name = repo.name
        this.description = repo.description
        this.url = repo.htmlUrl
        this.homepage = repo.homepage
        this.contributors.addAll(events.collect {contributorLoader.get(it.actor)})
        this.issuesOpenedCount = events.count {it.type == TYPE_ISSUES && it.payload.action == "opened"}
        this.issuesClosedCount = events.count {it.type == TYPE_ISSUES && it.payload.action == "closed"}
        this.commentCount = events.count {it.type in [TYPE_COMMIT_COMMENT, TYPE_ISSUE_COMMENT, TYPE_PULL_REQUEST_REVIEW_COMMENT]}
        this.pullRequestOpenedCount = events.count {it.type == TYPE_PULL_REQUEST && it.payload.action == "opened"}
        this.pullRequestClosedCount = events.count {it.type == TYPE_PULL_REQUEST && it.payload.action == "closed"}
        this.commitPushedCount = events.findAll {it.type == TYPE_PUSH}.sum {it.payload.size} as Integer ?: 0
    }
}
