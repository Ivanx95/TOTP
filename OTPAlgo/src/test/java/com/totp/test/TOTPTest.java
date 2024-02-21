package com.totp.test;

import com.otp.TOTP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TOTPTest {
    static {
        System.setProperty("app.lapse","60000");
    }
    @Test
    public void test() throws InterruptedException {

        TOTP test = new TOTP();
        test.setSecret("1234");


        String previous= test.getOtp();
        for(int i=0;i<10;i++){
            Thread.sleep(6000);
            String aux = test.getOtp();
            Assertions.assertEquals(previous,aux);
            previous=aux;
        }
    }
}
