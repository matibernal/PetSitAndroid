package ar.edu.davinci.petsit.mobile.ui.adopciones;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.ActivityPublicarAdopcionBinding;
import ar.edu.davinci.petsit.mobile.model.Adopcion;
import ar.edu.davinci.petsit.mobile.model.CrearAdopcionRequest;
import ar.edu.davinci.petsit.mobile.model.Mascota;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Formulario para publicar una de mis mascotas en adopción. */
public class PublicarAdopcionActivity extends AppCompatActivity {

    private ActivityPublicarAdopcionBinding binding;
    private final List<Mascota> misMascotas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPublicarAdopcionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(R.string.publicar_adopcion);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        binding.btnGuardar.setOnClickListener(v -> guardar());

        cargarMisMascotas();
    }

    private void cargarMisMascotas() {
        ApiClient.getApiService().getMisMascotas().enqueue(new Callback<List<Mascota>>() {
            @Override
            public void onResponse(Call<List<Mascota>> call, Response<List<Mascota>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    misMascotas.clear();
                    misMascotas.addAll(response.body());

                    List<String> nombres = new ArrayList<>();
                    for (Mascota m : misMascotas) nombres.add(m.getNombre());

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                            PublicarAdopcionActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, nombres);
                    binding.spinnerMascota.setAdapter(spinnerAdapter);

                    binding.formContainer.setVisibility(View.VISIBLE);
                    binding.tvSinMascotas.setVisibility(View.GONE);
                } else {
                    binding.formContainer.setVisibility(View.GONE);
                    binding.tvSinMascotas.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Mascota>> call, Throwable t) {
                binding.formContainer.setVisibility(View.GONE);
                binding.tvSinMascotas.setVisibility(View.VISIBLE);
                Toast.makeText(PublicarAdopcionActivity.this,
                        getString(R.string.error_generico) + " (" + t.getMessage() + ")",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void guardar() {
        int posicion = binding.spinnerMascota.getSelectedItemPosition();
        if (posicion < 0 || posicion >= misMascotas.size()) {
            Toast.makeText(this, R.string.error_campos, Toast.LENGTH_SHORT).show();
            return;
        }

        Long mascotaId = misMascotas.get(posicion).getId();
        String descripcion = String.valueOf(binding.etDescripcion.getText()).trim();
        String ubicacion = String.valueOf(binding.etUbicacion.getText()).trim();

        mostrarCargando(true);

        CrearAdopcionRequest request = new CrearAdopcionRequest(mascotaId, descripcion, ubicacion);
        ApiClient.getApiService().crearAdopcion(request).enqueue(new Callback<Adopcion>() {
            @Override
            public void onResponse(Call<Adopcion> call, Response<Adopcion> response) {
                mostrarCargando(false);
                if (response.isSuccessful()) {
                    Toast.makeText(PublicarAdopcionActivity.this, R.string.publicado_ok, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PublicarAdopcionActivity.this, R.string.error_generico, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Adopcion> call, Throwable t) {
                mostrarCargando(false);
                Toast.makeText(PublicarAdopcionActivity.this,
                        getString(R.string.error_generico) + " (" + t.getMessage() + ")",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarCargando(boolean cargando) {
        binding.progressBar.setVisibility(cargando ? View.VISIBLE : View.GONE);
        binding.btnGuardar.setEnabled(!cargando);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
