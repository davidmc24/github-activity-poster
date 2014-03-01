package app

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Scopes
import org.eclipse.egit.github.core.client.GitHubClient
import org.eclipse.egit.github.core.service.EventService
import org.eclipse.egit.github.core.service.OrganizationService
import org.eclipse.egit.github.core.service.RepositoryService
import org.eclipse.egit.github.core.service.UserService

import javax.inject.Singleton

class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ActivityReportCacheLoader).in(Scopes.SINGLETON)
        bind(ActivityReportLoader).in(Scopes.SINGLETON)
        bind(ContributorCacheLoader).in(Scopes.SINGLETON)
        bind(ContributorLoader).in(Scopes.SINGLETON)
    }

    @Provides
    @Singleton
    GitHubClient provideGitHubClient() {
        def token = System.getenv("GITHUB_OAUTH_TOKEN")
        def client = new GitHubClient()
        client.setOAuth2Token(token)
        return client
    }

    @Provides
    @Singleton
    RepositoryService provideRepositoryService(GitHubClient gitHubClient) {
        return new RepositoryService(gitHubClient)
    }

    @Provides
    @Singleton
    EventService provideEventService(GitHubClient gitHubClient) {
        return new EventService(gitHubClient)
    }

    @Provides
    @Singleton
    OrganizationService provideOrganizationService(GitHubClient gitHubClient) {
        return new OrganizationService(gitHubClient)
    }

    @Provides
    @Singleton
    UserService provideUserService(GitHubClient gitHubClient) {
        return new UserService(gitHubClient)
    }
}
