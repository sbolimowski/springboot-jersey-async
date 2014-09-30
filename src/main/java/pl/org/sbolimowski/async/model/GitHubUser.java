package pl.org.sbolimowski.async.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GitHubUser {
    private final String login;
    private final String type;
    private final String name;
    @JsonCreator
    public GitHubUser(@JsonProperty("login") String login,
                      @JsonProperty("type") String type,
                      @JsonProperty("name") String name) {
        this.login = login;
        this.type = type;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
