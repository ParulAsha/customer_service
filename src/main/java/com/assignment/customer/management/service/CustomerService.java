package com.assignment.customer.management.service;

import com.assignment.customer.management.entity.Customer;
import com.assignment.customer.management.exception.ResourceNotFoundException;
import com.assignment.customer.management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        // Implement validation or business logic if needed
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
    }


    public Customer updateCustomer(Long customerId, Customer customer) {
        Customer existingCustomer = getCustomerById(customerId);
        // Update existingCustomer with new data from customer
        // Implement update logic as needed
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long customerId) {
        Customer existingCustomer = getCustomerById(customerId);
        customerRepository.delete(existingCustomer);
    }
    public List<Customer> bulkLoadCustomers(List<Customer> customers) {
        // Implement bulk load logic
        // Example: Use saveAll method of JpaRepository
        return customerRepository.saveAll(customers);
    }

    public List<Customer> bulkUpdateCustomers(List<Customer> customers) {
        // Implement bulk update logic
        // Example: Iterate through the list and update each customer
        List<Customer> updatedCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            Customer updatedCustomer = customerRepository.save(customer);
            updatedCustomers.add(updatedCustomer);
        }
        return updatedCustomers;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
