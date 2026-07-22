package ar.edu.davinci.petsit.mobile.ui.mascotas;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.ActivityAgregarMascotaBinding;
import ar.edu.davinci.petsit.mobile.model.Mascota;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarMascotaActivity extends AppCompatActivity {

    private ActivityAgregarMascotaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarMascotaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(R.string.agregar_mascota);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        binding.btnGuardar.setOnClickListener(v -> guardar());
    }

    private void guardar() {
        String nombre = String.valueOf(binding.etNombre.getText()).trim();
        String raza = String.valueOf(binding.etRaza.getText()).trim();
        String color = String.valueOf(binding.etColor.getText()).trim();
        String descripcion = String.valueOf(binding.etDescripcion.getText()).trim();
        String sexo = binding.rbMacho.isChecked() ? "Macho" : "Hembra";

        String tamano;
        if (binding.rbMediano.isChecked()) {
            tamano = "Mediano";
        } else if (binding.rbGrande.isChecked()) {
            tamano = "Grande";
        } else {
            tamano = "Chico";
        }

        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, R.string.error_campos, Toast.LENGTH_SHORT).show();
            return;
        }

        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setRaza(raza);
        mascota.setColor(color);
        mascota.setTamano(tamano);
        mascota.setSexo(sexo);
        mascota.setDescripcion(descripcion);

        mostrarCargando(true);

        ApiClient.getApiService().crearMascota(mascota).enqueue(new Callback<Mascota>() {
            @Override
            public void onResponse(Call<Mascota> call, Response<Mascota> response) {
                mostrarCargando(false);
                if (response.isSuccessful()) {
                    Toast.makeText(AgregarMascotaActivity.this, "Mascota guardada.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AgregarMascotaActivity.this, R.string.error_generico, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Mascota> call, Throwable t) {
                mostrarCargando(false);
                Toast.makeText(AgregarMascotaActivity.this,
                        getString(R.string.error_generico) + " (" + t.getMessage() + ")",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarCargando(boolean cargando) {
        binding.progressBar.setVisibility(cargando ? android.view.View.VISIBLE : android.view.View.GONE);
        binding.btnGuardar.setEnabled(!cargando);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
