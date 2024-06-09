package org.meteoServer.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class WeatherService {
    private static final String API_KEY = "fa0acfe72429f69050c37b451d681106";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    public static WeatherData getWeather(String city) throws IOException {
        String url = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.getEntity().getContent());

        double temperature = jsonNode.get("main").get("temp").asDouble();
        double humidity = jsonNode.get("main").get("humidity").asDouble();

        return new WeatherData(temperature, humidity);
    }
}