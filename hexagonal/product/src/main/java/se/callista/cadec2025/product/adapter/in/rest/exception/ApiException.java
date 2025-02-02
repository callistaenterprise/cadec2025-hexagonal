package se.callista.cadec2025.product.adapter.in.rest.exception;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final ZoneId utc = ZoneId.of("UTC");
    private final OffsetDateTime timestamp;
    private final HttpStatus status;

    public ApiException(HttpStatus status, String msg) {
        super(msg);
        this.timestamp = OffsetDateTime.now(utc);
        this.status = status;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
