package com.petsit.app.screens.user
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import ar.edu.davinci.petsit.mobile.R
import com.petsit.app.ui.components.*
import com.petsit.app.ui.theme.*
@Composable fun PetDetailScreen() = PetSitPage {
    Image(painterResource(R.drawable.pet_one), "Milo", Modifier.fillMaxWidth().height(285.dp).clip(RoundedCornerShape(25.dp)), contentScale = ContentScale.Crop)
    Text("Milo", color = PetSitBrandDark, fontSize = 32.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 18.dp))
    Text("Perro · Macho · 4 años", color = PetSitMuted); Spacer(Modifier.height(18.dp))
    InfoRow("Raza", "Border Collie"); InfoRow("Color", "Blanco y negro"); InfoRow("Tamaño", "Mediano")
    InfoRow("Características", "Collar verde, muy sociable"); PrimaryButton("Editar perfil")
}

