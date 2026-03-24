package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.model.DoctorAvailability;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.repository.DoctorAvailabilityRepository;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.request.DoctorAvailabilityRequest;
import nimblix.in.HealthCareHub.service.DoctorAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {

    @Autowired
    private DoctorAvailabilityRepository availabilityRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public String setAvailability(DoctorAvailabilityRequest request) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        boolean overlap = availabilityRepository.existsOverlappingSlot(
                doctor.getId(),
                request.getAvailableDate(),
                request.getStartTime(),
                request.getEndTime()
        );

        if (overlap) return "Slot overlaps with existing availability";

        DoctorAvailability availability = new DoctorAvailability();
        availability.setDoctor(doctor);
        availability.setAvailableDate(request.getAvailableDate());
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());
        availability.setIsAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true);

        availabilityRepository.save(availability);
        return "Availability set successfully";
    }

    @Override
    public String createSchedule(Long doctorId, List<DoctorAvailabilityRequest> requests) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        for (DoctorAvailabilityRequest request : requests) {
            boolean overlap = availabilityRepository.existsOverlappingSlot(
                    doctor.getId(),
                    request.getAvailableDate(),
                    request.getStartTime(),
                    request.getEndTime()
            );

            if (!overlap) {
                DoctorAvailability availability = new DoctorAvailability();
                availability.setDoctor(doctor);
                availability.setAvailableDate(request.getAvailableDate());
                availability.setStartTime(request.getStartTime());
                availability.setEndTime(request.getEndTime());
                availability.setIsAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true);
                availabilityRepository.save(availability);
            }
        }
        return "Schedule created successfully";
    }
}