package pl.org.sbolimowski.async.model;

public class UserInfo {
    private final GitHubUser gitHubUser;
    private final FacebookUser facebookUser;

    public UserInfo(FacebookUser facebookUser, GitHubUser gitHubUser) {
        this.gitHubUser = gitHubUser;
        this.facebookUser = facebookUser;
    }

    public GitHubUser getGitHubUser() {
        return gitHubUser;
    }

    public FacebookUser getFacebookUser() {
        return facebookUser;
    }
}
