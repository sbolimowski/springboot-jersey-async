package pl.org.async;

import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Futures {
    public static <T> CompletableFuture<T> toCompletable(Future<T> future, TaskExecutor taskExecutor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                AsyncResource.LOG.info("before getting future result "+future);
                T t = future.get();
                AsyncResource.LOG.info("after getting future result "+t);
                return t;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }, taskExecutor);
    }

    public static <T> CompletableFuture<T> toCompletable(Future<T> future) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                T t = future.get();
                AsyncResource.LOG.info("after getting future result "+t);
                return t;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
