package ru.agiletech.sprint.service.infrastructure.config;


import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
@EnableScheduling
@EnableDiscoveryClient
@EnableBinding(Source.class)
public class ApplicationConfig implements WebMvcConfigurer, SchedulingConfigurer {

    @Value("${event.publisher.poolSize}")
    private int poolSize;

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MdcInterceptor());
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(poolSize);
        taskScheduler.setThreadNamePrefix("eventPublisher-");
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }

    public static class MdcInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(@NotNull HttpServletRequest     request,
                                 @NotNull HttpServletResponse    response,
                                 @NotNull Object                 handler) {
            try {
                MDC.clear();
            }
            catch (IllegalStateException e){
                log.error("Error during clearing MDC", e);
                return false;
            }

            return true;
        }
    }

}
