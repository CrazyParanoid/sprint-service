package ru.agiletech.sprint.service.infrastructure.config;


import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.slf4j.MDC;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
@EnableDiscoveryClient
@EnableBinding(Source.class)
public class ApplicationConfig implements WebMvcConfigurer {

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
