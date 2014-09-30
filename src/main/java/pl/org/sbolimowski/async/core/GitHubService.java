package pl.org.sbolimowski.async.core;

import org.springframework.stereotype.Service;
import pl.org.sbolimowski.async.model.GitHubContributor;
import pl.org.sbolimowski.async.model.GitHubRepo;
import pl.org.sbolimowski.async.model.GitHubUser;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class GitHubService {

    private final WebTarget target = ClientBuilder.newClient().target("https://api.github.com/");

    public Future<GitHubUser> userInfoAsync(String user) {
        return target
                .path("/users/{user}")
                .resolveTemplate("user", user)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .async()
                .get(GitHubUser.class);
    }

    public Future<List<GitHubRepo>> reposAsync(String user) {
        return target
                .path("users/{user}/repos")
                .resolveTemplate("user", user)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .async()
                .get(new GenericType<List<GitHubRepo>>() { });
    }

    public Future<List<GitHubContributor>> contributorsAsync(String owner, String repo) {
        return target
                .path("/repos/{owner}/{repo}/contributors")
                .resolveTemplate("owner", owner)
                .resolveTemplate("repo", repo)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .async()
                .get(new GenericType<List<GitHubContributor>>() { });
    }
}
