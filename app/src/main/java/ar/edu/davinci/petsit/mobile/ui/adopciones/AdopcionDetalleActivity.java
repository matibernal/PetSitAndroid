package ar.edu.davinci.petsit.mobile.ui.adopciones;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.ActivityAdopcionDetalleBinding;
import ar.edu.davinci.petsit.mobile.model.Adopcion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdopcionDetalleActivity extends AppCompatActivity {

    public static final String EXTRA_ADOPCION_ID = "adopcion_id";

    private ActivityAdopcionDetalleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdopcionDetalleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        long adopcionId = getIntent().getLongExtra(EXTRA_ADOPCION_ID, -1);
        if (adopcionId == -1) {
            finish();
            return;
        }

        cargarAdopcion(adopcionId);
    }

    private void cargarAdopcion(long id) {
        ApiClient.getApiService().getAdopcion(id).enqueue(new Callback<Adopcion>() {
            @Override
            public void onResponse(Call<Adopcion> call, Response<Adopcion> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrar(response.body());
                } else {
                    Toast.makeText(AdopcionDetalleActivity.this, R.string.error_generico, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Adopcion> call, Throwable t) {
                Toast.makeText(AdopcionDetalleActivity.this,
                        getString(R.string.error_generico) + " (" + t.getMessage() + ")",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrar(Adopcion adopcion) {
        setTitle(adopcion.getMascotaNombre());
        binding.tvNombre.setText(adopcion.getMascotaNombre());
        binding.tvMeta.setText(adopcion.getFechaPublicacion() != null ? adopcion.getFechaPublicacion() : "");
        binding.tvEstado.setText(adopcion.getEstado() != null ? adopcion.getEstado() : "-");
        binding.tvNotas.setText(adopcion.getDescripcion() != null && !adopcion.getDescripcion().isEmpty()
                ? adopcion.getDescripcion() : "Sin datos.");

        if (adopcion.getUbicacion() != null && !adopcion.getUbicacion().isEmpty()) {
            binding.tvUbicacionLabel.setVisibility(View.VISIBLE);
            binding.tvUbicacion.setVisibility(View.VISIBLE);
            binding.tvUbicacion.setText(adopcion.getUbicacion());
        }

        binding.tvPublicadoPor.setText(adopcion.getUsuarioNombre() != null
                ? adopcion.getUsuarioNombre() : "-");

        String foto = adopcion.getFoto() != null && !adopcion.getFoto().isEmpty()
                ? adopcion.getFoto() : adopcion.getMascotaFoto();
        Glide.with(this).load(foto).centerCrop().into(binding.ivFoto);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
