package kg.itschool.booking.exceptions;

public class IncorrectUserNameException extends RuntimeException {
    public IncorrectUserNameException(String message) {
        super(message);
    }
}
