package cartbasic.cartbasic.exception;

import java.util.Arrays;

public class NotFoundException extends RuntimeException {
    private String message;

    public NotFoundException(String message, Object... var2) {
        this.message = message + Arrays.toString(var2);
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
