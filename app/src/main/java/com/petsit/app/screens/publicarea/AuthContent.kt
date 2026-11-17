package com.petsit.app.screens.publicarea
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import ar.edu.davinci.petsit.mobile.R
import com.petsit.app.ui.components.*
import com.petsit.app.ui.theme.*
@Composable internal fun AuthContent(
    register: Boolean,
    primaryAction: () -> Unit,
    secondaryAction: () -> Unit,
    recoveryAction: (() -> Unit)? = null,
    shelterLoginAction: (() -> Unit)? = null
) = PetSitPage {
    Spacer(Modifier.height(18.dp))
    Card(shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(7.dp)) {
        Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painterResource(R.drawable.petsit_login_logo), "PetSit", Modifier.fillMaxWidth(.64f).height(90.dp), contentScale = ContentScale.Fit)
            Text(if (register) "Creá tu cuenta" else "Bienvenido de nuevo", color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Text(if (register) "Sumate a la comunidad PetSit" else "Ingresá para continuar", color = PetSitMuted, modifier = Modifier.padding(bottom = 18.dp))
            if (register) StaticField("Nombre y apellido")
            StaticField("Correo electrónico"); StaticField("Contraseña")
            if (register) StaticField("Repetir contraseña")
            PrimaryButton(if (register) "Registrarme" else "Iniciar sesión", primaryAction)
            if (register) {
                TextButton(onClick = secondaryAction) { Text("Ya tengo cuenta", color = PetSitBrand) }
            } else {
                TextButton(onClick = shelterLoginAction ?: {}) {
                    Text("¿Sos refugio? Ingresá aquí", color = PetSitBrandDark, fontWeight = FontWeight.Bold)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    TextButton(onClick = recoveryAction ?: {}) { Text("Recuperar contraseña", color = PetSitBrand) }
                    Text("·", color = PetSitMuted)
                    TextButton(onClick = secondaryAction) { Text("Crear cuenta", color = PetSitBrand) }
                }
            }
        }
    }
}
