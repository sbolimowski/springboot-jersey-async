package pl.org.sbolimowski.async.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import pl.org.sbolimowski.async.core.FacebookService;
import pl.org.sbolimowski.async.core.Futures;
import pl.org.sbolimowski.async.core.GitHubService;
import pl.org.sbolimowski.async.model.FacebookInfo;
import pl.org.sbolimowski.async.model.GitHubInfo;
import pl.org.sbolimowski.async.model.UserInfo;

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

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

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
        CompletableFuture<GitHubInfo> gitHubInfoFuture = Futures.toCompletable(gitHubService.getInfoAsync(user), taskExecutor);
        CompletableFuture<FacebookInfo> facebookInfoFuture = Futures.toCompletable(facebookService.getInfoAsync(user), taskExecutor);

        gitHubInfoFuture
                .thenCombine(
                        facebookInfoFuture, (g, f) -> new UserInfo(f, g))
                .thenApply(
                        info -> asyncResponse.resume(info))
                .exceptionally(
                        e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(1000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(
                ar -> ar.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Operation timed out").build()));
    }
}
