package app

import groovy.transform.Canonical

@Canonical
class ActivityReport {
    List<Organization> organizations
    Map<Organization, List<ProjectActivity>> projectsByOrganization
    List<ProjectActivity> externalProjects

    List<ProjectActivity> getProjectsByOrganization(Organization organization) {
        return projectsByOrganization.get(organization)
    }
}
