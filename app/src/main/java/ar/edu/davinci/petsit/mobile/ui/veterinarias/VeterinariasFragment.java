package ar.edu.davinci.petsit.mobile.ui.veterinarias;

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
import ar.edu.davinci.petsit.mobile.databinding.FragmentVeterinariasBinding;
import ar.edu.davinci.petsit.mobile.model.Veterinaria;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Equivale a veterinarias.html / usuarioveterinarias.html. */
public class VeterinariasFragment extends Fragment {

    private FragmentVeterinariasBinding binding;
    private VeterinariaAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        binding = FragmentVeterinariasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new VeterinariaAdapter(veterinaria -> {
            Intent intent = new Intent(getContext(), VeterinariaDetalleActivity.class);
            intent.putExtra(VeterinariaDetalleActivity.EXTRA_VETERINARIA_ID, veterinaria.getId());
            startActivity(intent);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        binding.swipeRefresh.setOnRefreshListener(this::cargarVeterinarias);

        cargarVeterinarias();
    }

    private void cargarVeterinarias() {
        binding.swipeRefresh.setRefreshing(true);

        ApiClient.getApiService().getVeterinarias().enqueue(new Callback<List<Veterinaria>>() {
            @Override
            public void onResponse(Call<List<Veterinaria>> call, Response<List<Veterinaria>> response) {
                if (binding == null) return;
                binding.swipeRefresh.setRefreshing(false);

                List<Veterinaria> veterinarias = response.body();
                adapter.setVeterinarias(veterinarias);
                binding.tvVacio.setVisibility(
                        (veterinarias == null || veterinarias.isEmpty()) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFailure(Call<List<Veterinaria>> call, Throwable t) {
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
