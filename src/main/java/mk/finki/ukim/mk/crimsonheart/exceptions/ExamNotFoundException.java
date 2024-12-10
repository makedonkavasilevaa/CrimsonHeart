package mk.finki.ukim.mk.crimsonheart.exceptions;

public class ExamNotFoundException extends RuntimeException {
  public ExamNotFoundException(Long examId) {
    super(String.format("Exam with id %s not found", examId));
  }
}
