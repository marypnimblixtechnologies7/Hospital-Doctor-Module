package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.Patient;
import nimblix.in.HealthCareHub.model.Review;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DoctorProfileResponse;

import java.util.List;

public interface DoctorService {

    List<String> getAllRoles();

    String registerDoctor(DoctorRegistrationRequest request);

    // get doctor details by doctor Id
    DoctorProfileResponse getDoctorById(Long doctorId);

    // Update doctor details
    String updateDoctor(Long doctorId, DoctorRegistrationRequest request);

    // Get doctors list for a hospital
    List<Doctor> getDoctorsByHospital(Long hospitalId);

    // Get patients seen by doctor in a month
    List<Patient> getPatientsSeenByDoctorInMonth(Long doctorId, int month);
}