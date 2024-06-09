package org.meteoServer.services;

public class WeatherData {
    private final double temperature;
    private final double humidity;

    public WeatherData(double temperature, double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}
