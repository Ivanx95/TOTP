package com.example.cliclient;

import com.otp.TOTP;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicInteger;


public class TokenUI {
    public static void main(String[] args) {
        System.out.println("TOTP value: " + TOTP.lapse);
        JFrame f = new JFrame();
        f.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                super.windowClosing(e);
            }

        });
        f.setSize(300, 300);

        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel();

        TOTP otp = new TOTP();
        final JLabel label = new JLabel();
        final JLabel counter = new JLabel();
        Thread th = new Thread(() -> {
            AtomicInteger count = new AtomicInteger(otp.getRemaining());

            if (!SwingUtilities.isEventDispatchThread()) {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        label.setText(otp.getOtp());
                        counter.setText(String.format("Counter: %s  --", count.get()));

                    }
                });
            }
            while (true) {
                if (count.get() == 0) {
                    count.set(otp.getRemaining());
                }

                if (!SwingUtilities.isEventDispatchThread()) {
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            label.setText(otp.getOtp());
                            counter.setText(String.format("Counter: %s  --", count.get()));

                        }
                    });
                }

                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    //doNothing
                }
                count.decrementAndGet();
            }
        });

        otp.setSecret("1234");

        panel.add(counter);
        panel.add(label);
        f.add(panel);
        f.setTitle("OTP generator: Serial aaa111");
        f.setVisible(true);
        th.start();

    }
}