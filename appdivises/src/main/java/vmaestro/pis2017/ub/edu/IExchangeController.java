package vmaestro.pis2017.ub.edu;

/**
 * Created by victor on 16/02/2018.
 */

public interface IExchangeController {

    float performExchange(float amount, boolean commission, boolean custom);
    void setCustomFactor(float a);
    float getCustomFactor();
    Commission getCommission();
    void changeCommission(float cm) throws AppDivisesException;
    void updateFactorExchange(Currency from, Currency to);
    void changeOrder();


}
