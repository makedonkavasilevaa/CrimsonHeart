package mk.finki.ukim.mk.crimsonheart.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String embg) {
        super(String.format("User already exists", embg));
    }
}
