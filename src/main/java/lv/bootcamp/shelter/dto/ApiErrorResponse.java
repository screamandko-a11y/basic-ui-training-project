package lv.bootcamp.shelter.dto;

import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class ApiErrorResponse {
    private OffsetDateTime timestamp;
    private int status;
    private String error;
    private String path;
}
