package com.example.hellowebflux.utils;

public class SleepUtils {
    public static void sleepMilliseconds(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            //
        }
    }

    public static void sleepSeconds(int seconds) {
        sleepMilliseconds(seconds * 1000);
    }
}
