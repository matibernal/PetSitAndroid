package ar.edu.davinci.petsit.mobile.ui.mascotas;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.ActivityMascotaDetalleBinding;
import ar.edu.davinci.petsit.mobile.model.Mascota;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MascotaDetalleActivity extends AppCompatActivity {

    public static final String EXTRA_MASCOTA_ID = "mascota_id";

    private ActivityMascotaDetalleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMascotaDetalleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        long mascotaId = getIntent().getLongExtra(EXTRA_MASCOTA_ID, -1);
        if (mascotaId == -1) {
            finish();
            return;
        }

        cargarMascota(mascotaId);
    }

    private void cargarMascota(long id) {
        ApiClient.getApiService().getMascota(id).enqueue(new Callback<Mascota>() {
            @Override
            public void onResponse(Call<Mascota> call, Response<Mascota> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrar(response.body());
                } else {
                    Toast.makeText(MascotaDetalleActivity.this, R.string.error_generico, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Mascota> call, Throwable t) {
                Toast.makeText(MascotaDetalleActivity.this,
                        getString(R.string.error_generico) + " (" + t.getMessage() + ")",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrar(Mascota mascota) {
        setTitle(mascota.getNombre());
        binding.tvNombre.setText(mascota.getNombre());

        StringBuilder meta = new StringBuilder();
        if (mascota.getRaza() != null) meta.append(mascota.getRaza());
        if (mascota.getColor() != null && !mascota.getColor().isEmpty()) {
            if (meta.length() > 0) meta.append(" · ");
            meta.append(mascota.getColor());
        }
        if (mascota.getSexo() != null) {
            if (meta.length() > 0) meta.append(" · ");
            meta.append(mascota.getSexo());
        }
        binding.tvMeta.setText(meta.toString());

        binding.tvEstado.setText(mascota.getTamano() != null ? mascota.getTamano() : "-");
        binding.tvNotas.setText(mascota.getDescripcion() != null && !mascota.getDescripcion().isEmpty()
                ? mascota.getDescripcion() : "Sin datos.");

        Glide.with(this).load(mascota.getFoto()).centerCrop().into(binding.ivFoto);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
