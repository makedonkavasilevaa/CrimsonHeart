package mk.finki.ukim.mk.crimsonheart.exceptions;

public class UsersNotFoundException extends RuntimeException {
    public UsersNotFoundException(Long userId) {
        super(String.format("User %s not found", userId));
    }
}
