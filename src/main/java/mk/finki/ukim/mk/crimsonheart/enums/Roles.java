package mk.finki.ukim.mk.crimsonheart.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    SUPERADMIN,
    HOSPITALADMIN,
    REDCROSSADMIN,
    BLOODBANKADMIN,
    NURSE,
    DOCTOR,
    LABORATORY_ASSISTANT,
    MANAGER,
    PATIENT,
    VOLUNTEER,
    ORGANIZER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
