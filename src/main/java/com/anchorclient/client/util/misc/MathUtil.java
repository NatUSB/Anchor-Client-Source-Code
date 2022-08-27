package com.anchorclient.client.util.misc;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtil {

    /**
     * Linearly interpolates between the given range
     * @param now The start value
     * @param then The end range
     * @param percent The percentage
     * @return The value
     */
    public double interpolate(final double now, final double then, final double percent) {
        return (now + (then - now) * percent);
    }

    /**
     * Returns the percentage based on an input, min, and max
     * @param input The input
     * @param min The min value
     * @param max The max value
     * @return The value
     */
    public float getPercentByMaxAndMin(float input, float min, float max) {
        return ((input - min)) / (max - min);
    }

    /**
     * Snaps and clamps a value to a value
     * @param value The value
     * @param min The min value
     * @param max The max value
     * @param snapAmount The snap amount
     * @return The value
     */
    public float snap(float value, float min, float max, float snapAmount) {
        return Math.max(min, Math.min(Math.round(value / snapAmount) * snapAmount, max));
    }

    /**
     * Gets a random number in a certain range
     * @param min The min value
     * @param max The max value
     * @return The value
     */
    public float getRandomInRange(float min, float max) {
        return (float) ((Math.random() * (max - min)) + min);
    }

    /**
     * Gets the distance between 2 points
     * @param x1 The first x position
     * @param y1 The first y position
     * @param x2 The second x position
     * @param y2 The seconds y position
     * @return The distance
     */
    public float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.abs(Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)));
    }

    /**
     * Clamps a value to the specified range
     * @param num The value
     * @param min The min value
     * @param max The max value
     * @return The clamped value
     */
    public float clamp(float num, float min, float max){
        if(num > max) return max;
        return Math.max(num, min);
    }

    /**
     * Calculates the gaussian value for blurring
     * @param x The x value
     * @param sigma The sigma value
     * @return The gaussian value
     */
    public float calculateGaussianValue(float x, float sigma) {
        double PI = 3.141592653;
        double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

}
