package ar.edu.davinci.petsit.mobile.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import ar.edu.davinci.petsit.mobile.util.PersistentCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://10.0.2.2:8090/";

    private static Retrofit retrofit;

    public static void init(Context context) {
        if (retrofit != null) return;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .cookieJar(new PersistentCookieJar(context.getApplicationContext()))
                .addInterceptor(logging)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        if (retrofit == null) {
            throw new IllegalStateException("Llamá a ApiClient.init(context) antes de usar la API.");
        }
        return retrofit.create(ApiService.class);
    }
}
