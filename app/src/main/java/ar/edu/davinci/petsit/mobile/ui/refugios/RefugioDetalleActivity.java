package ar.edu.davinci.petsit.mobile.ui.refugios;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.ActivityRefugioDetalleBinding;
import ar.edu.davinci.petsit.mobile.model.Refugio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefugioDetalleActivity extends AppCompatActivity {

    public static final String EXTRA_REFUGIO_ID = "refugio_id";

    private ActivityRefugioDetalleBinding binding;
    private Refugio refugioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRefugioDetalleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        long refugioId = getIntent().getLongExtra(EXTRA_REFUGIO_ID, -1);
        if (refugioId == -1) {
            finish();
            return;
        }

        binding.btnLlamar.setOnClickListener(v -> llamar());
        binding.btnMaps.setOnClickListener(v -> verEnMaps());

        cargarRefugio(refugioId);
    }

    private void cargarRefugio(long id) {
        ApiClient.getApiService().getRefugio(id).enqueue(new Callback<Refugio>() {
            @Override
            public void onResponse(Call<Refugio> call, Response<Refugio> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrar(response.body());
                } else {
                    Toast.makeText(RefugioDetalleActivity.this, R.string.error_generico, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Refugio> call, Throwable t) {
                Toast.makeText(RefugioDetalleActivity.this,
                        getString(R.string.error_generico) + " (" + t.getMessage() + ")",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrar(Refugio refugio) {
        refugioActual = refugio;
        setTitle(refugio.getNombre());
        binding.tvNombre.setText(refugio.getNombre());
        binding.tvDireccion.setText(refugio.getDireccion());
        binding.tvTelefono.setText(refugio.getTelefono());

        // El refugio no tiene horario de atención en el backend real;
        // reusamos ese renglón para mostrar el correo de contacto, si hay.
        if (refugio.getCorreo() != null && !refugio.getCorreo().isEmpty()) {
            binding.tvHorarios.setText(refugio.getCorreo());
            binding.tvHorarios.setVisibility(android.view.View.VISIBLE);
        } else {
            binding.tvHorarios.setVisibility(android.view.View.GONE);
        }

        Glide.with(this).load(refugio.getFoto()).centerCrop().into(binding.ivFoto);
    }

    private void llamar() {
        if (refugioActual == null || refugioActual.getTelefono() == null) return;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + refugioActual.getTelefono()));
        startActivity(intent);
    }

    private void verEnMaps() {
        if (refugioActual == null) return;
        String query = Uri.encode(refugioActual.getDireccion() + " " + refugioActual.getUbicacion());
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
