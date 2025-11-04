package org.openreceivable.repository.impl;

import org.openreceivable.enums.CustomerStatus;
import org.openreceivable.model.Customer;
import org.openreceivable.repository.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of CustomerRepository
 * This is a temporary implementation for development/testing purposes.
 * In production, this should be replaced with a proper database-backed implementation.
 */
@Repository
public class InMemoryCustomerRepository implements CustomerRepository {
    
    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    
    @Override
    public Customer save(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        
        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            customer.setCustomerId(UUID.randomUUID().toString());
        }
        
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }
    
    @Override
    public Optional<Customer> findById(String customerId) {
        if (customerId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(customers.get(customerId));
    }
    
    @Override
    public Optional<Customer> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        return customers.values().stream()
                .filter(customer -> email.equals(customer.getEmail()))
                .findFirst();
    }
    
    @Override
    public Optional<Customer> findByTaxId(String taxId) {
        if (taxId == null) {
            return Optional.empty();
        }
        return customers.values().stream()
                .filter(customer -> taxId.equals(customer.getTaxId()))
                .findFirst();
    }
    
    @Override
    public List<Customer> findByStatus(CustomerStatus status) {
        if (status == null) {
            return new ArrayList<>();
        }
        return customers.values().stream()
                .filter(customer -> status.equals(customer.getStatus()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }
    
    @Override
    public void delete(String customerId) {
        if (customerId != null) {
            customers.remove(customerId);
        }
    }
    
    @Override
    public boolean exists(String customerId) {
        return customerId != null && customers.containsKey(customerId);
    }
}
