package mk.finki.ukim.mk.crimsonheart.service.impl;

import mk.finki.ukim.mk.crimsonheart.repository.UsersRepository;
import mk.finki.ukim.mk.crimsonheart.service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
}
