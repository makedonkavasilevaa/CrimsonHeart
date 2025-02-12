package mk.finki.ukim.mk.crimsonheart;


import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.enums.Sex;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AuthServiceTest {

    @Autowired
    private UsersRepository usersRepository;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void testRegisterAndLogin_Success() {
        Users user = new Users();
        user.setRole(Roles.PATIENT);         //ako prave problem smeni u PATIENT
        user.setName("John");
        user.setSurname("Doe");
        user.setBirthday(new Date());
        user.setSex(Sex.MALE);
        user.setEmail("john.doe@example.com");
        user.setPhone("123456789");
        user.setEmbg("1234567890123");
        user.setPassword(passwordEncoder.encode("password"));

        usersRepository.save(user);

        Users foundUser = usersRepository.findAllByEmail("john.doe@example.com").orElse(null);
        assertNotNull(foundUser);
        assertTrue(passwordEncoder.matches("password", foundUser.getPassword()));
    }

    @Test
    void testRegisterWithDuplicateEmbg_Fails() {
        Users user1 = new Users();
        user1.setEmbg("1234567890123");
        user1.setEmail("user1@example.com");
        user1.setPassword(passwordEncoder.encode("password"));
        usersRepository.save(user1);

        Users user2 = new Users();
        user2.setEmbg("1234567890123");  // Same EMBG
        user2.setEmail("user2@example.com");
        user2.setPassword(passwordEncoder.encode("password"));

        assertThrows(Exception.class, () -> usersRepository.save(user2));
    }

    @Test
    void testRegisterWithDuplicateEmail_Fails() {
        Users user1 = new Users();
        user1.setEmail("duplicate@example.com");
        user1.setEmbg("1234567890123");
        user1.setPassword(passwordEncoder.encode("password"));
        usersRepository.save(user1);

        Users user2 = new Users();
        user2.setEmail("duplicate@example.com"); // Same email
        user2.setEmbg("9876543210987");
        user2.setPassword(passwordEncoder.encode("password"));

        assertThrows(Exception.class, () -> usersRepository.save(user2));
    }

}
