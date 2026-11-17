package ar.edu.davinci.petsit.mobile.ui.postulaciones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ar.edu.davinci.petsit.mobile.databinding.ItemPostulacionBinding;
import ar.edu.davinci.petsit.mobile.model.Postulacion;

public class PostulacionAdapter extends RecyclerView.Adapter<PostulacionAdapter.ViewHolder> {

    private final List<Postulacion> postulaciones = new ArrayList<>();
    private boolean recibidas = true;

    /** recibidas=true muestra quién se postuló (con contacto); false muestra quién publicó. */
    public void setPostulaciones(List<Postulacion> nuevas, boolean recibidas) {
        this.recibidas = recibidas;
        postulaciones.clear();
        if (nuevas != null) postulaciones.addAll(nuevas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostulacionBinding binding = ItemPostulacionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(postulaciones.get(position), recibidas);
    }

    @Override
    public int getItemCount() {
        return postulaciones.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostulacionBinding binding;

        ViewHolder(ItemPostulacionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Postulacion p, boolean recibidas) {
            binding.tvMascota.setText(p.getMascotaNombre());

            if (recibidas) {
                binding.tvPersona.setText("Se postuló: " + valor(p.getUsuarioNombre()));

                StringBuilder contacto = new StringBuilder();
                if (p.getUsuarioCorreo() != null && !p.getUsuarioCorreo().isEmpty()) {
                    contacto.append(p.getUsuarioCorreo());
                }
                if (p.getUsuarioTelefono() != null && !p.getUsuarioTelefono().isEmpty()) {
                    if (contacto.length() > 0) contacto.append(" · ");
                    contacto.append(p.getUsuarioTelefono());
                }
                binding.tvContacto.setText(contacto.toString());
                binding.tvContacto.setVisibility(contacto.length() > 0 ? View.VISIBLE : View.GONE);
            } else {
                binding.tvPersona.setText("Publicó: " + valor(p.getPublicadorNombre()));
                binding.tvContacto.setVisibility(View.GONE);
            }

            binding.tvMensaje.setText(p.getMensaje() != null && !p.getMensaje().isEmpty()
                    ? p.getMensaje() : "Sin mensaje.");
            binding.tvFecha.setText(p.getFecha() != null ? p.getFecha() : "");

            Glide.with(binding.ivFoto.getContext())
                    .load(p.getMascotaFoto())
                    .centerCrop()
                    .into(binding.ivFoto);
        }

        private static String valor(String s) {
            return s != null ? s : "-";
        }
    }
}
