package nimblix.in.HealthCareHub.controller;

import java.util.List;

import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getAllRoles() {

        return ResponseEntity.ok(doctorService.getAllRoles());
    }
    @DeleteMapping("/deleteDoctorDetails/{doctorId}")
    public ResponseEntity<String>deleteDoctorDetails(@PathVariable Long doctorId){
        return
    ResponseEntity.ok(doctorService.deleteDoctorDetails((doctorId)));
    }
    @GetMapping("/getDoctorsByHospitalId/{hospitalId}")
    public ResponseEntity<List<Doctor>>
    getDoctorsByHospitalId(@PathVariable Long hospitalId){
        return
    ResponseEntity.ok(doctorService.getDoctorsByHospitalId(hospitalId));
    }

}
