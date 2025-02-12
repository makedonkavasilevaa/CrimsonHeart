package mk.finki.ukim.mk.crimsonheart.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import mk.finki.ukim.mk.crimsonheart.enums.BloodType;
import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.UsersCustomRepository;

import java.util.ArrayList;
import java.util.List;

public class UsersCustomRepositoryImpl implements UsersCustomRepository {

    @PersistenceContext(unitName="entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Users> getUsersByFilter(Roles roles, String name, String embg, BloodType type) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = builder.createQuery(Users.class);
        Root<Users> root = criteriaQuery.from(Users.class);

        List<Predicate> restrictions = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            Predicate nameFilter = builder.like(root.get("name"), "%" + name + "%");
            Predicate surnameFilter = builder.like(root.get("surname"), "%" + name + "%");
            restrictions.add(builder.or(nameFilter, surnameFilter));
        }

        if (embg != null && !embg.isEmpty() && embg.length() == 13) {
            restrictions.add(builder.equal(root.get("embg"), embg));
        }

        if (type != null) {
            restrictions.add(builder.equal(root.get("bloodType"), type));
        }

        if (roles != null) {
            restrictions.add(builder.equal(root.get("role"), roles));
        }

        criteriaQuery.where(builder.and(restrictions.toArray(new Predicate[0])));

        // Build and execute query
        TypedQuery<Users> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}