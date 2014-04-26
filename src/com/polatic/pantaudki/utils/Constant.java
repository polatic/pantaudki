package com.polatic.pantaudki.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

public class Constant {
    public static final Boolean DEBUG_MODE = true;
    public static final String GOOGLE_PROJECT_ID = "864191031758";

    public static final String PREFERENCES_NAME = "PantauPreferences";
    public static final String SERVER_URL = "http://api.hackjak.bappedajakarta.go.id/apbd/";
    public static final String TAG = "Pantau";
    public static final String IS_FIRSTTIME = "IsFirstTime";
    public static final String IS_LOGIN = "IsLogin";

    
    public static final Integer CONNECTION_TIMEOUT = 13000;
    public static final Integer CONNECTION_RETRIES = 2;
    public static final Integer SPLASH_DISPLAY_TIME = 3000;

    public static final String API_KEY = "XrRhaKBNGyM4GFZknuGBShL8XVC0EzM9";
    public static final String PARAM_SKPD = "skpd/";
    public static final String PARAM_PROGRAM = "program/";
    public static final String PARAM_URUSAN = "urusan/";
    public static final String PARAM_API_KEY = "/?apiKey=" + API_KEY;
    
    public static final String PARAM_SKPD_CODE = "SKPDKode";
    public static final String PARAM_SKPD_NAME = "SKPDNama";
    public static final String PARAM_FACEBOOK = "facebook";
    public static final String PARAM_CITY = "city";
    public static final String PARAM_BIRTHDAY = "birthday";
    public static final String PARAM_BBM = "bbm";
    public static final String PARAM_ADDRESS = "address";
    
    public static final String[] FACEBOOK_PERMISSION = {
            "email",
            "user_about_me", "user_birthday", "user_education_history",
            "user_location", "user_work_history", "friends_birthday"
    };

    public static final String API_CACHE_PROGRAM = "apicacheprogram";
    public static final String API_CACHE_URUSAN = "apicacheurusan";
    public static final String API_CACHE_SKPD= "apicacheskpd";

    public static final String FILE_PATH_IMAGE = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/" + TAG;

    public static final String CHAT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    public static final String DATE_FORMAT_DDMMYYYY = "dd-MM-yyyy";
    public static final String DATE_FORMAT_MMDDYYYY = "MM-dd-yyyy";
    /**
     * Intent used to display a status message in the screen.
     */
    public static final String DISPLAY_STATUS_ACTION =
            "com.android.polatic.custoom.DISPLAY_STATUS";

    /**
     * Intent used to display a chat message in the screen.
     */
    public static final String DISPLAY_MESSAGE_ACTION =
            "com.android.polatic.custoom.DISPLAY_MESSAGE";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a status message.
     * <p>
     * This method is defined in the common helper because it's used both by the
     * UI and the background service.
     * 
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_STATUS_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }

    /**
     * Notifies UI to display a chat message.
     * <p>
     * This method is defined in the common helper because it's used both by the
     * UI and the background service.
     * 
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayChat(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }

}
