package app

import groovy.util.logging.Slf4j
import org.eclipse.egit.github.core.event.Event
import org.eclipse.egit.github.core.service.EventService
import org.eclipse.egit.github.core.service.OrganizationService
import org.eclipse.egit.github.core.service.RepositoryService

import javax.inject.Inject

import static org.eclipse.egit.github.core.event.Event.*

@Slf4j
class ActivityReportBuilder {
    @Inject
    EventService eventService

    @Inject
    RepositoryService repositoryService

    @Inject
    OrganizationService organizationService

    @Inject
    ContributorLoader contributorLoader

    DateRange dateRange
    List<String> orgNames = []
    Set<String> includedEvents = [
            TYPE_COMMIT_COMMENT, TYPE_CREATE, TYPE_DELETE, TYPE_DOWNLOAD, TYPE_FOLLOW, TYPE_FORK, TYPE_FORK_APPLY,
            TYPE_GIST, TYPE_GOLLUM, TYPE_ISSUE_COMMENT, TYPE_ISSUES, TYPE_MEMBER, TYPE_PUBLIC, TYPE_PULL_REQUEST,
            TYPE_PULL_REQUEST_REVIEW_COMMENT, TYPE_PUSH, TYPE_TEAM_ADD, TYPE_WATCH
    ]

    ActivityReportBuilder dateRange(DateRange dateRange) {
        this.dateRange = dateRange
        return this
    }

    ActivityReportBuilder organization(String orgName) {
        orgNames << orgName
        return this
    }

    ActivityReportBuilder excludes(String... events) {
        includedEvents.removeAll(events)
        return this
    }

    ActivityReport build() {
        // TODO: add support for external projects
        def sponsoredProjects = []
        for (orgName in orgNames) {
            def orgRepos = repositoryService.getOrgRepositories(orgName)
//            def orgMembers = organizationService.getMembers(orgName)
            log.debug("processing org repo events for {}", orgName)
            for (repo in orgRepos) {
                log.debug("processing repo events for {}", repo.name)
                def events = filterEvents(eventService.pageEvents(repo))
                if (events) {
                    sponsoredProjects << new SponsoredProjectActivity(repo, events, contributorLoader)
                }
            }
//            log.debug("processing org member events for {}", orgName)
//            for (member in orgMembers) {
//                log.debug("processing member events for {}", member.login)
//                def events = filterEvents(eventService.pageUserEvents(member.login))
                // TODO: create external projects
//            }
        }
        return new ActivityReport(sponsoredProjects)
    }

    private List<Event> filterEvents(Iterator<Collection<Event>> pagedEvents) {
        def events = []
        for (page in pagedEvents) {
            for (event in page) {
                def created = event.createdAt
                if (created < dateRange.startDate) break
                if (created < dateRange.endDate && event.type in includedEvents) {
                    events << event
                }
            }
        }
        return events
    }
}
