package mk.finki.ukim.mk.crimsonheart.service;

import mk.finki.ukim.mk.crimsonheart.model.Users;

public interface AuthService {

    Users login(String username, String password);
}
