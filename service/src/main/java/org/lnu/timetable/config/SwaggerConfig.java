package org.lnu.timetable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.Set;

@Configuration
public class SwaggerConfig implements WebFluxConfigurer {

    Set<String> endpointsWithoutAuth = Set.of("/auth/sign-in");

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(List.of(createSecurityContext()))
                .securitySchemes(List.of(createApiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    private ApiKey createApiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext createSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(createDefaultAuth())
                .operationSelector(operationSelector -> !endpointsWithoutAuth.contains(operationSelector.requestMappingPattern()))
                .build();
    }

    private List<SecurityReference> createDefaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }
}
