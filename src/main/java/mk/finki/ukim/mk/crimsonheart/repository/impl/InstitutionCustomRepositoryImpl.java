package mk.finki.ukim.mk.crimsonheart.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.repository.InstitutionCustomRepository;

import java.util.ArrayList;
import java.util.List;

public class InstitutionCustomRepositoryImpl implements InstitutionCustomRepository {

    @PersistenceContext(unitName="entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Institution> filterInstitutions(String name, InstitutionsType type, String address, CityEnum city) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Institution> criteriaQuery = builder.createQuery(Institution.class);

        Root<Institution> root = criteriaQuery.from(Institution.class);
        Join<Institution, Location> joinLocation = root.join("location", JoinType.INNER);

        List<Predicate> restrictions = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            restrictions.add(builder.like(root.get("name"), "%" + name + "%"));
        }

        if (type != null) {
            restrictions.add(builder.equal(root.get("institutionsType"), type));
        }

        if (address != null && !address.isEmpty()) {
            restrictions.add(builder.like(joinLocation.get("address"), "%" + address + "%"));
        }

        if (city != null) {
            restrictions.add(builder.equal(joinLocation.get("city"), city));
        }

        criteriaQuery.where(builder.and(restrictions.toArray(new Predicate[0])));

        // Build and execute query
        TypedQuery<Institution> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
