package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
}
