package org.formulaone.rest.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSwagger
public class SwaggerConfig {

  private SpringSwaggerConfig springSwaggerConfig;

  @Autowired
  public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
    this.springSwaggerConfig = springSwaggerConfig;
  }

  @Bean
  public SwaggerSpringMvcPlugin customImplementation() {
    return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
        .apiInfo(apiInfo())
        .includePatterns(".api.*");
  }

  private ApiInfo apiInfo() {
    ApiInfo apiInfo = new ApiInfo(
        "Formula One API",
        "Formula One API is a web service which provides a historical record of Formula 1 racing data.",
        "Formula One API terms of service",
        "t0tec (t0tec.olmec@gmail.com)",
        "MIT license",
        "http://goo.gl/x8cMrQ"
    );
    return apiInfo;
  }
}
