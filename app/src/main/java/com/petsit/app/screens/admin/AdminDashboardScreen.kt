package com.petsit.app.screens.admin
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.petsit.app.data.SampleData
import com.petsit.app.ui.components.*
import com.petsit.app.ui.theme.*
@Composable fun AdminDashboardScreen() = PetSitPage {
    Text("Panel administrativo", color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 30.sp)
    Text("Resumen general de PetSit", color = PetSitMuted); Spacer(Modifier.height(20.dp))
    SampleData.adminMetrics.chunked(2).forEach { row ->
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            row.forEach { MetricCard(it, Modifier.weight(1f)) }
        }
    }
    SectionTitle("Actividad reciente")
    listOf("Nuevo usuario", "Reporte publicado", "Refugio verificado", "Solicitud recibida", "Veterinaria actualizada").forEachIndexed { index, label -> InfoRow(label, "Hace " + (index + 1) + " h") }
}

