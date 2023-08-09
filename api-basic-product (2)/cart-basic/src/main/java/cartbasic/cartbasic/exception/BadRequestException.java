package cartbasic.cartbasic.exception;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class BadRequestException extends RuntimeException {
    private String message;

    public BadRequestException(String errorCode, Object... var2) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(errorCode, var2);
        this.message = formattingTuple.getMessage();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
