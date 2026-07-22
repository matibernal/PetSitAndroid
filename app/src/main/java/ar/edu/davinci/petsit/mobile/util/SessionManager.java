package ar.edu.davinci.petsit.mobile.util;

import android.content.Context;
import android.content.SharedPreferences;

import ar.edu.davinci.petsit.mobile.model.Usuario;

public class SessionManager {

    private static final String PREFS_NAME = "petsit_session";
    private static final String KEY_USUARIO_ID = "usuario_id";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_CORREO = "correo";
    private static final String KEY_LOGGED_IN = "logged_in";

    private final Context appContext;
    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        appContext = context.getApplicationContext();
        prefs = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void guardarSesion(Usuario usuario) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_USUARIO_ID, usuario.getId() != null ? usuario.getId() : -1);
        editor.putString(KEY_NOMBRE, usuario.getNombreCompleto());
        editor.putString(KEY_CORREO, usuario.getCorreo());
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.apply();
    }

    public boolean estaLogueado() {
        return prefs.getBoolean(KEY_LOGGED_IN, false);
    }

    public long getUsuarioId() {
        return prefs.getLong(KEY_USUARIO_ID, -1);
    }

    public String getNombre() {
        return prefs.getString(KEY_NOMBRE, "");
    }

    public String getCorreo() {
        return prefs.getString(KEY_CORREO, "");
    }

    public void cerrarSesion() {
        prefs.edit().clear().apply();
        new PersistentCookieJar(appContext).limpiar();
    }
}
