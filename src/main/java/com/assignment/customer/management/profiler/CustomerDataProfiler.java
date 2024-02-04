package com.assignment.customer.management.profiler;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.web.multipart.MultipartFile;
import scala.collection.Seq;


public class CustomerDataProfiler {

    public void analyzeCustomerData(MultipartFile inputFile) {
        SparkSession spark = SparkSession.builder()
                .appName("CustomerDataProfiler")
                .master("local[*]") // Set Spark master URL
                .getOrCreate();

        // Load customer data from input file
        Dataset<Row> customerData = spark.read().format("csv").option("header", "true").load((Seq<String>) inputFile);

        // Count records
        long recordCount = customerData.count();
        System.out.println("Total number of records: " + recordCount);

        // Identify missing values
        long missingValuesCount = customerData.filter((FilterFunction<Row>) Row::anyNull).count();
        ;
        System.out.println("Number of missing values: " + missingValuesCount);

        // Add more profiling logic as needed

        spark.stop();
    }
}
