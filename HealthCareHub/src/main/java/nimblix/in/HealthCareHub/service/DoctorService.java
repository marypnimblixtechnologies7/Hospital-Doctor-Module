
package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.response.DoctorAvailabilityResponse;

import java.util.List;

public interface DoctorService {

    List<String> getAllRoles();

    List<DoctorAvailabilityResponse> updateDoctorAvailabilityStatus(Long doctorId, String status);

    List<DoctorAvailabilityResponse> getDoctorAvailability(Long doctorId);
}
