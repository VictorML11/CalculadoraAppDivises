package vmaestro.pis2017.ub.edu;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Deprecated
/**
 * This class has been considered deprecated since it generates a random data  which is not useful for the Test Driven Development.
 * Nevertheless it was a good idea to generate random data since we didn't have a data base.
 * Now it is better to use the CurrenyData.class
 */
public class CurrencyRandomData {

    private List<Currency> currencyList;

    public CurrencyRandomData(List<Currency> currencyList) {
        this.currencyList = currencyList;
        this.initCurrencies();
        this.loadRandomExchangeValues();
    }

    private void initCurrencies(){
        currencyList.add(new Currency(CurrencyType.EUR, R.drawable.eur));
        currencyList.add(new Currency(CurrencyType.USD, R.drawable.usd));
    }

    private void loadRandomExchangeValues() {
        CurrencyType baseCurrency = CurrencyType.EUR;
        for (CurrencyType currencyType : CurrencyType.values()) {
            Currency currency = getCurrencyFromType(currencyType);
            if (currency != null) {
                for (CurrencyType currentCurrency : CurrencyType.values()) {
                    if ((currencyType != currentCurrency)) {
                        if (!hasBeenGenerated(baseCurrency, currentCurrency)) {
                            currency.addExchangeValue(currentCurrency, generateFactorExchange());
                        } else {
                            float factor = generateFactorExchangeFromBase(currencyType, baseCurrency, currentCurrency);
                            currency.addExchangeValue(currentCurrency, factor);
                        }
                    } else {
                        currency.addExchangeValue(currentCurrency, 1.0f);
                    }
                }
            }
        }
    }

    private Currency getCurrencyFromType(CurrencyType currencyType){
        Currency currency = null;
        Iterator<Currency> it = currencyList.iterator();
        boolean found = false;
        while(it.hasNext() && !found){
            Currency c = it.next();
            if(!c.isDifferentCurrency(currencyType)){
                currency = c;
                found = true;
            }
        }
        return currency;
    }

    private float generateFactorExchangeFromBase(CurrencyType from, CurrencyType passFor, CurrencyType to) {
        Currency c = getCurrencyFromType(passFor);
        float relationFromGenerated = c.getChangeTo(from);
        float relationToGenerated = c.getChangeTo(to);
        return (1/relationFromGenerated)*relationToGenerated;
    }

    private float generateFactorExchange() {
        Random random = new Random();
        boolean bigger = random.nextBoolean();
        float calc = (float) (0.1567 + Math.random() * (0.9865 - 0.1567));
        return bigger ? (calc+1) : calc;
    }

    private boolean hasBeenGenerated(CurrencyType base, CurrencyType currencyType) {
        return getCurrencyFromType(base).hasCurrencyGenerated(currencyType);
    }


}
