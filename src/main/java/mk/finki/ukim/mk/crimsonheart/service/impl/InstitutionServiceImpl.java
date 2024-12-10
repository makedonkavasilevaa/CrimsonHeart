package mk.finki.ukim.mk.crimsonheart.service.impl;

import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.service.InstitutionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Override
    public Optional<Institution> listAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Institution> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Institution> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Institution> findByInstitutionType(InstitutionsType institutionsType) {
        return Optional.empty();
    }
}
