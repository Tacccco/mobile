package com.onon.android.weatherapplication;

public class weatherXmlData {
    private String time;
    private String symbol;
    private String temprature;

    public weatherXmlData(String time, String symbol, String temprature) {
        this.time = time;
        this.symbol = symbol;
        this.temprature = temprature;
    }

    @Override
    public String toString() {
        return "At time '" + time + "\n" +
                 symbol  +" "+ temprature + "'C";
    }
}
