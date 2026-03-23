package nimblix.in.HealthCareHub.serviceImpl;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.*;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.repository.SpecializationRepository;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DoctorProfileResponse;
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
      
        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

     
        Specialization specialization = specializationRepository
                .findByName(request.getSpecializationName())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        
        Doctor doctor = new Doctor();

        doctor.setName(request.getDoctorName());
        doctor.setEmailId(request.getDoctorEmail());
        doctor.setPassword(request.getPassword());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperience());
        doctor.setDescription(request.getDescription());
        doctor.setPhone(request.getPhoneNo());
        doctor.setConsultationFee(request.getConsultationFee());

        
        doctor.setHospital(hospital);
        doctor.setSpecialization(specialization);

        doctor.setIsActive("active");

       
        doctorRepository.save(doctor);

        return "Doctor Registered Successfully";
    }
    @Override
    public DoctorProfileResponse getDoctorById(Long doctorId) {

        // Fetch doctor details
        return doctorRepository.findDoctorProfileById(doctorId)

                // If doctor not found, throw exception
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    @Override
    public String updateDoctor(Long doctorId, DoctorRegistrationRequest request) {

        // Fetch existing doctor from DB
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Update only fields that are provided (non-null)
        if (request.getDoctorName() != null) {
            doctor.setName(request.getDoctorName());
        }

        if (request.getDoctorEmail() != null) {
            doctor.setEmailId(request.getDoctorEmail());
        }

        if (request.getQualification() != null) {
            doctor.setQualification(request.getQualification());
        }

        if (request.getExperience() != null) {
            doctor.setExperienceYears(request.getExperience());
        }

        if (request.getDescription() != null) {
            doctor.setDescription(request.getDescription());
        }

        if (request.getPhoneNo() != null) {
            doctor.setPhone(request.getPhoneNo());
        }

        if (request.getConsultationFee() != null) {
            doctor.setConsultationFee(request.getConsultationFee());
        }

        // Save updated doctor back to DB
        doctorRepository.save(doctor);

        return "Doctor updated successfully";
    }



}
