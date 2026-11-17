package com.petsit.app.screens.user

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.petsit.app.data.SampleData
import com.petsit.app.model.AdoptionPetUi
import com.petsit.app.ui.components.PetSitPage
import com.petsit.app.ui.theme.*

@Composable
fun AdoptionScreen() {
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("Todos") }
    var selected by remember { mutableStateOf<AdoptionPetUi?>(null) }
    var requestFor by remember { mutableStateOf<AdoptionPetUi?>(null) }
    var confirmation by remember { mutableStateOf<String?>(null) }
    val visible = SampleData.adoptionPetsDetailed.filter { pet ->
        val matchesType = filter == "Todos" || (filter == "Perros" && pet.type == "Perro") || (filter == "Gatos" && pet.type == "Gato") || (filter == "Cerca mío" && pet.neighborhood in listOf("Caballito", "Belgrano"))
        val text = query.trim().lowercase()
        matchesType && (text.isBlank() || listOf(pet.name, pet.neighborhood, pet.description, pet.shelter).any { it.lowercase().contains(text) })
    }

    PetSitPage {
        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(PetSitBrandSoft), shape = RoundedCornerShape(25.dp)) {
            Column(Modifier.padding(22.dp)) {
                Text("Adoptar", color = PetSitBrandDark, fontSize = 31.sp, fontWeight = FontWeight.Bold)
                Box(Modifier.width(145.dp).height(5.dp).clip(RoundedCornerShape(50)).background(Color(0xFFAFD3D0)))
                Text("Encontrá tu nuevo compañero. Filtrá por tipo, zona o nombre y enviá una solicitud al refugio.", color = PetSitMuted, lineHeight = 21.sp, modifier = Modifier.padding(top = 10.dp))
            }
        }
        OutlinedTextField(value = query, onValueChange = { query = it }, placeholder = { Text("⌕  Buscar por nombre, zona o señas") }, modifier = Modifier.fillMaxWidth().padding(top = 15.dp), singleLine = true, shape = RoundedCornerShape(26.dp), colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White))
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Todos", "Perros", "Gatos", "Cerca mío").forEach { label -> AdoptionFilter(label, selected = filter == label) { filter = label } }
        }
        confirmation?.let { message ->
            Card(Modifier.fillMaxWidth().padding(top = 12.dp), colors = CardDefaults.cardColors(Color(0xFFE4F5E5))) {
                Text("✓ $message", color = Color(0xFF27632A), fontWeight = FontWeight.Bold, modifier = Modifier.padding(13.dp))
            }
        }
        Text("${visible.size} mascota(s) para adoptar", color = PetSitBrandDark, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 17.dp))
        if (visible.isEmpty()) Text("No encontramos resultados con esos filtros.", color = PetSitMuted, modifier = Modifier.padding(vertical = 30.dp))
        visible.forEach { pet -> AdoptionCard(pet) { selected = pet } }
    }

    selected?.let { pet -> AdoptionDetailDialog(pet, { selected = null }) { selected = null; requestFor = pet } }
    requestFor?.let { pet -> AdoptionRequestDialog(pet, { requestFor = null }) {
        requestFor = null; confirmation = "Solicitud para ${pet.name} enviada correctamente"
    } }
}

@Composable
private fun AdoptionFilter(label: String, selected: Boolean, onClick: () -> Unit) {
    Surface(modifier = Modifier.clickable(onClick = onClick), shape = RoundedCornerShape(50), color = if (selected) PetSitBrand else Color.White) {
        Text(label, color = if (selected) Color.White else PetSitBrandDark, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp))
    }
}

