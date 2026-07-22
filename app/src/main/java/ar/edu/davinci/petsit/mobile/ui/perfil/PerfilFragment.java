package ar.edu.davinci.petsit.mobile.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ar.edu.davinci.petsit.mobile.databinding.FragmentPerfilBinding;
import ar.edu.davinci.petsit.mobile.ui.login.LoginActivity;
import ar.edu.davinci.petsit.mobile.util.SessionManager;

/** Equivale a perfil.html; acá se ve el usuario logueado y se cierra sesión. */
public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(requireContext());
        binding.tvNombre.setText(sessionManager.getNombre());
        binding.tvEmail.setText(sessionManager.getCorreo());

        binding.btnCerrarSesion.setOnClickListener(v -> {
            sessionManager.cerrarSesion();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
