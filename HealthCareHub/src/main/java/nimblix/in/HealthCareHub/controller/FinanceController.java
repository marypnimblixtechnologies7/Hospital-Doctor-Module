package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nimblix.in.HealthCareHub.model.Payment;import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class FinanceController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/revenue/yearly")
    public double getYearlyRevenue(@RequestParam int year) {

        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);

        List<Payment> payments = entityManager
                .createQuery("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :start AND :end", Payment.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();

        return payments.stream()
                .mapToDouble(Payment::getAmount)
                .sum();
    }

    @GetMapping("/revenue/summary")
    public Map<String, Double> getRevenueSummary() {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime startOfYear = now.withDayOfYear(1).toLocalDate().atStartOfDay();

        double daily = getRevenue(startOfDay, now);
        double monthly = getRevenue(startOfMonth, now);
        double yearly = getRevenue(startOfYear, now);

        Map<String, Double> response = new HashMap<>();
        response.put("daily", daily);
        response.put("monthly", monthly);
        response.put("yearly", yearly);

        return response;
    }

    private double getRevenue(LocalDateTime start, LocalDateTime end) {

        List<Payment> payments = entityManager
                .createQuery("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :start AND :end", Payment.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();

        return payments.stream()
                .mapToDouble(Payment::getAmount)
                .sum();
    }

}
