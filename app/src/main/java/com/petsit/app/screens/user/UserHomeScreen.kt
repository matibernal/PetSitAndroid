package com.petsit.app.screens.user
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.petsit.app.ui.components.*
import com.petsit.app.ui.theme.*
@Composable fun UserHomeScreen() = PetSitPage {
    Text("Hola, Kei", color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 30.sp)
    Text("¿Qué necesitás hacer hoy?", color = PetSitMuted); Spacer(Modifier.height(22.dp))
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(PetSitBrand), shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)) {
        Column(Modifier.padding(22.dp)) { Text("Tu comunidad PetSit", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold); Text("Encontrá ayuda, compartí reportes y cuidá a quienes más querés.", color = Color.White.copy(alpha=.85f)) }
    }
    SectionTitle("Accesos rápidos")
    listOf("Mis mascotas" to "Perfiles y datos", "Reportar mascota" to "Creá una alerta", "Mapa" to "Reportes cercanos", "Adoptar" to "Buscá un nuevo compañero", "Veterinarias" to "Atención cercana", "Refugios" to "Conocé organizaciones").forEach { FeatureCard(it.first, it.second, "⌁") }
}

