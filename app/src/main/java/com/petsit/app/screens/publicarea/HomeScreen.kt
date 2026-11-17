package com.petsit.app.screens.publicarea

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import ar.edu.davinci.petsit.mobile.R
import com.petsit.app.ui.components.*
import com.petsit.app.ui.theme.*

@Composable
fun HomeScreen(onCreateAccount: () -> Unit) = PetSitPage {
    Box(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(50))
            .background(PetSitBrandSoft).padding(horizontal = 12.dp, vertical = 7.dp)
    ) {
        Text("♥  UNA RED PARA VOLVER A CASA", color = PetSitBrand, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
    }

    Spacer(Modifier.height(18.dp))
    Text("No buscás sola.\nBuscamos juntos.", color = PetSitBrandDark, fontSize = 38.sp, lineHeight = 41.sp, fontWeight = FontWeight.Bold)
    Text(
        "PetSit conecta personas, refugios y veterinarias para acelerar reencuentros. Reportá, compartí y encontrá ayuda real cerca tuyo.",
        color = PetSitMuted, fontSize = 16.sp, lineHeight = 23.sp, modifier = Modifier.padding(top = 14.dp)
    )

    Row(Modifier.fillMaxWidth().padding(top = 18.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = {}, modifier = Modifier.weight(1f).height(50.dp), shape = RoundedCornerShape(25.dp), colors = ButtonDefaults.buttonColors(PetSitBrand)) {
            Text("Ver cómo funciona", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
        OutlinedButton(onClick = onCreateAccount, modifier = Modifier.weight(1f).height(50.dp), shape = RoundedCornerShape(25.dp)) {
            Text("Crear cuenta", color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }

    Row(Modifier.fillMaxWidth().padding(top = 14.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        listOf("✓ Reporte rápido", "✓ Ubicación", "✓ Comunidad").forEach {
            Surface(shape = RoundedCornerShape(50), color = Color.White, shadowElevation = 1.dp) {
                Text(it, color = PetSitBrandDark, fontWeight = FontWeight.SemiBold, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp))
            }
        }
    }

    Box(Modifier.fillMaxWidth().height(280.dp), contentAlignment = Alignment.Center) {
        Box(Modifier.size(205.dp).offset(x = 25.dp, y = (-15).dp).clip(CircleShape).background(Color(0xFFD6E7E5)))
        Box(Modifier.size(165.dp).offset(x = (-25).dp, y = 45.dp).clip(CircleShape).background(Color(0xFFF0E7D8)))
        Image(painterResource(R.drawable.home_dog), null, Modifier.fillMaxWidth().height(270.dp), contentScale = ContentScale.Fit)
    }

    Text("HECHO PARA ACOMPAÑAR", color = PetSitBrand, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
    Text("¿Cómo te ayuda PetSit?", color = PetSitBrandDark, fontSize = 29.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
    Text("Tres pasos simples para que la ayuda llegue más rápido.", color = PetSitMuted, modifier = Modifier.padding(bottom = 13.dp))

    HomeInfoCard(R.drawable.icon_reportar, "1) Reportá", "Cargá la información importante y una foto. Cuanto más claro el aviso, más fácil ayudar.")
    HomeInfoCard(R.drawable.icon_ubicar, "2) Ubicá", "Indicá la zona aproximada para que quienes están cerca puedan prestar atención.")
    HomeInfoCard(R.drawable.icon_conectar, "3) Conectá", "Refugios, veterinarias y personas cerca tuyo ayudan a difundir y actuar más rápido.")

    Spacer(Modifier.height(28.dp))
    Text("ACCEDÉ DIRECTO", color = PetSitBrand, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
    Text("Acciones rápidas", color = PetSitBrandDark, fontSize = 29.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
    Text("Elegí lo que necesitás hacer ahora.", color = PetSitMuted, modifier = Modifier.padding(bottom = 13.dp))

    QuickActionCard(R.drawable.icon_mapa_reportes, "Mapa de reportes", "Mirá mascotas perdidas y encontradas cerca tuyo.", "Abrir mapa")
    QuickActionCard(R.drawable.icon_refugios, "Refugios", "Encontrá refugios y conocé a quienes ayudan.", "Ver refugios")
    QuickActionCard(R.drawable.icon_veterinarias, "Veterinarias", "Veterinarias cercanas y guardias disponibles.", "Ver veterinarias")
}

@Composable
private fun HomeInfoCard(icon: Int, title: String, description: String) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 7.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(Modifier.padding(19.dp)) {
            Image(painterResource(icon), null, Modifier.size(52.dp), contentScale = ContentScale.Fit)
            Text(title, color = PetSitBrandDark, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 9.dp))
            Text(description, color = PetSitMuted, fontSize = 14.sp, lineHeight = 20.sp, modifier = Modifier.padding(top = 5.dp))
        }
    }
}

@Composable
private fun QuickActionCard(icon: Int, title: String, description: String, action: String) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 7.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(Modifier.padding(19.dp)) {
            Box(Modifier.size(60.dp).clip(RoundedCornerShape(18.dp)).background(PetSitBrandSoft), contentAlignment = Alignment.Center) {
                Image(painterResource(icon), null, Modifier.size(38.dp), contentScale = ContentScale.Fit)
            }
            Text(title, color = PetSitBrandDark, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 10.dp))
            Text(description, color = PetSitMuted, fontSize = 14.sp, lineHeight = 20.sp, modifier = Modifier.padding(top = 5.dp))
            Text(action + "  →", color = PetSitBrand, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 16.dp))
        }
    }
}

