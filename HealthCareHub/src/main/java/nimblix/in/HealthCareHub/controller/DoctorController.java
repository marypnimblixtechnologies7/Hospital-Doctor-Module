package nimblix.in.HealthCareHub.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.response.DoctorAvailabilityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import nimblix.in.HealthCareHub.service.DoctorService;

@RestController
@RequestMapping("api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getAllRoles() {

        return ResponseEntity.ok(doctorService.getAllRoles());
    }

    // update the doctor status
    @PutMapping("/{doctorId}/status")
    public ResponseEntity<Map<String, Object>> updateDoctorAvailability(
            @PathVariable Long doctorId,
            @RequestParam String status
    ){
        List<DoctorAvailabilityResponse>responseList=
                doctorService.updateDoctorAvailabilityStatus(doctorId,status);

        Map<String, Object>response=new HashMap<>();
        response.put(HealthCareConstants.STATUS,HealthCareConstants.STATUS_SUCCESS);
        response.put(HealthCareConstants.MESSAGE,"Doctor availability updated successfully");
        response.put(HealthCareConstants.DATA,responseList);

        return ResponseEntity.ok(response);
    }

    // check the doctor availability
    @GetMapping("/{doctorId}/availability")
    public  ResponseEntity<Map<String, Object>>getDoctorAvailability(
            @PathVariable Long doctorId
    ){
        List<DoctorAvailabilityResponse>responseList=doctorService.getDoctorAvailability(doctorId);

        Map<String, Object>response = new HashMap<>();
        response.put(HealthCareConstants.STATUS, HealthCareConstants.STATUS_SUCCESS);
        response.put(HealthCareConstants.MESSAGE,"Doctor Availability fetched successfully");
        response.put(HealthCareConstants.DATA, responseList);

        return ResponseEntity.ok(response);
    }
}
