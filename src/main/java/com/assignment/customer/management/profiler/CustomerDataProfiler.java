package com.assignment.customer.management.profiler;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class CustomerDataProfiler {

    public void analyzeCustomerData(MultipartFile inputFile) {
        SparkSession spark = SparkSession.builder()
                .appName("CustomerDataProfiler")
                .master("local[*]") // Set Spark master URL
                .getOrCreate();

        try {
            // Save the multipart file to a temporary location
            String tempFilePath = "/path/to/temp/file.csv"; // Define a temporary file path
            inputFile.transferTo(new File(tempFilePath));

            // Load customer data from the temporary file
            Dataset<Row> customerData = spark.read().format("csv").option("header", "true").load(tempFilePath);

            // Count records
            long recordCount = customerData.count();
            System.out.println("Total number of records: " + recordCount);

            // Identify missing values for each column
            for (String columnName : customerData.columns()) {
                long missingValuesCount = customerData.filter(customerData.col(columnName).isNull()).count();
                System.out.println("Number of missing values in column " + columnName + ": " + missingValuesCount);
            }

            // Add more profiling logic as needed

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred during data analysis: " + e.getMessage());
        } finally {
            // Stop the SparkSession to release resources
            spark.stop();
        }
    }
}
