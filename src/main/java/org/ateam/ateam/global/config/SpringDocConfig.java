package org.ateam.ateam.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components()
				.addSchemas("ErrorResponse", errorSchema())
			);
	}

	private Schema<?> errorSchema() {
		Schema<?> schema = new Schema<>();
		schema.setType("object");
		schema.addProperty("message", new StringSchema());
		schema.addProperty("status", new IntegerSchema());
		schema.addProperty("errors", new ArraySchema().items(new StringSchema()));
		schema.addProperty("code", new StringSchema());
		return schema;
	}
}
