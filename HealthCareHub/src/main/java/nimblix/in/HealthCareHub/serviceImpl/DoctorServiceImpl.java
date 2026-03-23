package nimblix.in.HealthCareHub.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.DoctorAvailability;
import nimblix.in.HealthCareHub.model.Role;
import nimblix.in.HealthCareHub.repository.DoctorAvailabilityRepository;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.response.DoctorAvailabilityResponse;
import nimblix.in.HealthCareHub.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorAvailabilityRepository doctorAvailabilityRepository;

    @Override
    public List<String> getAllRoles() {
        return Arrays.stream(Role.values())
                .map(Enum::name)
                .toList();
    }

    @Override
    public List<DoctorAvailabilityResponse> updateDoctorAvailabilityStatus(Long doctorId, String status) {

        // Step 1: Check doctor exists
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException(HealthCareConstants.DOCTOR_NOT_FOUND));

        // Step 2: Validate status
        if (!status.equalsIgnoreCase(HealthCareConstants.ACTIVE) &&
                !status.equalsIgnoreCase(HealthCareConstants.IN_ACTIVE)) {
            throw new RuntimeException("Invalid status value");
        }

        // Step 3: Convert status → boolean
        boolean isAvailable = status.equalsIgnoreCase(HealthCareConstants.ACTIVE);

        // Step 4: Fetch availability list
        List<DoctorAvailability> availabilityList =
                doctorAvailabilityRepository.findByDoctor_Id(doctorId);

        // Step 5: Update all records
        for (DoctorAvailability slot : availabilityList) {
            slot.setAvailable(isAvailable);
        }

        // Step 6: Save updated records
        List<DoctorAvailability> updatedList =
                doctorAvailabilityRepository.saveAll(availabilityList);

        // Step 7: Convert to DTO
        List<DoctorAvailabilityResponse> responseList = new ArrayList<>();

        for (DoctorAvailability slot : updatedList) {
            DoctorAvailabilityResponse response = DoctorAvailabilityResponse.builder()
                    .id(slot.getId())
                    .doctorId(doctor.getId())
                    .doctorName(doctor.getName())
                    .availableDate(slot.getAvailableDate())
                    .startTime(slot.getStartTime())
                    .endTime(slot.getEndTime())
                    .available(slot.isAvailable())
                    .createdTime(slot.getCreatedTime())
                    .updatedTime(slot.getUpdatedTime())
                    .build();

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public List<DoctorAvailabilityResponse> getDoctorAvailability(Long doctorId) {

        List<DoctorAvailability> availabilityList = doctorAvailabilityRepository.findByDoctor_Id(doctorId);

        return availabilityList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Mapper method for doctor Availability check
    private DoctorAvailabilityResponse mapToResponse(DoctorAvailability availability){
        DoctorAvailabilityResponse response = new DoctorAvailabilityResponse();

        response.setId(availability.getId());
        response.setDoctorId(availability.getDoctor().getId());


        response.setDoctorName(availability.getDoctor().getName());
        response.setAvailableDate(availability.getAvailableDate());
        response.setStartTime(availability.getStartTime());
        response.setEndTime(availability.getEndTime());

        // Assuming your entity has a boolean field like "available" or "status"
        response.setAvailable(availability.isAvailable());

        // If you are using LocalDateTime in  entity, conver to String
        response.setCreatedTime(
                availability.getCreatedTime()!=null? availability.getCreatedTime().toString() : null
        );
        response.setUpdatedTime(
                availability.getUpdatedTime()!=null?availability.getUpdatedTime().toString():null
        );
        return response;
    }

}