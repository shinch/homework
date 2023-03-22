package com.gmail.shinch.search_blog.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
public class EventExecutorConfig {
    @Bean("keywordEventExecutor")
    public Executor keywordEventExecutor() {
        String threadGroupName = "KEYWORD-EVENT";
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(1);
        taskExecutor.setThreadNamePrefix(threadGroupName + "-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean("searchErrorEventExecutor")
    public Executor searchErrorEventExecutor() {
        String threadGroupName = "ERROR-EVENT";
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(1);
        taskExecutor.setThreadNamePrefix(threadGroupName + "-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}

