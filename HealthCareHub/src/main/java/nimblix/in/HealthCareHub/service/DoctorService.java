
package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DoctorRegistrationResponse;

import java.util.List;

public interface DoctorService {

    List<String> getAllRoles();

    DoctorRegistrationResponse registerDoctor(DoctorRegistrationRequest request);

    String addDoctorToHospital(Long doctorId, Long hospitalId);
}
