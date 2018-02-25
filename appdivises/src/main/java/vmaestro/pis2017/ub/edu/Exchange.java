package vmaestro.pis2017.ub.edu;

/**
 * Created by victor on 16/02/2018.
 */

public class Exchange {

    private float factorExchange;
    private float customFactorExchange;
    private Commission commission;
    private Currency from;
    private Currency to;

    public Exchange(Currency from, Currency to, float commision) throws AppDivisesException {
        this.from = from;
        this.to = to;
        this.commission = new Commission(commision);
        updateFactorExchange();
    }

    public Exchange(Currency from, Currency to, Commission commision) {
        this.from = from;
        this.to = to;
        this.commission = commision;
        updateFactorExchange();
    }

    public Exchange(Currency from, Currency to){
        this.from = from;
        this.to = to;
        commission = new Commission();
        updateFactorExchange();
    }

    public float getCustomFactorExchange() {
        return customFactorExchange;
    }

    public void setCustomFactorExchange(float customFactorExchange) {
        this.customFactorExchange = customFactorExchange;
    }

    public float getFactorExchange() {
        return factorExchange;
    }

    public void setFactorExchange(float factorExchange) {
        this.factorExchange = factorExchange;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }


    /**
     * Update general Factor Exchange
     */
    public void updateFactorExchange(){
        if(from.isDifferentCurrency(to)){
            this.setFactorExchange(from.getChangeTo(to));
            this.setCustomFactorExchange(from.getChangeTo(to));
        }else{
            this.setFactorExchange(1.0f);
            this.setCustomFactorExchange(1.0f);
        }
    }


    /**
     * Update the Factor Exchange with Change
     */
    public void updateFactorExchange(Currency from, Currency to){
        this.setFrom(from);
        this.setTo(to);
        if(this.from.isDifferentCurrency(this.to)){
            this.setFactorExchange(this.from.getChangeTo(this.to));
            this.setCustomFactorExchange(from.getChangeTo(to));
        }else{
            this.setFactorExchange(1.0f);
            this.setCustomFactorExchange(1.0f);
        }
    }

    /**
     * Change the order of the Currency exchange
     */
    public void changeOrder(){
        Currency tmp = from;
        from = to;
        to = tmp;
        updateFactorExchange();
    }


    /**
     * Perform an exchange currency given an amount with the current Factor Exchange
     * @param amount
     * @return float
     */
    public float performExchange(float amount, boolean commision, boolean custom){
        boolean diff = this.from.isDifferentCurrency(to);
        float factor = (custom) ? this.customFactorExchange : this.factorExchange;
        return (diff && commision) ? this.commission.applyCommission((amount*factor)) : amount*factor;
    }

}
