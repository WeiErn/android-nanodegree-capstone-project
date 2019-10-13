package com.udacity.findaflight.data;

public class SearchResultCurrency {
    private String code;
    private String symbol;
    private String thousandsSeparator;
    private String decimalSeparator;
    private boolean symbolOnLeft;
    private boolean spaceBetweenAmountAndSymbol;
    private int roundingCoefficient;
    private int decimalDigits;

    public SearchResultCurrency(String code, String symbol, String thousandsSeparator, String decimalSeparator, boolean symbolOnLeft, boolean spaceBetweenAmountAndSymbol, int roundingCoefficient, int decimalDigits) {
        this.code = code;
        this.symbol = symbol;
        this.thousandsSeparator = thousandsSeparator;
        this.decimalSeparator = decimalSeparator;
        this.symbolOnLeft = symbolOnLeft;
        this.spaceBetweenAmountAndSymbol = spaceBetweenAmountAndSymbol;
        this.roundingCoefficient = roundingCoefficient;
        this.decimalDigits = decimalDigits;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getThousandsSeparator() {
        return thousandsSeparator;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    public boolean isSymbolOnLeft() {
        return symbolOnLeft;
    }

    public boolean isSpaceBetweenAmountAndSymbol() {
        return spaceBetweenAmountAndSymbol;
    }

    public int getRoundingCoefficient() {
        return roundingCoefficient;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }
}
