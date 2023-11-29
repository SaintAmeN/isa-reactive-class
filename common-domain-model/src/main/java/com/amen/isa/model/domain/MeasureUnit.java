package com.amen.isa.model.domain;

public enum MeasureUnit {
    UNIT("u"),
    KILOGRAM("kg"),
    GRAM("g");
    private final String symbol;

    MeasureUnit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
