package pl.org.sbolimowski.async.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GitHubInfo {
    private final String login;
    private final String type;
    private final int publicRepos;
    private final int followers;
    private final int following;

    @JsonCreator
    public GitHubInfo(@JsonProperty("login") String login,
                      @JsonProperty("type") String type,
                      @JsonProperty("public_repos") int publicRepos,
                      @JsonProperty("followers") int followers,
                      @JsonProperty("following") int following) {
        this.login = login;
        this.type = type;
        this.publicRepos = publicRepos;
        this.followers = followers;
        this.following = following;
    }

    public String getLogin() {
        return login;
    }

    public String getType() {
        return type;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }
}
