package br.com.senior.erp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
    private final String title;
    private final String description;
    private final String version;

    public SwaggerConfiguration(@Value("${spring.application.swagger.title}") final String title,
                                @Value("${spring.application.swagger.description}") final String description,
                                @Value("${spring.application.swagger.version}") final String version) {
        this.title = title;
        this.description = description;
        this.version = version;
    }

    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.senior.erp"))
                .build()
                .tags(new Tag("ERP", "Fluxo de pedido", 0))
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title(title.toUpperCase())
                .description(description.toUpperCase())
                .version(version)
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }
}
