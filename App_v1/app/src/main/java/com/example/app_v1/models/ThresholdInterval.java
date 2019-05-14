package com.example.app_v1.models;

public class ThresholdInterval {
    private float min;
    private float max;

    public ThresholdInterval(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public void setMin(int minValue) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
