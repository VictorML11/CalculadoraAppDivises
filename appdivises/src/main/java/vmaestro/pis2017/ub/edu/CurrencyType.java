package vmaestro.pis2017.ub.edu;

/**
 * Created by victor on 16/02/2018.
 */

public enum CurrencyType {

    EUR("Euro", "€"),
    USD("Dollar", "$");

    private String name;
    private String symbol;

    CurrencyType(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CurrencyType{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}