package nimblix.in.HealthCareHub.serviceImpl;

import java.util.Arrays;
import java.util.List;

import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Specialization;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.repository.SpecializationRepository;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DoctorRegistrationResponse;
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import nimblix.in.HealthCareHub.model.Role;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.web.bind.annotation.PostMapping;

import static nimblix.in.HealthCareHub.constants.HealthCareConstants.DOCTOR_REGISTERED_SUCCESS;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public List<String> getAllRoles() {
        return Arrays.stream(Role.values())
                .map(Enum::name)
                .toList();
    }

    //doctor registration
    @Override
    public DoctorRegistrationResponse registerDoctor(DoctorRegistrationRequest request) {

        // check doctor already exists
        if (doctorRepository.findByEmailId(request.getDoctorEmail()).isPresent()) {
            throw new IllegalArgumentException(HealthCareConstants.DOCTOR_ALREADY_EXISTS);
        }

        // get hospital
        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new RuntimeException(HealthCareConstants.HOSPITAL_NOT_FOUND));

        // get specialization
        Specialization specialization = specializationRepository
                .findByName(request.getSpecializationName())
                .orElseThrow(() -> new RuntimeException(HealthCareConstants.SPECIALIZATION_NOT_FOUND));

        // create doctor object
        Doctor doctor = new Doctor();

        doctor.setName(request.getDoctorName());
        doctor.setEmailId(request.getDoctorEmail());
        doctor.setPassword(passwordEncoder.encode(request.getPassword()));
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperience());
        doctor.setDescription(request.getDescription());
        doctor.setPhone(request.getPhoneNo());
        doctor.setConsultationFee(request.getConsultationFee());

        // set relations
        doctor.setHospital(hospital);
        doctor.setSpecialization(specialization);
        doctor.setIsActive("active");

        // save doctor
        Doctor savedDoctor = doctorRepository.save(doctor);

        // response (simple like your style)
        DoctorRegistrationResponse response = new DoctorRegistrationResponse();
        response.setDoctorId(savedDoctor.getId());
        response.setDoctorName(savedDoctor.getName());
        response.setDoctorEmail(savedDoctor.getEmailId());
        response.setConsultationFee(savedDoctor.getConsultationFee());
        response.setSpecialization(specialization.getName());
        response.setHospitalName(hospital.getName());
        response.setHospitalId(hospital.getId());
        response.setQualification(savedDoctor.getQualification());
        response.setExperienceYears(savedDoctor.getExperienceYears());
        response.setMessage("Doctor Registered Successfully");

        return response;
    }

    //adding doctor to a hospital
    @Override
    public String addDoctorToHospital(Long doctorId, Long hospitalId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        doctor.setHospital(hospital);

        doctorRepository.save(doctor);

        return "Doctor " + doctor.getName() +
                " assigned to " + hospital.getName() +
                " hospital successfully";
    }

}

