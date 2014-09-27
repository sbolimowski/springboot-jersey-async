import pl.org.async.AsyncResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableAsync
public class Application extends ResourceConfig  {

    public Application() {
        register(AsyncResource.class);
    }

    @Bean
    public TaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(4);
        pool.setThreadGroupName("asyncExecutor");
        return pool;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}