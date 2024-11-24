package com.om1cael;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String baseCurrency, conversionCurrency;
        double amount;

        try(Scanner scanner = new Scanner(System.in)) {
            System.out.print("Base currency: ");
            baseCurrency = scanner.next();
            System.out.print("Conversion currency: ");
            conversionCurrency = scanner.next();
            System.out.print("Amount: ");
            amount = scanner.nextDouble();
        }

        CurrencyConvert currencyConvert = new CurrencyConvert(baseCurrency, conversionCurrency, amount);
        currencyConvert.printCurrency();
    }
}