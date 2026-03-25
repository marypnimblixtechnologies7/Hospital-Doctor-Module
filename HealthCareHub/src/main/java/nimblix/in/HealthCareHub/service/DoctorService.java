
package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.model.Doctor;

import java.util.List;

public interface DoctorService {

    List<String> getAllRoles();

    String deleteDoctorDetails(Long doctorId);

    List<Doctor> getDoctorsByHospitalId(Long hospitalId);

}
