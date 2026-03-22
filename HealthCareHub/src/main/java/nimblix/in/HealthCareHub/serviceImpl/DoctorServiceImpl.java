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
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nimblix.in.HealthCareHub.model.Role;
import nimblix.in.HealthCareHub.service.DoctorService;

import static nimblix.in.HealthCareHub.constants.HealthCareConstants.DOCTOR_REGISTERED_SUCCESS;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    SpecializationRepository specializationRepository;

    @Override
    public List<String> getAllRoles()
    {

        return Arrays.stream(Role.values())
                .map(Enum::name)
                .toList();
    }

    @Override
    public String registerDoctor(DoctorRegistrationRequest request)
    {
        if (doctorRepository.findByEmailId(request.getDoctorEmail()).isPresent())
        {
            return HealthCareConstants.DOCTOR_ALREADY_EXISTS;
        }
      // getting hospital from database
        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        // getting specialization using name
        Specialization specialization = specializationRepository
                .findByName(request.getSpecializationName())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        // creating doctor object
        Doctor doctor = new Doctor();

        doctor.setName(request.getDoctorName());
        doctor.setEmailId(request.getDoctorEmail());
        doctor.setPassword(request.getPassword());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperience());
        doctor.setDescription(request.getDescription());
        doctor.setPhone(request.getPhoneNo());
        doctor.setConsultationFee(request.getConsultationFee());

        // setting relations
        doctor.setHospital(hospital);
        doctor.setSpecialization(specialization);

        doctor.setIsActive("active");

        // saving doctor
        doctorRepository.save(doctor);

        return "Doctor Registered Successfully";
    }
}
