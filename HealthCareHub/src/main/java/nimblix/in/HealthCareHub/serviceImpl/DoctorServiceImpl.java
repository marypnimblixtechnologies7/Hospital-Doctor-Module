package nimblix.in.HealthCareHub.serviceImpl;


import java.util.List;
import java.util.Arrays;

import nimblix.in.HealthCareHub.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nimblix.in.HealthCareHub.model.Role;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<String> getAllRoles() {

        return Arrays.stream(Role.values())
                .map(Enum::name)
                .toList();
    }

    @Override
    public String  deleteDoctorDetails(Long doctorId) {
        Doctor doctor= doctorRepository.findById(doctorId).orElse(null);
    if(doctor == null){
        return "Doctor not found";
    }
    doctorRepository.delete(doctor);
    return "Doctor deleted successfully";
}
  @Override
public List<Doctor>getDoctorsByHospitalId(Long hospitalId) {

        return doctorRepository.findByHospitalId(hospitalId);
  }
}

