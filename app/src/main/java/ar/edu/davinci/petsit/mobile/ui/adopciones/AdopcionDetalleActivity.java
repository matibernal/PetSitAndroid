package ar.edu.davinci.petsit.mobile.ui.adopciones;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.ActivityAdopcionDetalleBinding;
import ar.edu.davinci.petsit.mobile.model.Adopcion;
import ar.edu.davinci.petsit.mobile.model.CrearPostulacionRequest;
import ar.edu.davinci.petsit.mobile.model.Postulacion;
import ar.edu.davinci.petsit.mobile.util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdopcionDetalleActivity extends AppCompatActivity {

    public static final String EXTRA_ADOPCION_ID = "adopcion_id";

    private ActivityAdopcionDetalleBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdopcionDetalleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

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

        boolean esMia = adopcion.getUsuarioId() != null
                && adopcion.getUsuarioId() == sessionManager.getUsuarioId();
        boolean activa = "ACTIVA".equals(adopcion.getEstado());
        if (activa && !esMia) {
            binding.btnPostularme.setVisibility(View.VISIBLE);
            binding.btnPostularme.setOnClickListener(v -> pedirMensaje(adopcion.getId()));
        }
    }

    private void pedirMensaje(long adopcionId) {
        EditText etMensaje = new EditText(this);
        etMensaje.setHint(R.string.mensaje_postulacion_hint);
        etMensaje.setMinLines(3);
        etMensaje.setGravity(android.view.Gravity.TOP);

        int margen = (int) (20 * getResources().getDisplayMetrics().density);
        android.widget.FrameLayout contenedor = new android.widget.FrameLayout(this);
        contenedor.setPadding(margen, margen / 2, margen, 0);
        contenedor.addView(etMensaje);

        new AlertDialog.Builder(this)
                .setTitle(R.string.postularme_titulo)
                .setView(contenedor)
                .setPositiveButton(R.string.enviar, (dialog, which) ->
                        enviarPostulacion(adopcionId, String.valueOf(etMensaje.getText()).trim()))
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }

    private void enviarPostulacion(long adopcionId, String mensaje) {
        binding.btnPostularme.setEnabled(false);

        ApiClient.getApiService().postularse(new CrearPostulacionRequest(adopcionId, mensaje))
                .enqueue(new Callback<Postulacion>() {
                    @Override
                    public void onResponse(Call<Postulacion> call, Response<Postulacion> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AdopcionDetalleActivity.this, R.string.postulacion_ok, Toast.LENGTH_SHORT).show();
                            binding.btnPostularme.setVisibility(View.GONE);
                        } else if (response.code() == 409) {
                            Toast.makeText(AdopcionDetalleActivity.this, R.string.postulacion_duplicada, Toast.LENGTH_SHORT).show();
                            binding.btnPostularme.setVisibility(View.GONE);
                        } else {
                            binding.btnPostularme.setEnabled(true);
                            Toast.makeText(AdopcionDetalleActivity.this, R.string.error_generico, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Postulacion> call, Throwable t) {
                        binding.btnPostularme.setEnabled(true);
                        Toast.makeText(AdopcionDetalleActivity.this,
                                getString(R.string.error_generico) + " (" + t.getMessage() + ")",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
