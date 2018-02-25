package vmaestro.pis2017.ub.edu;

/**
 * Created by victor on 16/02/2018.
 */

public class ExchangeController implements IExchangeController{

    private static ExchangeController instance;
    private Exchange exchange;


    public ExchangeController(Currency from, Currency to) {
        exchange = new Exchange(from,to);
    }

    @Override
    public float performExchange(float amount, boolean commission, boolean custom){
        return exchange.performExchange(amount,commission, custom);
    }

    @Override
    public void updateFactorExchange(Currency from, Currency to) {
        exchange.updateFactorExchange(from,to);
    }

    @Override
    public void changeOrder(){
        exchange.changeOrder();
    }

    @Override
    public void changeCommission(float cm) throws AppDivisesException {
        exchange.getCommission().setCommission(cm);
    }
    @Override
    public Commission getCommission(){
        return exchange.getCommission();
    }

    @Override
    public float getCustomFactor(){
        return exchange.getCustomFactorExchange();
    }

    @Override
    public void setCustomFactor(float a){
        this.exchange.setCustomFactorExchange(a);
    }

    /**
     * Singleton Instance since this is a controller
     * @return ExchangeController instance
     */
    public static ExchangeController getInstance(Currency from, Currency to) {
        synchronized (ExchangeController.class) {
            if (instance == null)
                instance = new ExchangeController(from,to);
        }
        return instance;
    }
}
