package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.request.DoctorAvailabilityRequest;
import java.util.List;

public interface DoctorAvailabilityService {
    String setAvailability(DoctorAvailabilityRequest request);
    String createSchedule(Long doctorId, List<DoctorAvailabilityRequest> requests);
}