package com.assignment.customer.management.controller;

import com.assignment.customer.management.profiler.CustomerDataProfiler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/spark")
public class SparkController {

    @PostMapping("/profile-customer-data")
    public ResponseEntity<String> profileCustomerData(@RequestParam("inputFile") MultipartFile inputFile) {
        if (inputFile.isEmpty()) {
            // Handle empty file
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        CustomerDataProfiler profiler = new CustomerDataProfiler();
        // Pass the file to the profiler for analysis
        profiler.analyzeCustomerData(inputFile);

        return ResponseEntity.ok("Data profiling completed successfully");
    }
}
