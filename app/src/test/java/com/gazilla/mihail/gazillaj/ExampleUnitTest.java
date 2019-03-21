package com.gazilla.mihail.gazillaj;

import com.gazilla.mihail.gazillaj.ui.account.AccountActivity;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkPhone(){
        //AccountActivity activity = new AccountActivity();
        //assertEquals(activity.checkPhone("+79251459197"), "9251459197");
        String input1 = "qwerty";
        String input2 = "123456";
        String input3 = "qwerty123456";

        String type1 = poromoORrefer(input1);
        String type2 = poromoORrefer(input2);
        String type3 = poromoORrefer(input3);

        assertEquals(type1, "Promo");
        assertEquals(type2, "Refer");
        assertEquals(type3, "Promo");

    }


    private String poromoORrefer(String text){
        if (text.matches("[0-9]+"))
            return "Refer";
        else
            return "Promo";
    }

}