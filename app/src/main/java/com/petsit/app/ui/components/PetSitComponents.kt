package com.petsit.app.ui.components
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import ar.edu.davinci.petsit.mobile.R
import com.petsit.app.model.*
import com.petsit.app.ui.theme.*

@Composable fun PetSitPage(content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 22.dp), content = content)
}
@Composable fun PetSitBrandHeader(compact: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painterResource(R.drawable.petsit_logo), "Logo PetSit", Modifier.size(if (compact) 46.dp else 64.dp))
        Text("PetSit", color = PetSitBrandDark, fontSize = if (compact) 27.sp else 34.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
    }
}
@Composable fun ScreenHero(title: String, subtitle: String) {
    Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(25.dp)).background(PetSitBrandSoft).padding(22.dp)) {
        Column { Text(title, color = PetSitBrandDark, fontSize = 29.sp, fontWeight = FontWeight.Bold); Text(subtitle, color = PetSitMuted, lineHeight = 21.sp, modifier = Modifier.padding(top = 7.dp)) }
    }
    Spacer(Modifier.height(20.dp))
}
@Composable fun SectionTitle(text: String) {
    Text(text, color = PetSitBrandDark, fontSize = 23.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp, bottom = 10.dp))
}
@Composable fun StaticField(label: String, multiline: Boolean = false) {
    OutlinedTextField(value = "", onValueChange = {}, label = { Text(label) }, modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(14.dp), singleLine = !multiline, minLines = if (multiline) 4 else 1)
}
@Composable fun PrimaryButton(text: String, onClick: () -> Unit = {}) {
    Button(onClick, Modifier.fillMaxWidth().height(54.dp).padding(top = 6.dp), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(PetSitBrand)) { Text(text, fontWeight = FontWeight.Bold) }
}
@Composable fun FeatureCard(title: String, subtitle: String, symbol: String) {
    Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(50.dp).clip(RoundedCornerShape(15.dp)).background(PetSitBrandSoft), contentAlignment = Alignment.Center) { Text(symbol, color = PetSitBrand, fontSize = 19.sp, fontWeight = FontWeight.Bold) }
            Column(Modifier.padding(start = 14.dp)) { Text(title, color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 17.sp); Text(subtitle, color = PetSitMuted, fontSize = 14.sp) }
        }
    }
}
@Composable fun PetCard(pet: PetUi) {
    Card(Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Image(painterResource(pet.imageRes), pet.name, Modifier.fillMaxWidth().height(190.dp), contentScale = ContentScale.Crop)
        Column(Modifier.padding(17.dp)) { StatusPill(pet.status); Text(pet.name, color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 22.sp, modifier = Modifier.padding(top = 8.dp)); Text(pet.detail, color = PetSitMuted) }
    }
}
@Composable fun PlaceCard(place: PlaceUi) {
    Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(Color.White)) {
        Column(Modifier.padding(18.dp)) { Text(place.name, color = PetSitBrandDark, fontSize = 18.sp, fontWeight = FontWeight.Bold); Text(place.detail, color = PetSitMuted); Text("Ver información  →", color = PetSitBrand, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp)) }
    }
}
@Composable fun StatusPill(text: String) {
    val warning = text.contains("PEND") || text.contains("BUSCA") || text.contains("REVIS")
    Box(Modifier.clip(RoundedCornerShape(50)).background(if (warning) PetSitWarning else PetSitBrandSoft).padding(horizontal = 10.dp, vertical = 5.dp)) { Text(text, color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 10.sp) }
}
@Composable fun FilterChips(labels: List<String>) {
    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        labels.forEachIndexed { index, label -> Box(Modifier.clip(RoundedCornerShape(50)).background(if (index == 0) PetSitBrand else Color.White).padding(horizontal = 15.dp, vertical = 9.dp)) { Text(label, color = if (index == 0) Color.White else PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 13.sp) } }
    }
    Spacer(Modifier.height(10.dp))
}
@Composable fun InfoRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) { Text(label, color = PetSitInk, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f)); Text(value, color = PetSitMuted, textAlign = TextAlign.End) }
    HorizontalDivider(color = Color(0xFFE5ECEA))
}
@Composable fun MetricCard(metric: AdminMetricUi, modifier: Modifier = Modifier) {
    Card(modifier.padding(vertical = 5.dp), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(Color.White)) { Column(Modifier.padding(17.dp)) { Text(metric.value, color = PetSitBrand, fontSize = 25.sp, fontWeight = FontWeight.Bold); Text(metric.label, color = PetSitMuted, fontSize = 13.sp) } }
}
@Composable fun FormLayout(title: String, fields: List<String>, action: String) = PetSitPage {
    ScreenHero(title, "Completá la información solicitada.")
    Card(shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(Color.White)) { Column(Modifier.padding(20.dp)) { fields.forEach { StaticField(it, multiline = it == "Mensaje" || it == "Descripción") }; PrimaryButton(action) } }
}

