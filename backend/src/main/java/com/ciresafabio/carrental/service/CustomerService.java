package com.ciresafabio.carrental.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ciresafabio.carrental.domain.model.Customer;
import com.ciresafabio.carrental.repository.CustomerRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepo.findById(id);
    }

    public Customer saveCustomer(Customer customer) {
        // TODO validate before saving, e.g.: only allow if all required fields present
        return customerRepo.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
        // TODO mappers with POJO DTOs
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        return customerRepo.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
        customerRepo.delete(customer);
    }

}
