package io.github.vilginushki;

import io.github.vilginushki.structures.ListStructure;
import io.github.vilginushki.structures.SetStructure;
import io.github.vilginushki.structures.TreeStructure;
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

        for (int t = 0; t < trials; t++) {
            try {
                DataStructure<T> structure = structureClass.getDeclaredConstructor().newInstance();

                long startInsert = System.nanoTime();
                for (T value : data) structure.insert(value);
                long endInsert = System.nanoTime();

                long startSearch = System.nanoTime();
                for (T value : data) structure.search(value);
                long endSearch = System.nanoTime();

                results.get("Insert Time").add((endInsert - startInsert) / 1e6);
                results.get("Search Time").add((endSearch - startSearch) / 1e6);
            } catch (Exception e) {
                System.err.println("Error during benchmarking: " + e.getMessage());
            }
        }
        return results;
    }

    public static void plotResults(String title, Map<String, List<Double>> listResults, Map<String, List<Double>> setResults, Map<String, List<Double>> treeResults) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < listResults.get("Insert Time").size(); i++) {
            dataset.addValue(listResults.get("Insert Time").get(i), "ListStructure", "Trial " + (i + 1));
            dataset.addValue(setResults.get("Insert Time").get(i), "SetStructure", "Trial " + (i + 1));
            dataset.addValue(treeResults.get("Insert Time").get(i), "TreeStructure", "Trial " + (i + 1));
        }

        JFreeChart chart = ChartFactory.createLineChart(
                title + " - Insert Time",
                "Trial",
                "Time (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame(title + " - Insert Time");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);

        // Plot Search Time
        dataset.clear();
        for (int i = 0; i < listResults.get("Search Time").size(); i++) {
            dataset.addValue(listResults.get("Search Time").get(i), "ListStructure", "Trial " + (i + 1));
            dataset.addValue(setResults.get("Search Time").get(i), "SetStructure", "Trial " + (i + 1));
            dataset.addValue(treeResults.get("Search Time").get(i), "TreeStructure", "Trial " + (i + 1));
        }

        JFreeChart searchChart = ChartFactory.createLineChart(
                title + " - Search Time",
                "Trial",
                "Time (ms)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel searchChartPanel = new ChartPanel(searchChart);
        JFrame searchFrame = new JFrame(title + " - Search Time");
        searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchFrame.getContentPane().add(searchChartPanel);
        searchFrame.pack();
        searchFrame.setVisible(true);
    }

    public static void main(String[] args) {
        int dataSize = 100000;
        int trials = 5;

        for (DataGenerationStrategy strategy : DataGenerationStrategy.values()) {
            System.out.println("\nStrategy: " + strategy);
            List<Integer> data = generateData(dataSize, strategy);

            Map<String, List<Double>> listResults = benchmark(ListStructure.class, data, trials);
            Map<String, List<Double>> setResults = benchmark(SetStructure.class, data, trials);
            Map<String, List<Double>> treeResults = benchmark(TreeStructure.class, data, trials);

            System.out.println("ListStructure: " + listResults);
            System.out.println("SetStructure: " + setResults);
            System.out.println("TreeStructure: " + treeResults);

            plotResults("Benchmark Results - " + strategy, listResults, setResults, treeResults);
        }
    }
}