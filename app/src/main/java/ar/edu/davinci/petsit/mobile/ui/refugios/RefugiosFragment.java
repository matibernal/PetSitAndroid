package ar.edu.davinci.petsit.mobile.ui.refugios;

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
import ar.edu.davinci.petsit.mobile.databinding.FragmentRefugiosBinding;
import ar.edu.davinci.petsit.mobile.model.Refugio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Equivale a refugios.html / usuariorefugio.html. */
public class RefugiosFragment extends Fragment {

    private FragmentRefugiosBinding binding;
    private RefugioAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        binding = FragmentRefugiosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new RefugioAdapter(refugio -> {
            Intent intent = new Intent(getContext(), RefugioDetalleActivity.class);
            intent.putExtra(RefugioDetalleActivity.EXTRA_REFUGIO_ID, refugio.getId());
            startActivity(intent);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        binding.swipeRefresh.setOnRefreshListener(this::cargarRefugios);

        cargarRefugios();
    }

    private void cargarRefugios() {
        binding.swipeRefresh.setRefreshing(true);

        ApiClient.getApiService().getRefugios().enqueue(new Callback<List<Refugio>>() {
            @Override
            public void onResponse(Call<List<Refugio>> call, Response<List<Refugio>> response) {
                if (binding == null) return;
                binding.swipeRefresh.setRefreshing(false);

                List<Refugio> refugios = response.body();
                adapter.setRefugios(refugios);
                binding.tvVacio.setVisibility(
                        (refugios == null || refugios.isEmpty()) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFailure(Call<List<Refugio>> call, Throwable t) {
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
