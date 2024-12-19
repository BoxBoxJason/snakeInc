package org.snakeinc.server.dto;

public class SnakeStats {
    private String snake;
    private int min;
    private int max;
    private double average;

    public SnakeStats(String snake, Number min, Number max, Number average) {
        this.snake = snake;
        this.min = min.intValue();
        this.max = max.intValue();
        this.average = average.doubleValue();
    }

    public String getSnake() {
        return snake;
    }

    public void setSnake(String snake) {
        this.snake = snake;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
