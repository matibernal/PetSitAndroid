package ar.edu.davinci.petsit.mobile.ui.mascotas;

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
import ar.edu.davinci.petsit.mobile.databinding.FragmentMascotasBinding;
import ar.edu.davinci.petsit.mobile.model.Mascota;
import ar.edu.davinci.petsit.mobile.util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Equivale a mis-mascotas.html: listado de mascotas del usuario logueado. */
public class MascotasFragment extends Fragment {

    private FragmentMascotasBinding binding;
    private MascotaAdapter adapter;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        binding = FragmentMascotasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(requireContext());

        adapter = new MascotaAdapter(mascota -> {
            Intent intent = new Intent(getContext(), MascotaDetalleActivity.class);
            intent.putExtra(MascotaDetalleActivity.EXTRA_MASCOTA_ID, mascota.getId());
            startActivity(intent);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(this::cargarMascotas);

        binding.fabAgregar.setOnClickListener(v ->
                startActivity(new Intent(getContext(), AgregarMascotaActivity.class)));

        cargarMascotas();
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarMascotas();
    }

    private void cargarMascotas() {
        binding.swipeRefresh.setRefreshing(true);

        ApiClient.getApiService().getMisMascotas()
                .enqueue(new Callback<List<Mascota>>() {
                    @Override
                    public void onResponse(Call<List<Mascota>> call, Response<List<Mascota>> response) {
                        if (binding == null) return;
                        binding.swipeRefresh.setRefreshing(false);

                        List<Mascota> mascotas = response.body();
                        adapter.setMascotas(mascotas);
                        binding.tvVacio.setVisibility(
                                (mascotas == null || mascotas.isEmpty()) ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<Mascota>> call, Throwable t) {
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
