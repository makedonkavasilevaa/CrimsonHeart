package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Exam;

import java.util.List;

public interface ExamCustomRepository {

    List<Exam> filterExams(String name, String embg, DonationEvent event);
}
