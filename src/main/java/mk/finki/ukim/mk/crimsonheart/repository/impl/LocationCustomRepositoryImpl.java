package mk.finki.ukim.mk.crimsonheart.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.repository.LocationCustomRepository;

import java.util.ArrayList;
import java.util.List;

public class LocationCustomRepositoryImpl implements LocationCustomRepository {

    @PersistenceContext(unitName="entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Location> filterLocations(String address, CityEnum city) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Location> criteriaQuery = builder.createQuery(Location.class);

        Root<Location> root = criteriaQuery.from(Location.class);

        List<Predicate> restrictions = new ArrayList<>();

        if (address != null && !address.isEmpty()) {
            restrictions.add(builder.like(root.get("address"), "%" + address + "%"));
        }

        if (city != null) {
            restrictions.add(builder.equal(root.get("city"), city));
        }

        criteriaQuery.where(builder.and(restrictions.toArray(new Predicate[0])));

        // Build and execute query
        TypedQuery<Location> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
