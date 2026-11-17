package com.petsit.app.screens.publicarea

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petsit.app.ui.components.PetSitPage
import com.petsit.app.ui.components.PrimaryButton
import com.petsit.app.ui.components.StaticField
import com.petsit.app.ui.theme.*

@Composable
fun ShelterLoginScreen(onEnter: () -> Unit) = PetSitPage {
    Spacer(Modifier.height(18.dp))
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(7.dp)
    ) {
        Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(shape = RoundedCornerShape(50), color = PetSitBrandSoft) {
                Text("⌂  RED DE REFUGIOS", color = PetSitBrand, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(horizontal = 13.dp, vertical = 7.dp))
            }
            Text("Ingreso para refugios", color = PetSitBrandDark, fontSize = 26.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 14.dp))
            Text("Accedé al espacio de tu organización", color = PetSitMuted, modifier = Modifier.padding(bottom = 18.dp))
            StaticField("Correo institucional")
            StaticField("Contraseña")
            PrimaryButton("Ingresar como refugio", onEnter)
            Text("Prototipo visual · autenticación pendiente", color = PetSitMuted, fontSize = 11.sp, modifier = Modifier.padding(top = 10.dp))
        }
    }
}
