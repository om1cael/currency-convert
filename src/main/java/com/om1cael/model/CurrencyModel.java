package com.om1cael.model;

import com.google.gson.annotations.SerializedName;

public class CurrencyModel {
    @SerializedName("base_code") private String baseCurrency;
    private String conversionCurrency;
    private Double rate;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCode) {
        this.baseCurrency = baseCode;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getConversionCurrency() {
        return conversionCurrency;
    }

    public void setConversionCurrency(String conversionCurrency) {
        this.conversionCurrency = conversionCurrency;
    }

    @Override
    public String toString() {
        return "CurrencyModel{" +
                "baseCurrency='" + baseCurrency + '\'' +
                ", conversionCurrency='" + conversionCurrency + '\'' +
                ", rate=" + rate +
                '}';
    }
}
