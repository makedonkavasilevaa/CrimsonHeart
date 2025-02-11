package mk.finki.ukim.mk.crimsonheart.service.impl;


import mk.finki.ukim.mk.crimsonheart.exceptions.DonationEventNotFoundException;
import mk.finki.ukim.mk.crimsonheart.exceptions.ExamNotFoundException;
import mk.finki.ukim.mk.crimsonheart.exceptions.UsersNotFoundException;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;
import mk.finki.ukim.mk.crimsonheart.model.Exam;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.DonationEventRepository;
import mk.finki.ukim.mk.crimsonheart.repository.ExamRepository;
import mk.finki.ukim.mk.crimsonheart.repository.UsersRepository;
import mk.finki.ukim.mk.crimsonheart.service.ExamService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final UsersRepository usersRepository;
    private final DonationEventRepository eventRepository;

    public ExamServiceImpl(ExamRepository examRepository, UsersRepository usersRepository, DonationEventRepository eventRepository) {
        this.examRepository = examRepository;
        this.usersRepository = usersRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void create(Date performedOn, String bloodPressure, Float hemoglobin, Long donationEventId, Long doctorId, Long patientId, Long nurseID, boolean successfulExam, String comment) {
        Users doctor = this.usersRepository.findById(doctorId).orElseThrow( () -> new UsersNotFoundException(doctorId));
        Users patient = this.usersRepository.findById(patientId).orElseThrow(() -> new UsersNotFoundException(patientId));
        Users nurse = this.usersRepository.findById(nurseID).orElseThrow( () -> new UsersNotFoundException(nurseID));
        DonationEvent donationEvent = this.eventRepository.findById(donationEventId).orElseThrow(() -> new DonationEventNotFoundException(donationEventId));
        Exam exam = new Exam(performedOn, bloodPressure, hemoglobin, donationEvent, doctor, patient, nurse, successfulExam, comment );

        if (!successfulExam){
            patient.setTimesRejected(patient.getTimesRejected() + 1);
            patient.setHasBeenRejected(true);
        }

        this.examRepository.save(exam);
    }

    @Override
    public void update(Long examId, Date performedOn, String bloodPressure, Float hemoglobin, Long donationEventId, Long doctorId, Long patientId, Long nurseID, boolean successfulExam, String comment) {
        Users doctor = this.usersRepository.findById(doctorId).orElseThrow( () -> new UsersNotFoundException(doctorId));
        Users patient = this.usersRepository.findById(patientId).orElseThrow(() -> new UsersNotFoundException(patientId));
        Users nurse = this.usersRepository.findById(nurseID).orElseThrow( () -> new UsersNotFoundException(nurseID));
        DonationEvent donationEvent = this.eventRepository.findById(donationEventId).orElseThrow(() -> new DonationEventNotFoundException(donationEventId));
        Exam exam = this.examRepository.findById(examId).orElseThrow( () -> new ExamNotFoundException(examId));

        exam.setPerformedOn(performedOn);
        exam.setBloodPressure(bloodPressure);
        exam.setHemoglobin(hemoglobin);
        exam.setDonationEvent(donationEvent);
        exam.setDoctor(doctor);
        exam.setPatient(patient);
        exam.setNurse(nurse);
        exam.setComment(comment);
        exam.setSuccessfulExam(successfulExam);
        if (!successfulExam){
            patient.setTimesRejected(patient.getTimesRejected() + 1);
            patient.setHasBeenRejected(true);
        }
        this.examRepository.save(exam);
    }

    @Override
    public List<Exam> listAll() {
        return this.examRepository.findAll();
    }

    @Override
    public Exam findById(Long id) {
        return this.examRepository.findById(id).orElseThrow(() -> new ExamNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        this.examRepository.deleteById(id);
    }

    @Override
    public List<Exam> findByName(String name) {
        List<Exam> exams = new ArrayList<>();
        if (name != null || name != "")
            exams = this.examRepository.findAllByPatientNameContainsOrPatientSurnameContains(name, name);
        else
            exams = this.examRepository.findAll();
        return exams;
    }

    @Override
    public List<Exam> findByEvent(Long eventId) {
        if (eventId != null){
            DonationEvent event = eventRepository.findById(eventId).orElseThrow(() -> new DonationEventNotFoundException(eventId));
            return this.examRepository.findAllByDonationEvent(event);
        }else
            return this.examRepository.findAll();
    }

    @Override
    public List<Exam> findByPatientEmbg(String patientEmbg) {
        if (patientEmbg != null && patientEmbg != "" && patientEmbg.length() == 13){
            return this.examRepository.findAllByPatientEmbg(patientEmbg);
        }else
            return this.examRepository.findAll();
    }

    @Override
    public List<Exam> findByNameAndEvent(Long eventId, String name) {
        if (eventId != null && name != null || name != ""){
            DonationEvent event = eventRepository.findById(eventId).orElseThrow(() -> new DonationEventNotFoundException(eventId));
            return this.examRepository.findAllByDonationEventAndPatientNameContainsOrPatientSurnameContains(event, name, name);
        }else
            return this.examRepository.findAll();
    }

    @Override
    public List<Exam> findByNameAndEmbg(String name, String embg) {

        if (name != null && embg != null && embg.length() == 13){
            return this.examRepository.findAllByPatientNameContainsOrPatientSurnameContainsAndPatientEmbg(name, name, embg);
        }else
            return this.examRepository.findAll();
    }

    @Override
    public List<Exam> findByPatientEmbgAndEvent(String embg, Long eventId) {
        if (embg != null && embg != "" && embg.length() == 13 && eventId != null){
            DonationEvent event = this.eventRepository.findById(eventId).orElseThrow(() -> new DonationEventNotFoundException(eventId));
            return this.examRepository.findAllByPatientEmbgAndDonationEvent(embg, event);
        }else
          return this.examRepository.findAll();
    }

    @Override
    public List<Exam> findByNameAndPatientEmbgAndEvent(String name, String embg, Long eventId) {
        if (embg != null && embg != "" && embg.length() == 13 && eventId != null && name != null) {
            DonationEvent event = this.eventRepository.findById(eventId).orElseThrow(() -> new DonationEventNotFoundException(eventId));
            return this.examRepository.findAllByPatientEmbgAndDonationEventAndPatientNameContainsOrPatientSurnameContains(embg, event, name, name);
        }else
            return this.examRepository.findAll();
    }
}
