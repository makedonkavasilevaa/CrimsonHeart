package mk.finki.ukim.mk.crimsonheart.exceptions;

public class InstitutionNotFoundException extends RuntimeException {
    public InstitutionNotFoundException(Long institutionId) {
        super(String.format("Institution %s not found", institutionId));
    }
}
