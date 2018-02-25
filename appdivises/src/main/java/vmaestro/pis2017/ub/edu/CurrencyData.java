package vmaestro.pis2017.ub.edu;

import java.util.Iterator;
import java.util.List;

/**
 * Created by victor on 24/02/2018.
 */

public class CurrencyData {

    private List<Currency> currencyList;

    public CurrencyData(List<Currency> currencyList) {
        this.currencyList = currencyList;
        this.initCurrencies();
        this.loadExchangeValues();
    }

    private void initCurrencies(){
        currencyList.add(new Currency(CurrencyType.EUR, R.drawable.eur));
        currencyList.add(new Currency(CurrencyType.USD, R.drawable.usd));
    }

    private void loadExchangeValues() {
        Currency c = null;
        c = getCurrencyFromType(CurrencyType.EUR);
        c.addExchangeValue(CurrencyType.EUR, 1.0f);
        c.addExchangeValue(CurrencyType.USD, 1.24f);

        c = getCurrencyFromType(CurrencyType.USD);
        c.addExchangeValue(CurrencyType.USD, 1.0f);
        c.addExchangeValue(CurrencyType.EUR, 0.86f);

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

}
