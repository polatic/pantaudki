package com.polatic.pantaudki.utils;

import android.util.Log;

public class LogManager {
    public static void print(String message) {
        if (Constant.DEBUG_MODE) {
            Log.d(Constant.TAG, message);
        }
    }
}
