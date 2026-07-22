package ar.edu.davinci.petsit.mobile.ui.registro;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.ActivityRegistroBinding;
import ar.edu.davinci.petsit.mobile.model.RegistroRequest;
import ar.edu.davinci.petsit.mobile.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private ActivityRegistroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegistrarse.setOnClickListener(v -> intentarRegistro());
        binding.tvYaTengoCuenta.setOnClickListener(v -> finish());
    }

    private void intentarRegistro() {
        String nombre = String.valueOf(binding.etNombre.getText()).trim();
        String apellido = String.valueOf(binding.etApellido.getText()).trim();
        String telefono = String.valueOf(binding.etTelefono.getText()).trim();
        String correo = String.valueOf(binding.etEmail.getText()).trim();
        String contrasena = String.valueOf(binding.etPassword.getText()).trim();
        String contrasenaConfirm = String.valueOf(binding.etPasswordConfirm.getText()).trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido)
                || TextUtils.isEmpty(correo) || TextUtils.isEmpty(contrasena)) {
            Toast.makeText(this, R.string.error_campos, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contrasena.equals(contrasenaConfirm)) {
            Toast.makeText(this, R.string.error_passwords, Toast.LENGTH_SHORT).show();
            return;
        }

        mostrarCargando(true);

        // POST /petsit/api/usuarios/registro: nuevo endpoint (ver
        // backend-para-tu-proyecto/). Registra siempre como DUENO.
        RegistroRequest request = new RegistroRequest(nombre, apellido, correo, contrasena, telefono);

        ApiClient.getApiService().registrarUsuario(request)
                .enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        mostrarCargando(false);
                        if (response.isSuccessful()) {
                            Toast.makeText(RegistroActivity.this,
                                    "Cuenta creada. Ya podés iniciar sesión.", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(RegistroActivity.this, R.string.error_generico, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        mostrarCargando(false);
                        Toast.makeText(RegistroActivity.this,
                                getString(R.string.error_generico) + " (" + t.getMessage() + ")",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void mostrarCargando(boolean cargando) {
        binding.progressBar.setVisibility(cargando ? android.view.View.VISIBLE : android.view.View.GONE);
        binding.btnRegistrarse.setEnabled(!cargando);
    }
}
