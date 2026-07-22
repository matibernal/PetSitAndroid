package ar.edu.davinci.petsit.mobile.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.ActivityLoginBinding;
import ar.edu.davinci.petsit.mobile.model.LoginRequest;
import ar.edu.davinci.petsit.mobile.model.Usuario;
import ar.edu.davinci.petsit.mobile.ui.home.HomeActivity;
import ar.edu.davinci.petsit.mobile.ui.registro.RegistroActivity;
import ar.edu.davinci.petsit.mobile.util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ApiClient necesita el Context para el CookieJar; se inicializa acá
        // porque esta es la primera Activity que arranca la app.
        ApiClient.init(this);

        sessionManager = new SessionManager(this);

        if (sessionManager.estaLogueado()) {
            irAHome();
            return;
        }

        binding.btnLogin.setOnClickListener(v -> intentarLogin());
        binding.tvCrearCuenta.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));
    }

    private void intentarLogin() {
        String correo = String.valueOf(binding.etEmail.getText()).trim();
        String contrasena = String.valueOf(binding.etPassword.getText()).trim();

        if (TextUtils.isEmpty(correo) || TextUtils.isEmpty(contrasena)) {
            Toast.makeText(this, R.string.error_campos, Toast.LENGTH_SHORT).show();
            return;
        }

        mostrarCargando(true);

        // POST /petsit/api/auth/login: si las credenciales son correctas,
        // el backend deja la sesión (cookie JSESSIONID) autenticada y
        // devuelve el Usuario en el body. Retrofit/OkHttp guardan la
        // cookie solos gracias al CookieJar de ApiClient.
        ApiClient.getApiService().login(new LoginRequest(correo, contrasena))
                .enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        mostrarCargando(false);
                        if (response.isSuccessful() && response.body() != null) {
                            sessionManager.guardarSesion(response.body());
                            irAHome();
                        } else if (response.code() == 401) {
                            Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.error_generico, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        mostrarCargando(false);
                        Toast.makeText(LoginActivity.this,
                                getString(R.string.error_generico) + " (" + t.getMessage() + ")",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void mostrarCargando(boolean cargando) {
        binding.progressBar.setVisibility(cargando ? android.view.View.VISIBLE : android.view.View.GONE);
        binding.btnLogin.setEnabled(!cargando);
    }

    private void irAHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
