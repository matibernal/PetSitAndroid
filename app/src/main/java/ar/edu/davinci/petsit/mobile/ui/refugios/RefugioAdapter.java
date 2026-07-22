package ar.edu.davinci.petsit.mobile.ui.refugios;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.petsit.mobile.databinding.ItemRefugioBinding;
import ar.edu.davinci.petsit.mobile.model.Refugio;

public class RefugioAdapter extends RecyclerView.Adapter<RefugioAdapter.ViewHolder> {

    public interface OnRefugioClickListener {
        void onRefugioClick(Refugio refugio);
    }

    private final List<Refugio> refugios = new ArrayList<>();
    private final OnRefugioClickListener listener;

    public RefugioAdapter(OnRefugioClickListener listener) {
        this.listener = listener;
    }

    public void setRefugios(List<Refugio> nuevos) {
        refugios.clear();
        if (nuevos != null) refugios.addAll(nuevos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRefugioBinding binding = ItemRefugioBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(refugios.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return refugios.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRefugioBinding binding;

        ViewHolder(ItemRefugioBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Refugio refugio, OnRefugioClickListener listener) {
            binding.tvNombre.setText(refugio.getNombre());
            binding.tvCiudad.setText(refugio.getUbicacion());

            Glide.with(binding.ivFoto.getContext())
                    .load(refugio.getFoto())
                    .centerCrop()
                    .into(binding.ivFoto);

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onRefugioClick(refugio);
            });
        }
    }
}
