package com.hostfully.bookingservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String JSON_MEDIA_TYPE = "application/json";

    private static final String BAD_REQUEST_EXAMPLE = "{\"code\" : 400, \"status\" : \"Bad Request\", \"Message\" : \"Bad Request\"}";
    private static final String INTERNAL_SERVER_ERROR_EXAMPLE = "{\"code\" : 500, \"status\" : \"InternalServerError\", \"Message\" : \"InternalServerError\"}";
    private static final String SUCCESSFUL_RESPONSE_EXAMPLE = "{\"name\":\"string\",\"surname\":\"string\",\"age\":0}";

    @Bean
    public OpenAPI api() {
        Components components = new Components();
        components.addResponses("badRequest", buildApiResponse(BAD_REQUEST_EXAMPLE));
        components.addResponses("internalServerError", buildApiResponse(INTERNAL_SERVER_ERROR_EXAMPLE));
        components.addResponses("successfulResponse", buildApiResponse(SUCCESSFUL_RESPONSE_EXAMPLE));

        return new OpenAPI()
                .components(components)
                .info(new Info().title("Booking Service API Docs").version("1.0.0").description("Booking Service API Docs"));
    }

    private ApiResponse buildApiResponse(String example) {
        return new ApiResponse()
                .content(new Content().addMediaType(JSON_MEDIA_TYPE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default", new Example().value(example))));
    }
}