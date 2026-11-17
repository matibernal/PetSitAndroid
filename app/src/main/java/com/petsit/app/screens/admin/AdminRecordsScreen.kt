package com.petsit.app.screens.admin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.petsit.app.ui.components.*
import com.petsit.app.ui.theme.*
@Composable fun AdminRecordsScreen(title: String) = PetSitPage {
    ScreenHero(title, "Gestión y seguimiento desde el panel de PetSit.")
    StaticField("Buscar en " + title); FilterChips(listOf("Todos", "Pendientes", "Activos"))
    repeat(7) { index ->
        Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(17.dp), colors = CardDefaults.cardColors(Color.White)) {
            Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(42.dp).clip(CircleShape).background(PetSitBrandSoft), contentAlignment = Alignment.Center) { Text((index + 1).toString(), color = PetSitBrand, fontWeight = FontWeight.Bold) }
                Column(Modifier.weight(1f).padding(start = 12.dp)) { Text("Registro " + (index + 1), color = PetSitBrandDark, fontWeight = FontWeight.Bold); Text("Información de ejemplo", color = PetSitMuted, fontSize = 13.sp) }
                StatusPill(if (index % 3 == 0) "PENDIENTE" else "ACTIVO")
            }
        }
    }
}

