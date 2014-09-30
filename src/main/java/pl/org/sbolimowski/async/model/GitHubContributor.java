package pl.org.sbolimowski.async.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GitHubContributor {
    private final long id;
    private final String login;

    public GitHubContributor(@JsonProperty("id") long id,
                             @JsonProperty("login") String login) {
        this.id = id;
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
}
