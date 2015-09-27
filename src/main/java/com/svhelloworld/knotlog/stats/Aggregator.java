package com.svhelloworld.knotlog.stats;

/**
 * Generates aggregate statistics over a set of values.
 * 
 * @author Jason Andersen
 * @since Feb 16, 2010
 *
 */
public class Aggregator {
    /**
     * Count of values
     */
    private int count;
    /**
     * Minimum value
     */
    private float min = Float.MAX_VALUE;
    /**
     * Maximum value
     */
    private float max = Float.MIN_VALUE;
    /**
     * Sum of all values
     */
    private float sum;
    
    /**
     * Aggregates a series of values.
     * @param values series of values to add to aggregation
     */
    public void aggregate(Float...values) {
        for (float value : values) {
            count++;
            min = value < min ? value : min;
            max = value > max ? value : max;
            sum+= value;
        }
    }
    
    /**
     * @return the average of all the values
     */
    public float getAverage() {
        return sum / count;
    }
    
    /**
     * @return the count of values
     */
    public int getCount() {
        return count;
    }

    /**
     * @return the minimum value
     */
    public float getMinimum() {
        return min;
    }

    /**
     * @return the maximum value
     */
    public float getMaximum() {
        return max;
    }

    /**
     * @return the sum of all values
     */
    public float getSum() {
        return sum;
    }
    
}
