package pl.org.async;

import org.springframework.stereotype.Component;
import pl.org.async.model.GitHubInfo;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.Future;

@Component
public class GitHubService {

    public Future<GitHubInfo> getInfoAsync(String user) {
        return ClientBuilder.newClient()
                .target("https://api.github.com/")
                .path("/users/{user}")
                .resolveTemplate("user", user)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .async()
                .get(GitHubInfo.class);
    }
}
