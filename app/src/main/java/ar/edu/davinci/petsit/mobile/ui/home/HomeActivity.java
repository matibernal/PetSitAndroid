package ar.edu.davinci.petsit.mobile.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ar.edu.davinci.petsit.mobile.R;
import ar.edu.davinci.petsit.mobile.api.ApiClient;
import ar.edu.davinci.petsit.mobile.databinding.ActivityHomeBinding;
import ar.edu.davinci.petsit.mobile.ui.mascotas.MascotasFragment;
import ar.edu.davinci.petsit.mobile.ui.perfil.PerfilFragment;
import ar.edu.davinci.petsit.mobile.ui.refugios.RefugiosFragment;
import ar.edu.davinci.petsit.mobile.ui.veterinarias.VeterinariasFragment;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiClient.init(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            mostrarFragment(new MascotasFragment());
        }

        binding.bottomNav.setOnItemSelectedListener(this::onNavItemSelected);
    }

    private boolean onNavItemSelected(@NonNull android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_mascotas) {
            mostrarFragment(new MascotasFragment());
            return true;
        } else if (id == R.id.nav_refugios) {
            mostrarFragment(new RefugiosFragment());
            return true;
        } else if (id == R.id.nav_veterinarias) {
            mostrarFragment(new VeterinariasFragment());
            return true;
        } else if (id == R.id.nav_perfil) {
            mostrarFragment(new PerfilFragment());
            return true;
        }
        return false;
    }

    private void mostrarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
