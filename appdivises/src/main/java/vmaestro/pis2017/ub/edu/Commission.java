package vmaestro.pis2017.ub.edu;

/**
 * Created by victor on 16/02/2018.
 */

public class Commission {

    private float commision;

    public Commission(){
        this.commision = 0.025f;
    }

    public Commission(float commission) throws AppDivisesException {
        setCommission(commission);
    }

    public float getCommission() {
        return commision;
    }

    /**
     * Commission should be a number between 0.0 to 1.0 (Percentage)
     * @param commission
     * @throws AppDivisesException
     */
    public void setCommission(float commission) throws AppDivisesException{
        if((commission < 0.0f) || commission > 1.0f){
            this.commision = 0.025f;
            throw new AppDivisesException("Error! The commission should be decimal number from 0 to 1. Commission set to default value 0.025");
        }else{
            this.commision = commission;
        }
    }

    /**
     * Apply a commission for an exchange
     * @param amount
     * @return
     */
    public float applyCommission(float amount){
        return ((amount) - (amount*this.commision));
    }

    @Override
    public String toString() {
        return "Commission{" +
                "commission=" + commision +
                '}';
    }
}
