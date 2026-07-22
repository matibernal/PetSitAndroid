package ar.edu.davinci.petsit.mobile.api;

import java.util.List;

import ar.edu.davinci.petsit.mobile.model.LoginRequest;
import ar.edu.davinci.petsit.mobile.model.Mascota;
import ar.edu.davinci.petsit.mobile.model.RegistroRequest;
import ar.edu.davinci.petsit.mobile.model.Refugio;
import ar.edu.davinci.petsit.mobile.model.Usuario;
import ar.edu.davinci.petsit.mobile.model.Veterinaria;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // ---------- Auth ----------

    @POST("petsit/api/auth/login")
    Call<Usuario> login(@Body LoginRequest request);

    @GET("petsit/api/usuarios/me")
    Call<Usuario> getUsuarioActual();

    @POST("petsit/api/usuarios/registro")
    Call<Usuario> registrarUsuario(@Body RegistroRequest request);

    // ---------- Mascotas ----------

    @GET("petsit/api/mascotas/mias")
    Call<List<Mascota>> getMisMascotas();

    @GET("petsit/api/mascotas/{id}")
    Call<Mascota> getMascota(@Path("id") long id);

    @POST("petsit/api/mascotas")
    Call<Mascota> crearMascota(@Body Mascota mascota);

    // ---------- Refugios ----------

    @GET("petsit/api/refugios")
    Call<List<Refugio>> getRefugios();

    @GET("petsit/api/refugios/{id}")
    Call<Refugio> getRefugio(@Path("id") long id);

    // ---------- Veterinarias ----------

    @GET("petsit/api/veterinarias")
    Call<List<Veterinaria>> getVeterinarias();

    @GET("petsit/api/veterinarias/{id}")
    Call<Veterinaria> getVeterinaria(@Path("id") long id);
}
