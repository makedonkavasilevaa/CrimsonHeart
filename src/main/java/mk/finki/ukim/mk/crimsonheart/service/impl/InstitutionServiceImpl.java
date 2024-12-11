package mk.finki.ukim.mk.crimsonheart.service.impl;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.repository.InstitutionRepository;
import mk.finki.ukim.mk.crimsonheart.service.InstitutionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;

    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @Override
    public List<Institution> listAll() {
        return this.institutionRepository.findAll();
    }

    @Override
    public Optional<Institution> findById(Long id) {
        return this.institutionRepository.findById(id);
    }

    @Override
    public Optional<Institution> findByName(String name) {
        return this.institutionRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Optional<Institution> findByInstitutionType(InstitutionsType institutionsType) {
        return this.institutionRepository.findAllByInstitutionsType(institutionsType);
    }

    @Override
    public Optional<Institution> findAllByCity(CityEnum cityEnum) {
        return this.institutionRepository.findAllByLocationCity(cityEnum);
    }
}
