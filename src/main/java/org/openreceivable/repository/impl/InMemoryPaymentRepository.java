package org.openreceivable.repository.impl;

import org.openreceivable.enums.PaymentStatus;
import org.openreceivable.model.Payment;
import org.openreceivable.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of PaymentRepository
 * This is a temporary implementation for development/testing purposes.
 * In production, this should be replaced with a proper database-backed implementation.
 */
@Repository
public class InMemoryPaymentRepository implements PaymentRepository {
    
    private final Map<String, Payment> payments = new ConcurrentHashMap<>();
    
    @Override
    public Payment save(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }
        
        if (payment.getPaymentId() == null || payment.getPaymentId().isEmpty()) {
            payment.setPaymentId(UUID.randomUUID().toString());
        }
        
        payments.put(payment.getPaymentId(), payment);
        return payment;
    }
    
    @Override
    public Optional<Payment> findById(String paymentId) {
        if (paymentId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(payments.get(paymentId));
    }
    
    @Override
    public List<Payment> findByCustomerId(String customerId) {
        if (customerId == null) {
            return new ArrayList<>();
        }
        return payments.values().stream()
                .filter(payment -> customerId.equals(payment.getCustomerId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Payment> findByReceivableId(String receivableId) {
        if (receivableId == null) {
            return new ArrayList<>();
        }
        return payments.values().stream()
                .filter(payment -> receivableId.equals(payment.getReceivableId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Payment> findByStatus(PaymentStatus status) {
        if (status == null) {
            return new ArrayList<>();
        }
        return payments.values().stream()
                .filter(payment -> status.equals(payment.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            return new ArrayList<>();
        }
        return payments.values().stream()
                .filter(payment -> {
                    LocalDateTime paymentDate = payment.getPaymentDate();
                    return paymentDate != null && 
                           !paymentDate.isBefore(startDate) && 
                           !paymentDate.isAfter(endDate);
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Payment> findAll() {
        return new ArrayList<>(payments.values());
    }
    
    @Override
    public void delete(String paymentId) {
        if (paymentId != null) {
            payments.remove(paymentId);
        }
    }
    
    @Override
    public boolean exists(String paymentId) {
        return paymentId != null && payments.containsKey(paymentId);
    }
}
