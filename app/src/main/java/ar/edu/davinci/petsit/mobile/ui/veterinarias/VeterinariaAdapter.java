package ar.edu.davinci.petsit.mobile.ui.veterinarias;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.petsit.mobile.databinding.ItemVeterinariaBinding;
import ar.edu.davinci.petsit.mobile.model.Veterinaria;

public class VeterinariaAdapter extends RecyclerView.Adapter<VeterinariaAdapter.ViewHolder> {

    public interface OnVeterinariaClickListener {
        void onVeterinariaClick(Veterinaria veterinaria);
    }

    private final List<Veterinaria> veterinarias = new ArrayList<>();
    private final OnVeterinariaClickListener listener;

    public VeterinariaAdapter(OnVeterinariaClickListener listener) {
        this.listener = listener;
    }

    public void setVeterinarias(List<Veterinaria> nuevas) {
        veterinarias.clear();
        if (nuevas != null) veterinarias.addAll(nuevas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVeterinariaBinding binding = ItemVeterinariaBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(veterinarias.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return veterinarias.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemVeterinariaBinding binding;

        ViewHolder(ItemVeterinariaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Veterinaria veterinaria, OnVeterinariaClickListener listener) {
            binding.tvNombre.setText(veterinaria.getNombre());
            binding.tvCiudad.setText(veterinaria.getUbicacion());
            binding.tvTags.setText(veterinaria.getHorarioAtencion() != null ? veterinaria.getHorarioAtencion() : "");

            Glide.with(binding.ivFoto.getContext())
                    .load(veterinaria.getFoto())
                    .centerCrop()
                    .into(binding.ivFoto);

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onVeterinariaClick(veterinaria);
            });
        }
    }
}
