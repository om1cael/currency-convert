package com.om1cael;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.om1cael.model.CurrencyModel;

import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class APIService {
    private final int HTTP_OK_CODE = 200;

    private URI apiEndpoint;
    private String apiKey = System.getenv("API_KEY");

    private String conversionCurrency;

    public APIService(String baseCurrency, String conversionCurrency) {
        if(apiKey.isEmpty() || apiKey.isBlank())
            throw new IllegalStateException("API key not set!");

        this.apiEndpoint = URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency);
        this.conversionCurrency = conversionCurrency;
    }

    public CompletableFuture<CurrencyModel> mapJSON() {
        return this.fetchAPI().thenApply(response -> {
            Gson gson = new Gson();

            JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
            JsonElement conversionRate = jsonObject
                    .getAsJsonObject("conversion_rates")
                    .get(this.conversionCurrency);

            CurrencyModel currencyModel = gson.fromJson(jsonObject, CurrencyModel.class);
            currencyModel.setRate(conversionRate.getAsDouble());
            currencyModel.setConversionCurrency(this.conversionCurrency);

            return currencyModel;
        }).exceptionally(ex -> {
            System.out.println("It was not possible to parse the JSON response: " + ex.getMessage());
            return null;
        });
    }

    private CompletableFuture<String> fetchAPI() {
        final Duration CONNECTION_TIMEOUT = Duration.ofSeconds(10);

        try(HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(apiEndpoint)
                    .GET()
                    .timeout(CONNECTION_TIMEOUT)
                    .build();

            CompletableFuture<HttpResponse<String>> futureResponse =
                    httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

            return futureResponse.thenApply(response -> {
                if(response.statusCode() != HTTP_OK_CODE) {
                    throw new RuntimeException("The API returned a not ok code.");
                }

                return response.body();
            });
        } catch (UncheckedIOException e) {
            throw new RuntimeException("Error while creating the HTTP client: " + e.getMessage());
        }
    }
}
