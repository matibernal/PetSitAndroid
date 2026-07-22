package ar.edu.davinci.petsit.mobile.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class PersistentCookieJar implements CookieJar {

    private static final String PREFS_NAME = "petsit_cookies";

    private final SharedPreferences prefs;

    public PersistentCookieJar(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies.isEmpty()) return;
        Set<String> serializadas = new HashSet<>();
        for (Cookie cookie : cookies) {
            serializadas.add(cookie.toString());
        }
        prefs.edit().putStringSet(url.host(), serializadas).apply();
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        Set<String> serializadas = prefs.getStringSet(url.host(), Collections.emptySet());
        List<Cookie> cookies = new ArrayList<>();
        for (String s : serializadas) {
            Cookie cookie = Cookie.parse(url, s);
            if (cookie != null) cookies.add(cookie);
        }
        return cookies;
    }

    public void limpiar() {
        prefs.edit().clear().apply();
    }
}
