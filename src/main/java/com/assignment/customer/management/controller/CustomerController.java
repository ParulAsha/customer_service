package com.assignment.customer.management.controller;

import com.assignment.customer.management.entity.Customer;
import com.assignment.customer.management.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(customerId, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk-load")
    public ResponseEntity<List<Customer>> bulkLoadCustomers(@RequestBody List<Customer> customers) {
        List<Customer> loadedCustomers = customerService.bulkLoadCustomers(customers);
        return new ResponseEntity<>(loadedCustomers, HttpStatus.CREATED);
    }

    @PutMapping("/bulk-update")
    public ResponseEntity<List<Customer>> bulkUpdateCustomers(@RequestBody List<Customer> customers) {
        List<Customer> updatedCustomers = customerService.bulkUpdateCustomers(customers);
        return ResponseEntity.ok(updatedCustomers);
    }
}
