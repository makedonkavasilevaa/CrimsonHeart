package mk.finki.ukim.mk.crimsonheart.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.DonationEventCustomRepository;

import java.util.ArrayList;
import java.util.List;

public class DonationEventCustomRepositoryImpl implements DonationEventCustomRepository {

    @PersistenceContext(unitName="entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<DonationEvent> filterEvents(String name, DonationType donationType, CityEnum city) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DonationEvent> criteriaQuery = builder.createQuery(DonationEvent.class);

        Root<DonationEvent> root = criteriaQuery.from(DonationEvent.class);
        Join<DonationEvent, Location> joinLocation = root.join("location", JoinType.INNER);


        List<Predicate> restrictions = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            Predicate nameFilter = builder.like(root.get("name"), "%" + name + "%");
            Predicate descrFilter = builder.like(root.get("description"), "%" + name + "%");
            restrictions.add(builder.or(nameFilter, descrFilter));
        }

        if (donationType != null) {
            restrictions.add(builder.equal(root.get("donationType"), donationType));
        }

        if (city != null) {
            restrictions.add(builder.equal(joinLocation.get("city"), city));
        }

        criteriaQuery.where(builder.and(restrictions.toArray(new Predicate[0])));

        // Build and execute query
        TypedQuery<DonationEvent> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
