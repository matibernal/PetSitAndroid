package ar.edu.davinci.petsit.mobile.ui.mascotas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.petsit.mobile.databinding.ItemMascotaBinding;
import ar.edu.davinci.petsit.mobile.model.Mascota;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.ViewHolder> {

    public interface OnMascotaClickListener {
        void onMascotaClick(Mascota mascota);
    }

    private final List<Mascota> mascotas = new ArrayList<>();
    private final OnMascotaClickListener listener;

    public MascotaAdapter(OnMascotaClickListener listener) {
        this.listener = listener;
    }

    public void setMascotas(List<Mascota> nuevas) {
        mascotas.clear();
        if (nuevas != null) mascotas.addAll(nuevas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMascotaBinding binding = ItemMascotaBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mascotas.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMascotaBinding binding;

        ViewHolder(ItemMascotaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Mascota mascota, OnMascotaClickListener listener) {
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

            Glide.with(binding.ivFoto.getContext())
                    .load(mascota.getFoto())
                    .centerCrop()
                    .into(binding.ivFoto);

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onMascotaClick(mascota);
            });
        }
    }
}
