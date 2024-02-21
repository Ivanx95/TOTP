package com.otp;


import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TOTP {
    public static Long lapse = Long.valueOf(System.getProperty("app.lapse"));
    private String secret;
    private String serial;
    private Integer epsilon = 1000000;


    private String toHex(byte[] bytes) {
        String result = "";

        for (int i = 0; i < bytes.length; i++) {
            result += Integer.toHexString((bytes[i] & 0xff) + 0x100).substring(1);
        }
        return result;
    }


    public String getOtp() {
        String sha1 = getSha1();
        String lastNumber = sha1.substring(sha1.length() - 1);

        Integer index = Integer.valueOf(lastNumber, 16);
        String otp = sha1.substring(index, index + 6);
        Integer otpNumber = Integer.parseInt(otp, 16) % this.epsilon;
        return String.valueOf(otpNumber);
    }

    public String getSha1() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String secretPlusTime = secret + System.currentTimeMillis() / lapse;
        String sha1 = toHex(md.digest(secretPlusTime.getBytes(Charset.defaultCharset())));
        return sha1;
    }

    public int getRemaining() {
        long remainin = System.currentTimeMillis() % lapse;
        return (60-((int) remainin / 1000));
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }


}
