package com.oneworldaccuracy.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfiguration  implements AsyncConfigurer {


    @Override
    @Bean(name = "emailExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Async-");
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(600);
        executor.initialize();
        return executor;
    }


}
