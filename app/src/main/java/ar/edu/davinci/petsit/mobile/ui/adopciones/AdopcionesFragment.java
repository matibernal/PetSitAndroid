package ar.edu.davinci.petsit.mobile.ui.adopciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.FragmentAdopcionesBinding;
import ar.edu.davinci.petsit.mobile.model.Adopcion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Listado público de mascotas en adopción (GET /petsit/api/adopciones). */
public class AdopcionesFragment extends Fragment {

    private FragmentAdopcionesBinding binding;
    private AdopcionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        binding = FragmentAdopcionesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new AdopcionAdapter(adopcion -> {
            Intent intent = new Intent(getContext(), AdopcionDetalleActivity.class);
            intent.putExtra(AdopcionDetalleActivity.EXTRA_ADOPCION_ID, adopcion.getId());
            startActivity(intent);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(this::cargarAdopciones);

        binding.fabPublicar.setOnClickListener(v ->
                startActivity(new Intent(getContext(), PublicarAdopcionActivity.class)));

        cargarAdopciones();
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarAdopciones();
    }

    private void cargarAdopciones() {
        binding.swipeRefresh.setRefreshing(true);

        ApiClient.getApiService().getAdopciones()
                .enqueue(new Callback<List<Adopcion>>() {
                    @Override
                    public void onResponse(Call<List<Adopcion>> call, Response<List<Adopcion>> response) {
                        if (binding == null) return;
                        binding.swipeRefresh.setRefreshing(false);

                        List<Adopcion> adopciones = response.body();
                        adapter.setAdopciones(adopciones);
                        binding.tvVacio.setVisibility(
                                (adopciones == null || adopciones.isEmpty()) ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<Adopcion>> call, Throwable t) {
                        if (binding == null) return;
                        binding.swipeRefresh.setRefreshing(false);
                        binding.tvVacio.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(),
                                getString(R.string.error_generico) + " (" + t.getMessage() + ")",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
