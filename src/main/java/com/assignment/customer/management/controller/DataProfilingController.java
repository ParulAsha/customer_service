package com.assignment.customer.management.controller;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class DataProfilingController {

    @GetMapping("/data-profiling")
    public ResponseEntity<String> performDataProfiling() {
        // Initialize Spark session
        SparkSession spark = SparkSession.builder()
                .appName("CustomerDataProfiling")
                .master("local[*]") // Use local mode for demonstration purposes
                .getOrCreate();

        try {
            // Load customer data into a Spark DataFrame (replace this with your actual data source)
            Dataset<Row> customerData = spark.read()
                    .format("csv")
                    .option("header", "true")
                    .load("../resources/customerdata.csv");

            // Perform data profiling operations using Spark DataFrame APIs
            long totalRecords = customerData.count();
            long[] missingValuesCounts = new long[customerData.columns().length];

            for (int i = 0; i < customerData.columns().length; i++) {
                missingValuesCounts[i] = customerData.filter(customerData.col(customerData.columns()[i]).isNull()).count();
            }

            // Format profiling insights into a JSON response
            StringBuilder profilingResults = new StringBuilder();
            profilingResults.append("{\n");
            profilingResults.append("\"total_records\": ").append(totalRecords).append(",\n");
            profilingResults.append("\"missing_values_counts\": {\n");
            for (int i = 0; i < customerData.columns().length; i++) {
                profilingResults.append("\"").append(customerData.columns()[i]).append("\": ").append(missingValuesCounts[i]);
                if (i < customerData.columns().length - 1) {
                    profilingResults.append(",\n");
                } else {
                    profilingResults.append("\n");
                }
            }
            profilingResults.append("}\n");
            profilingResults.append("}");

            return ResponseEntity.ok(profilingResults.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during data profiling: " + e.getMessage());
        } finally {
            // Stop the SparkSession to release resources
            spark.stop();
        }
    }
}
