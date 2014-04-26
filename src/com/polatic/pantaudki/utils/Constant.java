package com.polatic.pantaudki.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

public class Constant {
    public static final Boolean DEBUG_MODE = true;
    public static final String GOOGLE_PROJECT_ID = "864191031758";

    public static final String PREFERENCES_NAME = "PantauPreferences";
    public static final String SERVER_URL = "http://api.hackjak.bappedajakarta.go.id/apbd";
    public static final String TAG = "Pantau";
    public static final String IS_FIRSTTIME = "IsFirstTime";
    public static final String IS_LOGIN = "IsLogin";

    public static final String FRAGMENT_NUMBER = "fragment_number";
    
    public static final Integer CONNECTION_TIMEOUT = 13000;
    public static final Integer CONNECTION_RETRIES = 2;
    public static final Integer SPLASH_DISPLAY_TIME = 3000;

    public static final String API_KEY = "XrRhaKBNGyM4GFZknuGBShL8XVC0EzM9";
    public static final String PARAM_SKPD = "/skpd/";
    public static final String PARAM_PROGRAM = "/program/";
    public static final String PARAM_URUSAN = "/urusan/";
    public static final String PARAM_API_KEY = "/?apiKey=" + API_KEY;
    public static final String PARAM_URUSAN_WITH_AND = "&urusan=";
    
    public static final String PARAM_PAGE = "&page=";
    public static final Integer LIMIT_PAGE = 30;
    public static final String PARAM_PER_PAGE = "&per_page=" + LIMIT_PAGE;
    
    public static final String PARAM_SKPD_CODE = "SKPDKode";
    public static final String PARAM_SKPD_NAME = "SKPDNama";
    
    public static final String PARAM_PROGRAM_NAME = "namaProgram";
    public static final String PARAM_PROGRAM_CODE = "program";
    
    public static final String JSON_URUSAN_CODE = "urusan";
    public static final String JSON_URUSAN_NAME = "namaUrusan";

    public static final String JSON_PROGRAM_NAME = "namaProgram";
    public static final String JSON_PROGRAM_CODE = "program";

    public static final String JSON_SKPDNAMA = "SKPDNama";
    public static final String JSON_KEGIATAN_NAME = "namaKegiatan";
    public static final String JSON_KEGIATAN_ID = "noKegiatan";
    public static final String JSON_PRICE = "nilai";
    public static final String JSON_UNIT = "unit";
    public static final String JSON_YEAR = "year";
    public static final String JSON_IS_PYHSIC = "fisik";
    public static final String JSON_REALIZATION = "realisasi";
    public static final String JSON_PERCENT_REALIZATION = "persenRealisasi";
    public static final String JSON_ID = "id";
        
    public static final String API_CACHE_PROGRAM = "apicacheprogram";
    public static final String API_CACHE_URUSAN = "apicacheurusan";
    public static final String API_CACHE_SKPD= "apicacheskpd";
    
    public static final String PARAM_NAME = "name";
    public static final String PARAM_VALUE = "value";


}
