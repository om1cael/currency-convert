package com.om1cael;

public class CurrencyConvert {
    private APIService apiService;

    private String baseCurrency;
    private String conversionCurrency;
    private double amount;

    public CurrencyConvert(String baseCurrency, String conversionCurrency, double amount) {
        this.baseCurrency = baseCurrency;
        this.conversionCurrency = conversionCurrency;
        this.amount = amount;

        this.apiService = new APIService(this.baseCurrency, this.conversionCurrency);
    }

    public void printCurrency() {
        apiService.mapJSON().thenAccept(response -> {
            if(amount == 1) {
                System.out.println("1 " + response.getBaseCurrency() + " is " + response.getRate() + " " + response.getConversionCurrency());
                return;
            }

            double totalConversionAmount = amount * response.getRate();
            System.out.println(this.amount + " " + response.getBaseCurrency() + " is " + totalConversionAmount + " " + response.getConversionCurrency());
        }).exceptionally(ex -> {
            throw new RuntimeException("An error occurred while parsing the response: " + ex.getMessage());
        });
    }
}
