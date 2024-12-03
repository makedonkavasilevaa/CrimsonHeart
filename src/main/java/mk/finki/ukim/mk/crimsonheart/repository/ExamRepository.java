package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
}
