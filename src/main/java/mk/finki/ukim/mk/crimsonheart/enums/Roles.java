package mk.finki.ukim.mk.crimsonheart.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    SUPERADMIN,
    NURSE,
    DOCTOR,
    MANAGER,
    PATIENT,
    ORGANIZER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
