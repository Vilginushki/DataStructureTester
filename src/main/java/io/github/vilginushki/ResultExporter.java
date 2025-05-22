package io.github.vilginushki;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResultExporter {

    public static void saveResultsToJson(String fileName, Map<String, Map<String, List<Double>>> allResults) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), allResults);
            System.out.println("Results saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving results to file: " + e.getMessage());
        }
    }
}