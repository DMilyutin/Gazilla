package com.gazilla.mihail.gazillaj;

import com.gazilla.mihail.gazillaj.ui.account.AccountActivity;

import org.junit.Test;

import static com.gazilla.mihail.gazillaj.helps.HelpersMetodsKt.checkFormatPhone;
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
        /*String input1 = "qwerty";
        String input2 = "123456";
        String input3 = "qwerty123456";*/
/*
        String type1 = poromoORrefer(input1);
        String type2 = poromoORrefer(input2);
        String type3 = poromoORrefer(input3);*/

        String type1 = checkFormatPhone("+79251459197");
        String type2 = checkFormatPhone("9251459197");
        String type3 = checkFormatPhone("89251459197");
        String type4 = checkFormatPhone("889251459197");
        String type5 = checkFormatPhone("892514591");

        assertEquals(type1, "9251459197");
        assertEquals(type2, "9251459197");
        assertEquals(type3, "9251459197");
        assertEquals(type4, "");
        assertEquals(type5, "");

        //RegistrationFragmentPresenter presenter = new RegistrationFragmentPresenter();

        /*String type4 = presenter.checkDataIsPromoorRefer(input1);
        String type5 = presenter.checkDataIsPromoorRefer(input2);
        String type6 = presenter.checkDataIsPromoorRefer(input3);

        assertEquals(type4, "Promo");
        assertEquals(type5, "Refer");
        assertEquals(type6, "Promo");*/
    }


    private String poromoORrefer(String text){
        if (text.matches("[0-9]+"))
            return "Refer";
        else
            return "Promo";
    }

}