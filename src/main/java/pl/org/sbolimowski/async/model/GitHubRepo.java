package pl.org.sbolimowski.async.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GitHubRepo {
    private final long id;
    private final String name;
    private final int size;
    private final int watchers;
    private final int forks;

    public GitHubRepo(@JsonProperty("id") long id,
                      @JsonProperty("name") String name,
                      @JsonProperty("size") int size,
                      @JsonProperty("watchers") int watchers,
                      @JsonProperty("forks") int forks) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.watchers = watchers;
        this.forks = forks;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getWatchers() {
        return watchers;
    }

    public int getForks() {
        return forks;
    }
}
