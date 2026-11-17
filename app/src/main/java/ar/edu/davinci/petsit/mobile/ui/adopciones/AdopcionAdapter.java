package ar.edu.davinci.petsit.mobile.ui.adopciones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.petsit.mobile.databinding.ItemAdopcionBinding;
import ar.edu.davinci.petsit.mobile.model.Adopcion;

public class AdopcionAdapter extends RecyclerView.Adapter<AdopcionAdapter.ViewHolder> {

    public interface OnAdopcionClickListener {
        void onAdopcionClick(Adopcion adopcion);
    }

    private final List<Adopcion> adopciones = new ArrayList<>();
    private final OnAdopcionClickListener listener;

    public AdopcionAdapter(OnAdopcionClickListener listener) {
        this.listener = listener;
    }

    public void setAdopciones(List<Adopcion> nuevas) {
        adopciones.clear();
        if (nuevas != null) adopciones.addAll(nuevas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdopcionBinding binding = ItemAdopcionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(adopciones.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return adopciones.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAdopcionBinding binding;

        ViewHolder(ItemAdopcionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Adopcion adopcion, OnAdopcionClickListener listener) {
            binding.tvNombre.setText(adopcion.getMascotaNombre());

            String meta = adopcion.getUbicacion();
            binding.tvMeta.setText(meta != null && !meta.isEmpty() ? meta : "-");

            binding.tvEstado.setText(adopcion.getEstado() != null ? adopcion.getEstado() : "-");

            String foto = adopcion.getFoto() != null && !adopcion.getFoto().isEmpty()
                    ? adopcion.getFoto() : adopcion.getMascotaFoto();

            Glide.with(binding.ivFoto.getContext())
                    .load(foto)
                    .centerCrop()
                    .into(binding.ivFoto);

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onAdopcionClick(adopcion);
            });
        }
    }
}
