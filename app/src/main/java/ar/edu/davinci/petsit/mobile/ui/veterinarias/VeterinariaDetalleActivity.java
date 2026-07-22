package ar.edu.davinci.petsit.mobile.ui.veterinarias;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.ActivityVeterinariaDetalleBinding;
import ar.edu.davinci.petsit.mobile.model.Veterinaria;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VeterinariaDetalleActivity extends AppCompatActivity {

    public static final String EXTRA_VETERINARIA_ID = "veterinaria_id";

    private ActivityVeterinariaDetalleBinding binding;
    private Veterinaria veterinariaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVeterinariaDetalleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        long veterinariaId = getIntent().getLongExtra(EXTRA_VETERINARIA_ID, -1);
        if (veterinariaId == -1) {
            finish();
            return;
        }

        binding.btnLlamar.setOnClickListener(v -> llamar());
        binding.btnMaps.setOnClickListener(v -> verEnMaps());

        cargarVeterinaria(veterinariaId);
    }

    private void cargarVeterinaria(long id) {
        ApiClient.getApiService().getVeterinaria(id).enqueue(new Callback<Veterinaria>() {
            @Override
            public void onResponse(Call<Veterinaria> call, Response<Veterinaria> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrar(response.body());
                } else {
                    Toast.makeText(VeterinariaDetalleActivity.this, R.string.error_generico, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Veterinaria> call, Throwable t) {
                Toast.makeText(VeterinariaDetalleActivity.this,
                        getString(R.string.error_generico) + " (" + t.getMessage() + ")",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrar(Veterinaria veterinaria) {
        veterinariaActual = veterinaria;
        setTitle(veterinaria.getNombre());
        binding.tvNombre.setText(veterinaria.getNombre());
        binding.tvDireccion.setText(veterinaria.getDireccion());
        binding.tvTelefono.setText(veterinaria.getTelefono());
        binding.tvHorarios.setText(veterinaria.getHorarioAtencion());

        // El backend real no tiene flags de "a domicilio"/"guardia 24h";
        // ese renglón queda libre, así que no mostramos tvTags.
        binding.tvTags.setVisibility(android.view.View.GONE);

        Glide.with(this).load(veterinaria.getFoto()).centerCrop().into(binding.ivFoto);
    }

    private void llamar() {
        if (veterinariaActual == null || veterinariaActual.getTelefono() == null) return;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + veterinariaActual.getTelefono()));
        startActivity(intent);
    }

    private void verEnMaps() {
        if (veterinariaActual == null) return;
        String query = Uri.encode(veterinariaActual.getDireccion() + " " + veterinariaActual.getUbicacion());
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/search/?api=1&query=" + query));
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
