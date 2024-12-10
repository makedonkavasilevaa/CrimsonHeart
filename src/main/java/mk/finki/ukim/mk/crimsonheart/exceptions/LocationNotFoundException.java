package mk.finki.ukim.mk.crimsonheart.exceptions;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(Long locationId) {
        super(String.format("Location %s not found", locationId));
    }
}