@Composable
private fun AdoptionCard(pet: AdoptionPetUi, onOpen: () -> Unit) {
    Card(Modifier.fillMaxWidth().padding(top = 16.dp), colors = CardDefaults.cardColors(Color.White), shape = RoundedCornerShape(22.dp), elevation = CardDefaults.cardElevation(3.dp)) {
        Image(painterResource(pet.imageRes), pet.name, Modifier.fillMaxWidth().height(220.dp), contentScale = ContentScale.Crop)
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(pet.name, color = PetSitBrandDark, fontSize = 23.sp, fontWeight = FontWeight.Bold); AdoptionTag(pet.type)
            }
            Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("⌖ ${pet.neighborhood}", color = PetSitMuted, fontSize = 13.sp); Text("▣ ${pet.date}", color = PetSitMuted, fontSize = 12.sp)
            }
            Text("⌂ ${pet.shelter}", color = PetSitMuted, fontSize = 13.sp, modifier = Modifier.padding(top = 7.dp))
            OutlinedButton(onClick = onOpen, modifier = Modifier.fillMaxWidth().padding(top = 12.dp), shape = RoundedCornerShape(22.dp), border = BorderStroke(1.dp, Color(0xFFB8D8D5))) {
                Text("Ver datos", color = PetSitBrandDark, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable private fun AdoptionTag(text: String) = Surface(shape = RoundedCornerShape(50), color = PetSitBrandSoft, border = BorderStroke(1.dp, Color(0xFFB8D8D5))) {
    Text(text, color = PetSitBrandDark, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 11.dp, vertical = 6.dp))
}

@Composable
private fun AdoptionDialogShell(title: String, close: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Dialog(onDismissRequest = close) {
        Card(Modifier.fillMaxWidth().heightIn(max = 720.dp), colors = CardDefaults.cardColors(Color.White), shape = RoundedCornerShape(24.dp)) {
            Column(Modifier.verticalScroll(rememberScrollState()).padding(18.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(title, color = PetSitBrandDark, fontSize = 20.sp, fontWeight = FontWeight.Bold); Text("✕", fontSize = 24.sp, modifier = Modifier.clickable(onClick = close))
                }
                HorizontalDivider(Modifier.padding(vertical = 12.dp), color = Color(0xFFB8D8D5)); content()
            }
        }
    }
}

@Composable
private fun AdoptionDetailDialog(pet: AdoptionPetUi, close: () -> Unit, request: () -> Unit) = AdoptionDialogShell("♡  Mascota en adopción", close) {
    Image(painterResource(pet.imageRes), pet.name, Modifier.fillMaxWidth().height(270.dp).clip(RoundedCornerShape(15.dp)), contentScale = ContentScale.Crop)
    Row(Modifier.fillMaxWidth().padding(top = 13.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(pet.name, color = PetSitBrandDark, fontSize = 28.sp, fontWeight = FontWeight.Bold); AdoptionTag("Adopción")
    }
    Text("${pet.type} · ${pet.neighborhood}", color = PetSitMuted, modifier = Modifier.padding(top = 4.dp))
    Text("⌂ ${pet.shelter}\n▣ ${pet.age}\n⚖ ${pet.weight}\n▤ ${pet.description}", color = PetSitBrandDark, lineHeight = 25.sp, modifier = Modifier.padding(top = 10.dp))
    Text("Ficha rápida", color = PetSitBrandDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 13.dp, bottom = 5.dp))
    TraitBar("Nivel de energía", pet.energy); TraitBar("Sociable con personas", pet.people)
    TraitBar("Con otros ${if (pet.type == "Gato") "gatos" else "perros"}", pet.otherPets); TraitBar("Apto departamento", pet.apartment); TraitBar(if (pet.type == "Gato") "Uso de arenero" else "Entrenamiento", pet.habits)
    Button(onClick = request, modifier = Modifier.fillMaxWidth().padding(top = 14.dp), colors = ButtonDefaults.buttonColors(PetSitBrand), shape = RoundedCornerShape(22.dp)) { Text("Enviar solicitud", fontWeight = FontWeight.Bold) }
}

@Composable
private fun TraitBar(label: String, value: Int) {
    Row(Modifier.fillMaxWidth().padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = PetSitBrandDark, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.35f))
        LinearProgressIndicator(progress = value / 5f, modifier = Modifier.weight(1f).height(7.dp).clip(RoundedCornerShape(50)), color = Color(0xFF9CC8C5), trackColor = Color(0xFFE7ECEB))
    }
}

@Composable
private fun AdoptionRequestDialog(pet: AdoptionPetUi, close: () -> Unit, submit: () -> Unit) = AdoptionDialogShell("➤  Enviar solicitud", close) {
    var name by remember { mutableStateOf("") }; var email by remember { mutableStateOf("") }; var phone by remember { mutableStateOf("") }; var zone by remember { mutableStateOf("") }; var housing by remember { mutableStateOf("") }; var otherPets by remember { mutableStateOf("") }; var message by remember { mutableStateOf("") }
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(PetSitBrandSoft)) { Column(Modifier.padding(14.dp)) { Text("● SOLICITUD", color = PetSitBrand, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold); Text("Mascota: ${pet.name}", color = PetSitBrandDark, fontSize = 18.sp); Text("Refugio: ${pet.shelter}", color = PetSitMuted) } }
    RequestField("Nombre y apellido", name, { name = it }, "Ej: Keila Degregorio"); RequestField("Email", email, { email = it }, "tu@email.com")
    RequestField("Teléfono", phone, { phone = it }, "Ej: 11 1234 5678"); RequestField("Zona / Barrio", zone, { zone = it }, "Ej: Villa Devoto")
    RequestField("Tipo de vivienda", housing, { housing = it }, "Casa o departamento"); RequestField("¿Tenés otras mascotas?", otherPets, { otherPets = it }, "Sí / No")
    RequestField("Mensaje para el refugio", message, { message = it }, "Contales por qué querés adoptar y cómo es tu rutina.", 4)
    Row(Modifier.fillMaxWidth().padding(top = 13.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(onClick = close, modifier = Modifier.weight(1f)) { Text("Cancelar") }
        Button(onClick = submit, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(PetSitBrand)) { Text("Enviar", fontWeight = FontWeight.Bold) }
    }
}

@Composable
private fun RequestField(label: String, value: String, change: (String) -> Unit, hint: String, lines: Int = 1) {
    Text(label, color = PetSitBrandDark, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
    OutlinedTextField(value = value, onValueChange = change, modifier = Modifier.fillMaxWidth(), placeholder = { Text(hint) }, singleLine = lines == 1, minLines = lines, shape = RoundedCornerShape(13.dp))
}
