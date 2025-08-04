package com.example.odgateway.infrastructure.config;

import com.example.odgateway.infrastructure.resolver.LoginAccountResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("!test")
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginAccountResolver loginAccountResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginAccountResolver);
    }
}
