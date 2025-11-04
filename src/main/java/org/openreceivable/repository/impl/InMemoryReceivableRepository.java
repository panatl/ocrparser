package org.openreceivable.repository.impl;

import org.openreceivable.enums.ReceivableStatus;
import org.openreceivable.model.Receivable;
import org.openreceivable.repository.ReceivableRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of ReceivableRepository
 * This is a temporary implementation for development/testing purposes.
 * In production, this should be replaced with a proper database-backed implementation.
 */
@Repository
public class InMemoryReceivableRepository implements ReceivableRepository {
    
    private final Map<String, Receivable> receivables = new ConcurrentHashMap<>();
    
    @Override
    public Receivable save(Receivable receivable) {
        if (receivable == null) {
            throw new IllegalArgumentException("Receivable cannot be null");
        }
        
        if (receivable.getReceivableId() == null || receivable.getReceivableId().isEmpty()) {
            receivable.setReceivableId(UUID.randomUUID().toString());
        }
        
        receivables.put(receivable.getReceivableId(), receivable);
        return receivable;
    }
    
    @Override
    public Optional<Receivable> findById(String receivableId) {
        if (receivableId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(receivables.get(receivableId));
    }
    
    @Override
    public List<Receivable> findByCustomerId(String customerId) {
        if (customerId == null) {
            return new ArrayList<>();
        }
        return receivables.values().stream()
                .filter(receivable -> customerId.equals(receivable.getCustomerId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Receivable> findByContractId(String contractId) {
        if (contractId == null) {
            return new ArrayList<>();
        }
        return receivables.values().stream()
                .filter(receivable -> contractId.equals(receivable.getContractId()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Receivable> findByStatus(ReceivableStatus status) {
        if (status == null) {
            return new ArrayList<>();
        }
        return receivables.values().stream()
                .filter(receivable -> status.equals(receivable.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Receivable> findOverdue(LocalDateTime asOfDate) {
        if (asOfDate == null) {
            asOfDate = LocalDateTime.now();
        }
        final LocalDateTime finalAsOfDate = asOfDate;
        return receivables.values().stream()
                .filter(receivable -> {
                    LocalDateTime dueDate = receivable.getDueDate();
                    return dueDate != null && 
                           dueDate.isBefore(finalAsOfDate) &&
                           receivable.getStatus() != ReceivableStatus.PAID;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Receivable> findByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            return new ArrayList<>();
        }
        return receivables.values().stream()
                .filter(receivable -> {
                    LocalDateTime dueDate = receivable.getDueDate();
                    return dueDate != null && 
                           !dueDate.isBefore(startDate) && 
                           !dueDate.isAfter(endDate);
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Receivable> findAll() {
        return new ArrayList<>(receivables.values());
    }
    
    @Override
    public void delete(String receivableId) {
        if (receivableId != null) {
            receivables.remove(receivableId);
        }
    }
    
    @Override
    public boolean exists(String receivableId) {
        return receivableId != null && receivables.containsKey(receivableId);
    }
}
