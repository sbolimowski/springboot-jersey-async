package pl.org.sbolimowski.async.model;

public class UserInfo {
    private final GitHubInfo gitHubInfo;
    private final FacebookInfo facebookInfo;

    public UserInfo(FacebookInfo facebookInfo, GitHubInfo gitHubInfo) {
        this.gitHubInfo = gitHubInfo;
        this.facebookInfo = facebookInfo;
    }

    public GitHubInfo getGitHubInfo() {
        return gitHubInfo;
    }

    public FacebookInfo getFacebookInfo() {
        return facebookInfo;
    }
}
