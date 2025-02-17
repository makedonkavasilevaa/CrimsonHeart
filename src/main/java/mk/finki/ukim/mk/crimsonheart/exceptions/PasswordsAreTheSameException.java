package mk.finki.ukim.mk.crimsonheart.exceptions;

public class PasswordsAreTheSameException extends RuntimeException {
    public PasswordsAreTheSameException() {
        super("The old and new passwords are the same");
    }
}