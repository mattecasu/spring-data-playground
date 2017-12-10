package playground.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class My404Exception extends RuntimeException {
    public My404Exception(String message) {
        super(message);
    }
}