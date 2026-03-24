package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * FIX: Updated table to 'appointments' and column to 'appointment_date_time'
     * to match your Appointment.java and MySQL Workbench.
     */
    @Query(value = "SELECT p.* FROM patients p " +
            "JOIN appointments a ON p.id = a.patient_id " +
            "WHERE a.doctor_id = :doctorId " +
            "AND MONTH(STR_TO_DATE(a.appointment_date_time, '%Y-%m-%d')) = :month",
            nativeQuery = true)
    List<Patient> findPatientsByDoctorAndMonth(@Param("doctorId") Long doctorId, @Param("month") int month);
}