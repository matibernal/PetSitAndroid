package ar.edu.davinci.petsit.mobile.ui.postulaciones;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.ActivityPostulacionesBinding;
import ar.edu.davinci.petsit.mobile.model.Postulacion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** "Mis solicitudes": postulaciones recibidas en mis adopciones y las que envié yo. */
public class PostulacionesActivity extends AppCompatActivity {

    private ActivityPostulacionesBinding binding;
    private PostulacionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostulacionesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(R.string.mis_solicitudes);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        adapter = new PostulacionAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) return;
            cargar(checkedId == R.id.btnRecibidas);
        });

        cargar(true);
    }

    private void cargar(boolean recibidas) {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.tvVacio.setVisibility(View.GONE);

        Call<List<Postulacion>> call = recibidas
                ? ApiClient.getApiService().getPostulacionesRecibidas()
                : ApiClient.getApiService().getMisPostulaciones();

        call.enqueue(new Callback<List<Postulacion>>() {
            @Override
            public void onResponse(Call<List<Postulacion>> call, Response<List<Postulacion>> response) {
                binding.progressBar.setVisibility(View.GONE);
                List<Postulacion> lista = response.isSuccessful() ? response.body() : null;
                adapter.setPostulaciones(lista, recibidas);
                binding.tvVacio.setVisibility(
                        (lista == null || lista.isEmpty()) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFailure(Call<List<Postulacion>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                adapter.setPostulaciones(null, recibidas);
                binding.tvVacio.setVisibility(View.VISIBLE);
                Toast.makeText(PostulacionesActivity.this,
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
