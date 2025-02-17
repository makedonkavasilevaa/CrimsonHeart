package mk.finki.ukim.mk.crimsonheart.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Exam;
import mk.finki.ukim.mk.crimsonheart.model.Location;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.ExamCustomRepository;

import java.util.ArrayList;
import java.util.List;

public class ExamCustomRepositoryImpl implements ExamCustomRepository {

    @PersistenceContext(unitName="entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<Exam> filterExams(String name, String embg, DonationEvent event) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Exam> criteriaQuery = builder.createQuery(Exam.class);

        Root<Exam> root = criteriaQuery.from(Exam.class);
        Join<Exam, DonationEvent> joinEvent = root.join("donationEvent", JoinType.INNER);
        Join<Exam, Users> joinUser = root.join("patient", JoinType.INNER);


        List<Predicate> restrictions = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            Predicate nameFilter = builder.like(joinUser.get("name"), "%" + name + "%");
            Predicate surnameFilter = builder.like(joinUser.get("surname"), "%" + name + "%");
            restrictions.add(builder.or(nameFilter, surnameFilter));
        }

        if (embg != null && !embg.isEmpty() && embg.length() == 13) {
            restrictions.add(builder.like(joinUser.get("embg"), "%" + embg + "%"));
        }

        if (event != null) {
            restrictions.add(builder.equal(joinEvent.get("id"), event.getId()));
        }

        criteriaQuery.where(builder.and(restrictions.toArray(new Predicate[0])));

        // Build and execute query
        TypedQuery<Exam> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<Exam> findExamsByEmbg(Users user) {
        String embg = user.getEmbg();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Exam> criteriaQuery = builder.createQuery(Exam.class);

        Root<Exam> root = criteriaQuery.from(Exam.class);
        Join<Exam, DonationEvent> joinEvent = root.join("donationEvent", JoinType.INNER);
        Join<Exam, Users> joinUser = root.join("patient", JoinType.INNER);


        List<Predicate> restrictions = new ArrayList<>();


        if (embg != null && !embg.isEmpty() && embg.length() == 13) {
            restrictions.add(builder.like(joinUser.get("embg"), "%" + embg + "%"));
        }

        criteriaQuery.where(builder.and(restrictions.toArray(new Predicate[0])));

        // Build and execute query
        TypedQuery<Exam> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}

