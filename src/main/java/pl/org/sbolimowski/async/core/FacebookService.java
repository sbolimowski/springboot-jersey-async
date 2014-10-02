package pl.org.sbolimowski.async.core;

import org.springframework.stereotype.Component;
import pl.org.sbolimowski.async.model.FacebookUser;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.Future;

@Component
public class FacebookService {

    private final WebTarget target = ClientBuilder.newClient()
            .target("http://graph.facebook.com/");

    public Future<FacebookUser> getUserAsync(String user) {
        return target
                .path("/{user}")
                .resolveTemplate("user", user)
                .request().async()
                .get(FacebookUser.class);
    }
}
