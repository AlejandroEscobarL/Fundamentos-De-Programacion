package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {

    @JsonProperty("rate")
    private double rate;

    @JsonProperty("count")
    private int count;

    // Constructor predeterminado (Jackson lo necesita)
    public Rating() {}

    // Constructor parametrizado
    public Rating(double rate, int count) {
        this.rate = rate;
        this.count = count;
    }

    // Getters y Setters
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
