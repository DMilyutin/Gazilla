package com.gazilla.mihail.gazillaj;

import com.gazilla.mihail.gazillaj.ui.registration.RegAndAutorizActivity;

import org.junit.Test;

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
        RegAndAutorizActivity activity = new RegAndAutorizActivity();
        assertEquals(activity.checkPhone("9251459197"), "9251459197");
    }
}