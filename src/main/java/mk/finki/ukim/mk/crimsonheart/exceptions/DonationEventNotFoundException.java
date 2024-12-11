package mk.finki.ukim.mk.crimsonheart.exceptions;

public class DonationEventNotFoundException extends RuntimeException {
    public DonationEventNotFoundException(Long eventId) {
        super(String.format("Donation event %d not found", eventId));
    }
}
