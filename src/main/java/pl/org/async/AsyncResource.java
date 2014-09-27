package pl.org.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import pl.org.async.model.FacebookInfo;
import pl.org.async.model.GitHubInfo;
import pl.org.async.model.UserInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static pl.org.async.Futures.toCompletable;

@Path("/")
@Component
public class AsyncResource {

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private TaskExecutor taskExecutor;

    @GET
    @Path("/async/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public void userInfoAsync(@Suspended AsyncResponse asyncResponse, @PathParam("user") String user) {
        CompletableFuture<GitHubInfo> gitHubInfoFuture = toCompletable(gitHubService.getInfoAsync(user), taskExecutor);
        CompletableFuture<FacebookInfo> facebookInfoFuture = toCompletable(facebookService.getInfoAsync(user), taskExecutor);

        gitHubInfoFuture
                .thenCombine(facebookInfoFuture, (g, f) -> new UserInfo(f, g))
                .thenAccept((info) -> asyncResponse.resume(info));

        asyncResponse.setTimeout(10000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(ar -> {
            ar.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Operation timed out").build());
        });
    }
}
