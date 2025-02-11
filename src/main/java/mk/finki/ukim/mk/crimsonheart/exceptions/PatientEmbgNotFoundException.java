package mk.finki.ukim.mk.crimsonheart.exceptions;

public class PatientEmbgNotFoundException extends RuntimeException {
    public PatientEmbgNotFoundException(String embg) {
        super("EMBG NOT FOUND!");
    }
}
