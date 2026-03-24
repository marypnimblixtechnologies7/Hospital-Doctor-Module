package nimblix.in.HealthCareHub.controller;

import nimblix.in.HealthCareHub.service.DoctorAvailabilityService;

import java.util.List;
import nimblix.in.HealthCareHub.request.DoctorAvailabilityRequest;

import nimblix.in.HealthCareHub.model.Review;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import nimblix.in.HealthCareHub.service.DoctorService;
import nimblix.in.HealthCareHub.response.DoctorProfileResponse;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorAvailabilityService doctorAvailabilityService;

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getAllRoles() {
        return ResponseEntity.ok(doctorService.getAllRoles());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorRegistrationRequest request) {
        return ResponseEntity.ok(doctorService.registerDoctor(request));
    }

    // to get doctor details by ID
    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorProfileResponse> getDoctorById(@PathVariable Long doctorId) {
        DoctorProfileResponse doctor = doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(doctor);
    }

    // update doctor details by ID
    @PatchMapping("/{doctorId}")
    public ResponseEntity<String> updateDoctor(
            @PathVariable Long doctorId,
            @RequestBody DoctorRegistrationRequest request) {

        String response = doctorService.updateDoctor(doctorId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/availability")
    public ResponseEntity<String> setDoctorAvailability(@RequestBody DoctorAvailabilityRequest request) {
        return ResponseEntity.ok(doctorAvailabilityService.setAvailability(request));
    }

    @PostMapping("/{doctorId}/schedule")
    public ResponseEntity<String> createDoctorSchedule(
            @PathVariable Long doctorId,
            @RequestBody List<DoctorAvailabilityRequest> scheduleRequests
    ) {
        return ResponseEntity.ok(doctorAvailabilityService.createSchedule(doctorId, scheduleRequests));
    }
}