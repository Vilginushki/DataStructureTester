package io.github.vilginushki;

import io.github.vilginushki.structures.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.*;

public class DataStructureBenchmark {

    public enum DataGenerationStrategy {
        RANDOM_UNIFORM, RANDOM_GAUSSIAN, RANDOM_EXPONENTIAL, SORTED, REVERSED, PARTIALLY_SORTED
    }

    public static List<Integer> generateData(int size, DataGenerationStrategy strategy) {
        List<Integer> data = new ArrayList<>();
        Random rand = new Random();

        switch (strategy) {
            case RANDOM_UNIFORM -> {
                for (int i = 0; i < size; i++) data.add(rand.nextInt(size));
            }
            case RANDOM_GAUSSIAN -> {
                for (int i = 0; i < size; i++) {
                    int value = (int) (rand.nextGaussian() * size / 6 + size / 2);
                    data.add(Math.max(0, Math.min(size - 1, value))); // Ensure within bounds
                }
            }
            case RANDOM_EXPONENTIAL -> {
                for (int i = 0; i < size; i++) {
                    int value = (int) (-Math.log(1 - rand.nextDouble()) * (size / 10));
                    data.add(Math.min(value, size - 1)); // Ensure within bounds
                }
            }
            case SORTED -> {
                for (int i = 0; i < size; i++) data.add(i);
            }
            case REVERSED -> {
                for (int i = size - 1; i >= 0; i--) data.add(i);
            }
            case PARTIALLY_SORTED -> {
                for (int i = 0; i < size / 2; i++) data.add(i);
                for (int i = size / 2; i < size; i++) data.add(rand.nextInt(size));
            }
        }
        return data;
    }

    public static <T> Map<String, List<Double>> benchmark(Class<? extends DataStructure> structureClass, List<T> data, int trials) {
        Map<String, List<Double>> results = new HashMap<>();
        results.put("Insert Time", new ArrayList<>());
        results.put("Search Time", new ArrayList<>());
        results.put("Remove Time", new ArrayList<>());

        for (int t = 0; t < trials; t++) {
            try {
                DataStructure<T> structure = structureClass.getDeclaredConstructor().newInstance();

                long startInsert = System.nanoTime();
                for (T value : data) structure.insert(value);
                long endInsert = System.nanoTime();

                long startSearch = System.nanoTime();
                for (T value : data) structure.search(value);
                long endSearch = System.nanoTime();

                long startRemove = System.nanoTime();
                for (T value : data) structure.remove(value);
                long endRemove = System.nanoTime();

                results.get("Insert Time").add((endInsert - startInsert) / 1e6);
                results.get("Search Time").add((endSearch - startSearch) / 1e6);
                results.get("Remove Time").add((endRemove - startRemove) / 1e6);
            } catch (Exception e) {
                System.err.println("Error during benchmarking: " + e.getMessage());
            }
        }
        return results;
    }

    public static void plotResults(String title, Map<String, Map<String, List<Double>>> allResults) {
        for (String action : List.of("Insert Time", "Search Time", "Remove Time")) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (Map.Entry<String, Map<String, List<Double>>> entry : allResults.entrySet()) {
                String structureName = entry.getKey();
                List<Double> times = entry.getValue().get(action);

                for (int i = 0; i < times.size(); i++) {
                    dataset.addValue(times.get(i), structureName, "Trial " + (i + 1));
                }
            }

            JFreeChart chart = ChartFactory.createLineChart(
                    title + " - " + action,
                    "Trial",
                    "Time (ms)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            ChartPanel chartPanel = new ChartPanel(chart);
            JFrame frame = new JFrame(title + " - " + action);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(chartPanel);
            frame.pack();
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        int dataSize = 100000;
        int trials = 5;

        // Register all structures dynamically
        Map<String, Class<? extends DataStructure>> structures = new LinkedHashMap<>();
        structures.put("ListStructure", ListStructure.class);
        structures.put("SetStructure", SetStructure.class);
        structures.put("TreeStructure", TreeStructure.class);
        structures.put("SkipListStructure", SkipListStructure.class);
        structures.put("SkipGraphStructure", SkipGraphStructure.class);
        structures.put("DictionaryStructure", DictionaryStructure.class);
        structures.put("HashTableStructure", HashTableStructure.class);
        structures.put("HashSetStructure", HashSetStructure.class);
        structures.put("SortedSetStructure", SortedSetStructure.class);

        for (DataGenerationStrategy strategy : DataGenerationStrategy.values()) {
            System.out.println("\nStrategy: " + strategy);
            List<Integer> data = generateData(dataSize, strategy);

            Map<String, Map<String, List<Double>>> allResults = new LinkedHashMap<>();

            for (Map.Entry<String, Class<? extends DataStructure>> entry : structures.entrySet()) {
                String structureName = entry.getKey();
                Class<? extends DataStructure> structureClass = entry.getValue();

                Map<String, List<Double>> results = benchmark(structureClass, data, trials);
                allResults.put(structureName, results);

                System.out.println(structureName + ": " + results);
            }

            plotResults("Benchmark Results - " + strategy, allResults);
        }
    }
}