package se.callista.cadec2025.product.adapter.in.rest.exception;

import org.springframework.http.HttpStatus;

public class NotUniqueException extends ApiException {

    private static final long serialVersionUID = 1L;

    public NotUniqueException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }
}
