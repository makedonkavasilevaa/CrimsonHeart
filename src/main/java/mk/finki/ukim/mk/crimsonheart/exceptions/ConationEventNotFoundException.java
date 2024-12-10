package mk.finki.ukim.mk.crimsonheart.exceptions;

public class ConationEventNotFoundException extends RuntimeException {
    public ConationEventNotFoundException(Long eventId) {
        super(String.format("Donation event %d not found", eventId));
    }
}
