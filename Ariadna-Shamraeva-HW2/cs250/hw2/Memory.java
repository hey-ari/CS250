package cs250.hw2;

import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Random;

public class Memory {

    // Main method: Entry point of the program. It processes the command-line arguments and executes the experiments.
    // Main method: processes command-line arguments and runs the experiments.
    public static void main(String[] args) {
        // Validate the number of arguments.
        if (args.length != 3) {
            System.out.println("Usage: java cs250.hw2.Memory <size> <experiments> <seed>");
            return;
        }

        // Parse command-line arguments.
        int size = Integer.parseInt(args[0]);
        int experiments = Integer.parseInt(args[1]);
        long seed = Long.parseLong(args[2]);

        // Execute the experiments for each task.
        performVolatileExperiment(size, experiments);
        performArrayAccessExperiment(size, experiments, seed);
        performCollectionExperiment(size, experiments, seed);
    }

    // Task 1: Examines the performance impact of the volatile keyword
    private static void performVolatileExperiment(int size, int experiments) {
        long totalRegularTime = 0;
        long totalVolatileTime = 0;
        long totalRegularSum = 0;
        long totalVolatileSum = 0;

        for (int exp = 0; exp < experiments; exp++) {
            // Non-volatile experiment
            long runningTotal = 0;
            long startTime; // Declare startTime once outside the loops.
            
            startTime = System.nanoTime(); // Assign value here, no declaration.
            for (int i = 0; i < size; i++) {
                runningTotal += (i % 2 == 0) ? i : -i;
            }
            long endTime = System.nanoTime();
            totalRegularTime += (endTime - startTime);
            totalRegularSum += runningTotal;

            // Volatile experiment
            runningTotal = 0;
            startTime = System.nanoTime(); // Reuse startTime by assigning a new value.
            runningTotal = 0;
            for (int i = 0; i < size; i++) {
                runningTotal += (i % 2 == 0) ? i : -i;
            }
            endTime = System.nanoTime();
            totalVolatileTime += (endTime - startTime);
            totalVolatileSum += runningTotal;
        }

        // Output results for Task 1.
        System.out.println("\nTask 1");
        System.out.println("Regular: " + (totalRegularTime / experiments / 1e9) + " seconds");
        System.out.println("Volatile: " + (totalVolatileTime / experiments / 1e9) + " seconds");
        System.out.println("Avg regular sum: " + (totalRegularSum / experiments));
        System.out.println("Avg volatile sum: " + (totalVolatileSum / experiments));
    }

        // Task 2: Experiment to measure access times in an array.
        private static void performArrayAccessExperiment(int size, int experiments, long seed) {
            
            Random random = new Random(seed);
            Integer[] numbers = new Integer[size];
    
            // Initialize the array with random numbers.
            for (int i = 0; i < size; i++) {
                numbers[i] = random.nextInt();
            }
    
            long totalKnownTime = 0;
            long totalRandomTime = 0;
            long totalSum = 0;
    
            // Run experiments to measure access times in the array.
            for (int exp = 0; exp < experiments; exp++) {
                long sum = 0;
                long startTime = System.nanoTime();
                // Access elements in the first 10% of the array.
                for (int i = 0; i < size / 10; i++) {
                    sum += numbers[i];
                }
                long endTime = System.nanoTime();
                totalKnownTime += (endTime - startTime);
    
                // Access a random element in the last 10% of the array.
                int randomIndex = size - size / 10 + random.nextInt(size / 10);
                startTime = System.nanoTime();
                sum += numbers[randomIndex];
                endTime = System.nanoTime();
                totalRandomTime += (endTime - startTime);
    
                totalSum += sum;
            }
    
            // Output results for Task 2.
            System.out.println("\nTask 2");
            System.out.println("Avg time to access known element: " + (totalKnownTime / (experiments * (size / 10))) + " nanoseconds");
            System.out.println("Avg time to access random element: " + (totalRandomTime / experiments) + " nanoseconds");
            System.out.println("Sum: " + totalSum);
        }

        // Task 3: Experiment to compare performance between TreeSet and LinkedList.
        private static void performCollectionExperiment(int size, int experiments, long seed) {
            
            TreeSet<Integer> treeSet = new TreeSet<>();
            LinkedList<Integer> linkedList = new LinkedList<>();
            Random random = new Random(seed);

    
        // Populate the TreeSet and LinkedList with the same elements.
        for (int i = 0; i < size; i++) {
            treeSet.add(i);
            linkedList.add(i);
        }

        long totalSetTime = 0;
        long totalListTime = 0;

        // Run experiments to measure the time for .contains() method in TreeSet and LinkedList.
        for (int exp = 0; exp < experiments; exp++) {
            int randomNumber = random.nextInt(size);

            // Measure time for TreeSet
            long startTime = System.nanoTime();
            treeSet.contains(randomNumber);
            long endTime = System.nanoTime();
            totalSetTime += (endTime - startTime);

            // Measure time for LinkedList
            startTime = System.nanoTime();
            linkedList.contains(randomNumber);
            endTime = System.nanoTime();
            totalListTime += (endTime - startTime);
        }

        // Output results for Task 3
        System.out.println("\nTask 3");
        System.out.println("Avg time to find in set: " + (totalSetTime / experiments) + " nanoseconds");
        System.out.println("Avg time to find in list: " + (totalListTime / experiments) + " nanoseconds");
    }
}