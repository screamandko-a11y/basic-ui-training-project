package lv.bootcamp.shelter.dto;


import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;

@Getter
public class ApiErrorResponse {

    @Schema(
            description = "Time when the error occurred",
            example = "2026-10-25T12:00:00Z")
    private OffsetDateTime timestamp;

    @Schema(
            description = "HTTP response status code",
            example = "400")
    private int status;

    @Schema(
            description = "HTTP error description",
            example = "Bad Request")
    private String error;

    @Schema(
            description = "Request path that caused the error",
            example = "/api/animals")
    private String path;
}
