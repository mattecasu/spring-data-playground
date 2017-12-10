package playground.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class My400Exception extends RuntimeException {
    public My400Exception(String message) {
        super(message);
    }
}