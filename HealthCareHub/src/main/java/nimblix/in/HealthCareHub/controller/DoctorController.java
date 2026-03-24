package nimblix.in.HealthCareHub.controller;

import java.util.List;

import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.Patient;
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
        // service to fetch doctor details
        DoctorProfileResponse doctor = doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(doctor);
    }

    // update doctor details by ID
    @PatchMapping("/{doctorId}")
    public ResponseEntity<String> updateDoctor(
            @PathVariable Long doctorId,
            @RequestBody DoctorRegistrationRequest request) {
        // service layer to update doctor
        String response = doctorService.updateDoctor(doctorId, request);
        return ResponseEntity.ok(response);
    }

    // --- NEW UPDATED API ENDPOINTS ---

    // 1. Get doctors list for a hospital
    @GetMapping("/hospitals/{hospitalId}/doctors")
    public ResponseEntity<List<Doctor>> getDoctorsByHospital(@PathVariable Long hospitalId) {
        List<Doctor> doctors = doctorService.getDoctorsByHospital(hospitalId);
        return ResponseEntity.ok(doctors);
    }

    // 2. Get patients seen by doctor in a month
    @GetMapping("/patients-seen/{doctorId}/{month}")
    public ResponseEntity<List<Patient>> getPatientsByDoctorAndMonth(
            @PathVariable Long doctorId,
            @PathVariable int month) {
        List<Patient> patients = doctorService.getPatientsSeenByDoctorInMonth(doctorId, month);
        return ResponseEntity.ok(patients);
    }
}