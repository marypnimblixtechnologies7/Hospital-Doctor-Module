package nimblix.in.HealthCareHub.controller;

import java.util.List;

import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import nimblix.in.HealthCareHub.service.DoctorService;

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
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorRegistrationRequest request)
    {
         return ResponseEntity.ok(doctorService.registerDoctor(request));
    }
}
