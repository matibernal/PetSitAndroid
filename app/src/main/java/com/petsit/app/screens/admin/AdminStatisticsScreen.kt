package com.petsit.app.screens.admin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.petsit.app.model.AdminMetricUi
import com.petsit.app.ui.components.*
import com.petsit.app.ui.theme.*
@Composable fun AdminStatisticsScreen() = PetSitPage {
    ScreenHero("Estadísticas", "Indicadores principales de la plataforma.")
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        MetricCard(AdminMetricUi("+18%", "Usuarios"), Modifier.weight(1f))
        MetricCard(AdminMetricUi("+27%", "Reencuentros"), Modifier.weight(1f))
    }
    SectionTitle("Actividad mensual")
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(Color.White)) {
        Row(Modifier.fillMaxWidth().height(210.dp).padding(22.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom) {
            listOf(55, 88, 70, 120, 95, 145).forEachIndexed { index, height ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(Modifier.width(24.dp).height(height.dp).clip(RoundedCornerShape(topStart = 7.dp, topEnd = 7.dp)).background(if (index == 5) PetSitBrand else PetSitBrand.copy(alpha=.35f)))
                    Text(listOf("Abr","May","Jun","Jul","Ago","Sep")[index], fontSize = 10.sp, color = PetSitMuted)
                }
            }
        }
    }
}

