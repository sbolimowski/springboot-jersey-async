package pl.org.sbolimowski.async.model;

public class UserInfo {
    private final GitHubUser gitHubUser;
    private final FacebookInfo facebookInfo;

    public UserInfo(FacebookInfo facebookInfo, GitHubUser gitHubUser) {
        this.gitHubUser = gitHubUser;
        this.facebookInfo = facebookInfo;
    }

    public GitHubUser getGitHubUser() {
        return gitHubUser;
    }

    public FacebookInfo getFacebookInfo() {
        return facebookInfo;
    }
}
