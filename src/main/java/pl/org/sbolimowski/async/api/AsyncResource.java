package pl.org.sbolimowski.async.api;

import org.springframework.core.task.TaskExecutor;
import pl.org.sbolimowski.async.core.FacebookService;
import pl.org.sbolimowski.async.model.GitHubRepo;
import pl.org.sbolimowski.async.utils.Futures;
import pl.org.sbolimowski.async.core.GitHubService;
import pl.org.sbolimowski.async.model.FacebookInfo;
import pl.org.sbolimowski.async.model.GitHubContributor;
import pl.org.sbolimowski.async.model.GitHubUser;
import pl.org.sbolimowski.async.model.UserInfo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

@Path("/")
public class AsyncResource {

    @Inject
    private FacebookService facebookService;

    @Inject
    private GitHubService gitHubService;

    @Inject
    private TaskExecutor executor;

    @GET
    @Path("/userInfo/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public void userInfoAsync(@Suspended AsyncResponse asyncResponse, @PathParam("user") String user) {
        CompletableFuture<GitHubUser> gitHubInfoFuture = Futures.toCompletable(gitHubService.userInfoAsync(user), executor);
        CompletableFuture<FacebookInfo> facebookInfoFuture = Futures.toCompletable(facebookService.getInfoAsync(user), executor);

        gitHubInfoFuture
                .thenCombine(
                        facebookInfoFuture, (g, f) -> new UserInfo(f, g))
                .thenApply(
                        info -> asyncResponse.resume(info))
                .exceptionally(
                        e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build()));

        asyncResponse.setTimeout(1000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(
                ar -> ar.resume(Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));

    }

    @GET
    @Path("/contributors/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public void contributorsAsync(@Suspended AsyncResponse asyncResponse, @PathParam("user") String user) {
        Futures.toCompletable(gitHubService.reposAsync(user), executor)
                .thenCompose(
                        repos -> getContributors(user, repos))
                .thenApply(
                        contributors -> contributors.flatMap(list -> list.stream()))
                .thenApply(
                        contributors -> contributors.collect(Collectors.groupingBy(
                                c -> c.getLogin(),
                                Collectors.counting())))
                .thenApply(
                        contributors -> asyncResponse.resume(contributors))
                .exceptionally(
                        e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build())
                );
    }

    private CompletableFuture<Stream<List<GitHubContributor>>> getContributors(String user, List<GitHubRepo> repos) {
        return Futures.sequence(
                repos.stream().map(r -> Futures.toCompletable(gitHubService.contributorsAsync(user, r.getName()), executor)));
    }

}
