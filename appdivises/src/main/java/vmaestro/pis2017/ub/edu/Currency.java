package vmaestro.pis2017.ub.edu;

import java.util.HashMap;

/**
 * Created by victor on 15/02/2018.
 */

public class Currency {

    private CurrencyType type;
    private int imgId;
    private HashMap<CurrencyType,Float> exchangeValues;

    public Currency(CurrencyType type, int imgId) {
        this.type = type;
        this.imgId = imgId;
        this.exchangeValues = new HashMap<>();
    }

    public CurrencyType getType() {
        return type;
    }

    public void setType(CurrencyType type) {
        this.type = type;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    /**
     * Get the exchange value factor to a certain currency from a CurrencyType
     * @param to
     * @return float
     */
    public float getChangeTo(CurrencyType to){
        return this.exchangeValues.get(to).floatValue();
    }

    /**
     * Get the exchange value factor to a certain currency from a Currency
     * @param to
     * @return float
     */
    public float getChangeTo(Currency to){
        return this.exchangeValues.get(to.getType()).floatValue();
    }

    /**
     * Verify if it is the same currency
     * @param currency
     * @return boolean
     */
    public boolean isDifferentCurrency(Currency currency){
        return currency.getType() != this.getType();
    }

    /**
     * Verify if it is the same currency
     * @param currencyType
     * @return boolean
     */
    public boolean isDifferentCurrency(CurrencyType currencyType){
        return currencyType != this.getType();
    }

    /**
     * Add an exchange value
     * @param currencyType
     * @param factor
     */
    public void addExchangeValue(CurrencyType currencyType, float factor){
        this.exchangeValues.put(currencyType,factor);
    }

    /**
     * Check if a Currency has been already generated or not
     * @param currencyType
     * @return boolean
     */
    public boolean hasCurrencyGenerated(CurrencyType currencyType){
        return this.exchangeValues.containsKey(currencyType);
    }


    @Override
    public String toString() {
        return "Currency{" +
                "type=" + type +
                ", imgId=" + imgId +
                '}';
    }
}
