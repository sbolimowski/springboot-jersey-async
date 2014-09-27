package pl.org.async.model;

import pl.org.async.model.FacebookInfo;
import pl.org.async.model.GitHubInfo;

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
