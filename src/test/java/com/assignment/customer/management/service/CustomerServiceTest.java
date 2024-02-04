package com.assignment.customer.management.service;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.assignment.customer.management.entity.Customer;
import com.assignment.customer.management.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCustomerById() {
        // Mock data
        Customer customer = new Customer();
        customer.setName("John Doe");
        Optional<Customer> optionalCustomer = Optional.of(customer);

        // Define mock behavior
        when(customerRepository.findById(1L)).thenReturn(optionalCustomer);

        // Call the service method
        Customer retrievedCustomer = customerService.getCustomerById(1L);

        // Verify the result
        Assertions.assertEquals("John Doe", retrievedCustomer.getName());
    }

    @Test
    public void testGetAllCustomers() {
        // Mock data
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John Doe", "john@example.com", "1234567890", "123 Main St", "ABC Company", "Technology", "Active", "Manager"));
        customers.add(new Customer("John Doe2", "john@example2.com", "12345678902", "123 Main St2", "ABC Company", "Technology", "Active", "Manager"));

        // Define mock behavior
        when(customerRepository.findAll()).thenReturn(customers);

        // Call the service method
        List<Customer> retrievedCustomers = customerService.getAllCustomers();

        // Verify the result
        Assertions.assertEquals(2, retrievedCustomers.size());
    }

    @Test
    public void testBulkUpdateCustomers() {
        // Create sample customers
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John Doe", "john@example.com", "1234567890", "123 Main St", "ABC Company", "Technology", "Active", "Manager"));
        customers.add(new Customer("John Doe2", "john@example2.com", "12345678902", "123 Main St2", "ABC Company", "Technology", "Active", "Manager"));

        // Mock behavior of customerRepository.save(customer)
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        List<Customer> updatedCustomers = customerService.bulkUpdateCustomers(customers);

        // Verify that the customerRepository.save(customer) method is called for each customer
        verify(customerRepository, times(2)).save(any(Customer.class));

        // Verify that the updatedCustomers list size matches the input list size
        Assertions.assertEquals(customers.size(), updatedCustomers.size());

        // Verify that the updated customers match the original customers
        for (int i = 0; i < customers.size(); i++) {
            Customer originalCustomer = customers.get(i);
            Customer updatedCustomer = updatedCustomers.get(i);
            Assertions.assertEquals(originalCustomer.getCustomerId(), updatedCustomer.getCustomerId());
            Assertions.assertEquals(originalCustomer.getName(), updatedCustomer.getName());
            Assertions.assertEquals(originalCustomer.getEmail(), updatedCustomer.getEmail());
            // Add assertions for other fields as needed
        }
    }
    @Test
    public void testUpdateCustomer() {
        // Mock data
        Long customerId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(customerId);
        existingCustomer.setName("John Doe");

        // Stubbing repository method
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

        // Call the service method
        Customer updatedCustomer = customerService.updateCustomer(customerId, existingCustomer);

        // Verify that the customerRepository.save() method is called
        verify(customerRepository, times(1)).save(existingCustomer);

        // Verify the result
        Assertions.assertEquals(existingCustomer, updatedCustomer);
    }

    @Test
    public void testDeleteCustomer() {
        // Mock data
        Long customerId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomerId(customerId);

        // Stubbing repository method
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

        // Call the service method
        customerService.deleteCustomer(customerId);

        // Verify that the customerRepository.delete() method is called
        verify(customerRepository, times(1)).delete(existingCustomer);
    }

    @Test
    public void testBulkLoadCustomers() {
        // Mock data
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        // Stubbing repository method
        when(customerRepository.saveAll(customers)).thenReturn(customers);

        // Call the service method
        List<Customer> savedCustomers = customerService.bulkLoadCustomers(customers);

        // Verify that the customerRepository.saveAll() method is called
        verify(customerRepository, times(1)).saveAll(customers);

        // Verify the result
        Assertions.assertEquals(customers, savedCustomers);
    }
    @Test
    public void testCreateCustomer() {
        // Create a sample customer
        Customer customerToSave = new Customer();
        customerToSave.setName("John Doe");
        customerToSave.setEmail("john@example.com");

        // Define the behavior of customerRepository.save(customer)
        when(customerRepository.save(customerToSave)).thenReturn(customerToSave);

        // Call the service method
        Customer savedCustomer = customerService.createCustomer(customerToSave);

        // Verify that customerRepository.save(customer) is called once with the correct argument
        verify(customerRepository, times(1)).save(customerToSave);

        // Verify that the saved customer is returned
        Assertions.assertNotNull(savedCustomer);
        Assertions.assertEquals("John Doe", savedCustomer.getName());
        Assertions.assertEquals("john@example.com", savedCustomer.getEmail());
        // Add assertions for other fields as needed
    }
}
