package com.projeto.cars.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket (DocumentationType.SWAGGER_2)
        .apiInfo (metaInfo())
        .select ()
        .apis (RequestHandlerSelectors.basePackage ("com.projeto.cars"))
        .paths (PathSelectors.any ())
        .build ();
    }

    private ApiInfo metaInfo(){
        ApiInfo apiInfo = new ApiInfo(
            "Carros API REST",
            "API REST de cadastro de carros",
            "1.0",
            "Terms of Service",
            new Contact("Allan Patrick", "https://www.instagram.com/allanpatrickdbp/",
                    "allan.15.patrick@gmail.com"),
            "Apache License Version 2.0",
            "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
    );
    return apiInfo;
    }

}