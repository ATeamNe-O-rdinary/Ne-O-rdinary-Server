package org.ateam.ateam.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .components(
            new Components()
                .addSchemas("ErrorResponse", errorSchema())
                .addSchemas("ResponseDto", responseDtoSchema()));
  }

  private Schema<?> errorSchema() {
    return new ObjectSchema()
        .addProperty("message", new StringSchema())
        .addProperty("status", new IntegerSchema())
        .addProperty("errors", new Schema<>().type("array"))
        .addProperty("code", new StringSchema());
  }

  private Schema<?> responseDtoSchema() {
    return new ObjectSchema()
        .addProperty("status", new IntegerSchema())
        .addProperty("code", new StringSchema())
        .addProperty("data", new Schema<>())
        .addProperty("message", new StringSchema());
  }
}
