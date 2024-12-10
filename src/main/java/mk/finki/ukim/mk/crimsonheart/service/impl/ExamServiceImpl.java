package mk.finki.ukim.mk.crimsonheart.service.impl;


import mk.finki.ukim.mk.crimsonheart.enums.Roles;
import mk.finki.ukim.mk.crimsonheart.model.Donation;
import mk.finki.ukim.mk.crimsonheart.model.Exam;
import mk.finki.ukim.mk.crimsonheart.model.Users;
import mk.finki.ukim.mk.crimsonheart.repository.DonationEventRepository;
import mk.finki.ukim.mk.crimsonheart.repository.ExamRepository;
import mk.finki.ukim.mk.crimsonheart.repository.UsersRepository;
import mk.finki.ukim.mk.crimsonheart.service.ExamService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public List<Exam> listAll() {
        return examRepository.findAll();
    }

    @Override
    public Optional<Exam> findById(Long id) {
        return examRepository.findById(id);
    }

    @Override
    public Exam save(Date performedOn, Long donationId, Long doctorId, Long patientId, Long nurseId, boolean successfulExam) {
        Users doctor = usersRepository.findById(doctorId).orElseThrow();
        Users patient = usersRepository.findById(patientId).orElseThrow();
        Users nurse = usersRepository.findById(nurseId).orElseThrow();
        Donation donation = eventRepository.findById(donationId).orElseThrow();
        Exam exam = new Exam(performedOn, donation, doctor, patient, nurse, successfulExam);

        if (!successfulExam){
            patient.setTimesRejected(patient.getTimesRejected() + 1);
            patient.setHasBeenRejected(true);
        }

        return null;
    }

    @Override
    public void deleteById(Long id) {
        this.examRepository.deleteById(id);
    }

    @Override
    public Optional<Exam> findByName(Roles role, String name) {
        if (role.equals(Roles.DOCTOR)) {
            return this.examRepository.findAllByDoctorName(name);
        }else if (role.equals(Roles.PATIENT)) {
            return this.examRepository.findAllByPatientName(name);
        } else if (role.equals(Roles.NURSE)) {
            return this.examRepository.findAllByNurseName(name);
        }else
            return this.examRepository.findAllByDoctorNameOrNurseNameOrPatientName(name, name, name);
    }
}
