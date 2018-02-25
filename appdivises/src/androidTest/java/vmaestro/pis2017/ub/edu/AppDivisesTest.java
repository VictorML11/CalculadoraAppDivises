package vmaestro.pis2017.ub.edu;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AppDivisesTest {

    public static final String MONEY_INPUT = "20.0";
    public static final String EUR_TO_USD_RESULT = "24.8 $";
    public static final String USD_TO_EUR_RESULT = "17.2 €";

    public static final String CUSTOM_FACTOR = "5.0";
    public static final String USD_TO_EUR_CUSTOM_RESULT = "100.0 €";

    public static final String COMMISSION = "0.5";
    public static final String USD_TO_EUR_CUSTOM_RESULT_COMMISSION = "50.0 €";

    public static final String WRONG_START_INPUT = ".";
    public static final String WRONG_COMMISSION_INPUT = "9";
    public static final String ERROR_START = "Error! The input should start with a number!";
    public static final String ERROR_TOO_BIG = "Error! The commission should be decimal number from 0 to 1. Commission set to default value 0.025";


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("vmaestro.pis2017.ub.edu", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    /*
    Tests to do:
    - Change from EUR to USD OK
    - Change from USD to EUR OK
    - Use the button changeOrder OK
    - Apply a commission OK
    - Do a custom FactorExchange OK
    - Verify inputs OK
     */

    @Test
    public void basicExchange(){
        // From EUR -> USD
        onView(withId(R.id.amountCurrency1)).perform(typeText(MONEY_INPUT), closeSoftKeyboard());
        onView(withId(R.id.amountCurrency2)).check(matches(withText(EUR_TO_USD_RESULT)));

        // From USD -> EUR
        onView(withId(R.id.conversor_id)).perform(click());
        onView(withId(R.id.amountCurrency2)).check(matches(withText(USD_TO_EUR_RESULT)));

        customFactorExchange();
        applyCommission();

    }

    public void customFactorExchange(){
        // Custom Exchange
        onView(withId(R.id.factorIn)).perform(typeText(CUSTOM_FACTOR),closeSoftKeyboard());
        onView(withId(R.id.checkBox_Factor)).perform(click());
        onView(withId(R.id.amountCurrency2)).check(matches(withText(USD_TO_EUR_CUSTOM_RESULT)));
    }

    public void applyCommission(){
        // Custom Commission
        onView(withId(R.id.commissionAmount)).perform(typeText(COMMISSION), closeSoftKeyboard());
        onView(withId(R.id.checkBox)).perform(click());
        onView(withId(R.id.amountCurrency2)).check(matches(withText(USD_TO_EUR_CUSTOM_RESULT_COMMISSION)));
    }


    @Test
    public void verifyInputs(){
        // MONEY INPUT
        onView(withId(R.id.amountCurrency1)).perform(typeText(WRONG_START_INPUT), closeSoftKeyboard());
        onView(withId(R.id.amountCurrency1)).check(matches(hasErrorText(ERROR_START)));

        // CUSTOM FACTOR INPUT
        onView(withId(R.id.factorIn)).perform(typeText(WRONG_START_INPUT), closeSoftKeyboard());
        onView(withId(R.id.factorIn)).check(matches(hasErrorText(ERROR_START)));

        // CUSTOM FACTOR INPUT
        onView(withId(R.id.commissionAmount)).perform(typeText(WRONG_START_INPUT), closeSoftKeyboard());
        onView(withId(R.id.commissionAmount)).check(matches(hasErrorText(ERROR_START)));

        //COMISSION INPUT TOO BIG
        onView(withId(R.id.commissionAmount)).perform(typeText(WRONG_COMMISSION_INPUT), closeSoftKeyboard());
        onView(withId(R.id.commissionAmount)).check(matches(hasErrorText(ERROR_TOO_BIG)));

    }


}
