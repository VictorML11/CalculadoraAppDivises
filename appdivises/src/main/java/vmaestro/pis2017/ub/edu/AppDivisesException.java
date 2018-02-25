package vmaestro.pis2017.ub.edu;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by victor on 16/02/2018.
 */

public class AppDivisesException extends Exception {

    public AppDivisesException() {
        super("AppDivises Exception");
    }

    public AppDivisesException(String msg) {
        super(msg);
        Logger.getLogger(AppDivisesException.class.getName()).log(Level.SEVERE, msg);
    }
}
