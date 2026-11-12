package com.routewise.dsa.sorting;

import java.util.Arrays;

public class SortMetrics {
    private String algorithmName;
    private int inputSize;
    private long comparisons;
    private long swaps;
    private long executionTimeNanos;
    private double executionTimeMicros;
    private String timeComplexityBest;
    private String timeComplexityAverage;
    private String timeComplexityWorst;
    private String spaceComplexity;
    private boolean isStable;
    private int[] sortedSample;

    public SortMetrics() {}

    public SortMetrics(String algorithmName, int inputSize, long comparisons, long swaps,
                       long executionTimeNanos, String best, String avg, String worst,
                       String space, boolean stable, int[] sample) {
        this.algorithmName = algorithmName;
        this.inputSize = inputSize;
        this.comparisons = comparisons;
        this.swaps = swaps;
        this.executionTimeNanos = executionTimeNanos;
        this.executionTimeMicros = executionTimeNanos / 1000.0;
        this.timeComplexityBest = best;
        this.timeComplexityAverage = avg;
        this.timeComplexityWorst = worst;
        this.spaceComplexity = space;
        this.isStable = stable;
        this.sortedSample = sample;
    }

    public String getAlgorithmName() { return algorithmName; }
    public void setAlgorithmName(String algorithmName) { this.algorithmName = algorithmName; }

    public int getInputSize() { return inputSize; }
    public void setInputSize(int inputSize) { this.inputSize = inputSize; }

    public long getComparisons() { return comparisons; }
    public void setComparisons(long comparisons) { this.comparisons = comparisons; }

    public long getSwaps() { return swaps; }
    public void setSwaps(long swaps) { this.swaps = swaps; }

    public long getExecutionTimeNanos() { return executionTimeNanos; }
    public void setExecutionTimeNanos(long executionTimeNanos) {
        this.executionTimeNanos = executionTimeNanos;
        this.executionTimeMicros = executionTimeNanos / 1000.0;
    }

    public double getExecutionTimeMicros() { return executionTimeMicros; }

    public String getTimeComplexityBest() { return timeComplexityBest; }
    public void setTimeComplexityBest(String timeComplexityBest) { this.timeComplexityBest = timeComplexityBest; }

    public String getTimeComplexityAverage() { return timeComplexityAverage; }
    public void setTimeComplexityAverage(String timeComplexityAverage) { this.timeComplexityAverage = timeComplexityAverage; }

    public String getTimeComplexityWorst() { return timeComplexityWorst; }
    public void setTimeComplexityWorst(String timeComplexityWorst) { this.timeComplexityWorst = timeComplexityWorst; }

    public String getSpaceComplexity() { return spaceComplexity; }
    public void setSpaceComplexity(String spaceComplexity) { this.spaceComplexity = spaceComplexity; }

    public boolean isStable() { return isStable; }
    public void setStable(boolean stable) { isStable = stable; }

    public int[] getSortedSample() { return sortedSample; }
    public void setSortedSample(int[] sortedSample) { this.sortedSample = sortedSample; }

    @Override
    public String toString() {
        return algorithmName + " [Size=" + inputSize + ", Time=" + executionTimeMicros + " μs, Comparisons=" + comparisons + ", Swaps=" + swaps + "]";
    }
}
