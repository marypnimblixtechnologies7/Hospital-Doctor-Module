
package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;

import java.util.List;

public interface DoctorService {

    List<String> getAllRoles();

    String registerDoctor(DoctorRegistrationRequest request);
}
