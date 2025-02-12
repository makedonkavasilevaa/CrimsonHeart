package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.Users;

import java.util.List;

public interface UsersCustomRepository {

    public List<Users> getUsersByFilter(Roles roles, String name, String embg, BloodType type);
}
