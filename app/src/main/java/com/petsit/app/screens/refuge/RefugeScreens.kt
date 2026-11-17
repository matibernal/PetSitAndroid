package com.petsit.app.screens.refuge

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import ar.edu.davinci.petsit.mobile.R
import com.petsit.app.ui.components.*
import com.petsit.app.ui.theme.*

private data class RefugePet(
    val name: String, val type: String, val sex: String, val breed: String,
    val zone: String, val age: String, val weight: String, val description: String,
    val image: Int, val status: String = "EN ADOPCIÓN"
)

private data class RefugeRequest(
    val user: String, val pet: String, val status: String, val message: String,
    val email: String, val phone: String
)

private data class RefugeAdoption(
    val pet: String, val adopter: String, val date: String, val status: String,
    val description: String, val image: Int, val phone: String, val email: String
)

private val initialPets = listOf(
    RefugePet("Luna", "Gato", "Hembra", "Mestiza", "Villa Devoto", "10 meses", "3,6 kg", "Tranquila y mimosera. Usa arenero.", R.drawable.adopt_cat_luna),
    RefugePet("Mishi", "Gato", "Macho", "Europeo", "Belgrano", "2 años", "4,4 kg", "Castrado. Sociable con visitas.", R.drawable.adopt_cat_mishi),
    RefugePet("Toby", "Perro", "Macho", "Mestizo", "Palermo", "3 años", "18 kg", "Ideal para familia. Aprende rápido y disfruta los paseos.", R.drawable.adopt_dog_toby)
)

@Composable
fun RefugeHomeScreen(onPets: () -> Unit, onRequests: () -> Unit, onAdoptions: () -> Unit) = PetSitPage {
    Surface(shape = RoundedCornerShape(50), color = PetSitBrandSoft) {
        Text("⌂  PANEL REFUGIO", color = PetSitBrand, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(horizontal = 13.dp, vertical = 7.dp))
    }
    Text("¡Hola, Refugio Patitas!", color = PetSitBrandDark, fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp))
    Text("Gestioná tu refugio de forma simple", color = PetSitBrandDark, fontSize = 23.sp, fontWeight = FontWeight.SemiBold)
    Text("Administrá mascotas, solicitudes y adopciones desde un único lugar.", color = PetSitMuted, lineHeight = 21.sp, modifier = Modifier.padding(top = 10.dp))

    Card(Modifier.fillMaxWidth().padding(top = 18.dp), shape = RoundedCornerShape(25.dp), colors = CardDefaults.cardColors(PetSitBrand)) {
        Row(Modifier.padding(19.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(R.drawable.shelter_hero), "Refugio", Modifier.size(116.dp).clip(RoundedCornerShape(20.dp)), contentScale = ContentScale.Crop)
            Column(Modifier.padding(start = 15.dp)) {
                Text("Tu red de cuidado", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text("12 mascotas · 5 solicitudes pendientes", color = Color.White.copy(alpha = .86f), fontSize = 13.sp)
            }
        }
    }
    SectionTitle("Acciones rápidas")
    RefugeAction("Registrar mascota", "Agregá nuevos animales al sistema", "＋", onPets)
    RefugeAction("Solicitudes", "Revisá solicitudes de adopción pendientes", "▣", onRequests)
    RefugeAction("Adopciones", "Administrá adopciones y seguimientos", "♡", onAdoptions)
}

@Composable
private fun RefugeAction(title: String, subtitle: String, symbol: String, onClick: () -> Unit) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 6.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(49.dp).clip(RoundedCornerShape(15.dp)).background(PetSitBrandSoft), contentAlignment = Alignment.Center) {
                Text(symbol, color = PetSitBrand, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            Column(Modifier.padding(start = 14.dp).weight(1f)) {
                Text(title, color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text(subtitle, color = PetSitMuted, fontSize = 13.sp)
            }
            Text("›", color = PetSitBrand, fontSize = 25.sp)
        }
    }
}

@Composable
fun RefugePetsScreen() {
    val pets = remember { mutableStateListOf<RefugePet>().apply { addAll(initialPets) } }
    var selected by remember { mutableStateOf<RefugePet?>(null) }
    var editing by remember { mutableStateOf<RefugePet?>(null) }
    var creating by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    val filtered = pets.filter { it.name.contains(query, true) || it.type.contains(query, true) || it.zone.contains(query, true) }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)) {
        ScreenHero("Mis mascotas", "Administrá las mascotas publicadas por tu refugio.")
        Button(onClick = { creating = true }, colors = ButtonDefaults.buttonColors(PetSitBrand), shape = RoundedCornerShape(22.dp), modifier = Modifier.align(Alignment.End)) {
            Text("＋ Registrar mascota", fontWeight = FontWeight.Bold)
        }
        OutlinedTextField(
            value = query, onValueChange = { query = it }, placeholder = { Text("Buscar por nombre, tipo o zona...") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), shape = RoundedCornerShape(24.dp), singleLine = true
        )
        Text("${filtered.size} mascotas publicadas", color = PetSitBrand, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        filtered.forEach { pet ->
            Card(Modifier.fillMaxWidth().padding(top = 12.dp), shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                Image(painterResource(pet.image), pet.name, Modifier.fillMaxWidth().height(190.dp), contentScale = ContentScale.Crop)
                Column(Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(pet.name, color = PetSitBrandDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        StatusPill(pet.status)
                    }
                    Text("${pet.type} · ${pet.breed} · ${pet.age}", color = PetSitMuted)
                    Text("⌖ ${pet.zone}    ⚖ ${pet.weight}", color = PetSitMuted, fontSize = 13.sp, modifier = Modifier.padding(top = 5.dp))
                    Row(Modifier.fillMaxWidth().padding(top = 11.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = { selected = pet }, modifier = Modifier.weight(1f)) { Text("Ver") }
                        Button(onClick = { editing = pet }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(PetSitBrand)) { Text("Editar") }
                    }
                }
            }
        }
    }
    selected?.let { pet -> RefugePetDetail(pet, { selected = null }, { selected = null; editing = pet }) }
    if (creating) RefugePetForm(null, onDismiss = { creating = false }) { newPet -> pets.add(0, newPet); creating = false }
    editing?.let { old -> RefugePetForm(old, onDismiss = { editing = null }) { updated ->
        val index = pets.indexOf(old); if (index >= 0) pets[index] = updated; editing = null
    } }
}

@Composable
private fun RefugePetDetail(pet: RefugePet, onDismiss: () -> Unit, onEdit: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(Color.White)) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Image(painterResource(pet.image), pet.name, Modifier.fillMaxWidth().height(230.dp), contentScale = ContentScale.Crop)
                Column(Modifier.padding(18.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(pet.name, fontSize = 27.sp, color = PetSitBrandDark, fontWeight = FontWeight.Bold); StatusPill(pet.status) }
                    InfoRow("Tipo", "${pet.type} · ${pet.sex}")
                    InfoRow("Raza", pet.breed)
                    InfoRow("Edad", pet.age)
                    InfoRow("Peso", pet.weight)
                    InfoRow("Zona", pet.zone)
                    Text(pet.description, color = PetSitMuted, modifier = Modifier.padding(vertical = 10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) { Text("Cerrar") }
                        Button(onClick = onEdit, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(PetSitBrand)) { Text("Editar mascota") }
                    }
                }
            }
        }
    }
}

@Composable
private fun RefugePetForm(existing: RefugePet?, onDismiss: () -> Unit, onSave: (RefugePet) -> Unit) {
    var name by remember { mutableStateOf(existing?.name ?: "") }
    var type by remember { mutableStateOf(existing?.type ?: "Perro") }
    var breed by remember { mutableStateOf(existing?.breed ?: "") }
    var age by remember { mutableStateOf(existing?.age ?: "") }
    var zone by remember { mutableStateOf(existing?.zone ?: "") }
    var description by remember { mutableStateOf(existing?.description ?: "") }
    Dialog(onDismissRequest = onDismiss) {
        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(Color.White)) {
            Column(Modifier.padding(18.dp).verticalScroll(rememberScrollState())) {
                Text(if (existing == null) "Registrar mascota" else "Editar ${existing.name}", color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 23.sp)
                RefugeInput("Nombre", name) { name = it }
                RefugeInput("Tipo", type) { type = it }
                RefugeInput("Raza", breed) { breed = it }
                RefugeInput("Edad", age) { age = it }
                RefugeInput("Zona / barrio", zone) { zone = it }
                RefugeInput("Características / descripción", description, true) { description = it }
                Text("Ficha rápida", color = PetSitBrandDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp))
                listOf("Nivel de energía", "Sociable con personas", "Con otros gatos", "Apto departamento", "Entrenamiento básico").forEach { label ->
                    Text("$label   3/5", color = PetSitMuted, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp)); Slider(value = .6f, onValueChange = {})
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                    OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) { Text("Cancelar") }
                    Button(
                        onClick = { if (name.isNotBlank()) onSave(RefugePet(name, type, existing?.sex ?: "Hembra", breed, zone, age, existing?.weight ?: "—", description, existing?.image ?: R.drawable.adopt_dog_duna)) },
                        modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(PetSitBrand)
                    ) { Text("Guardar") }
                }
            }
        }
    }
}

@Composable private fun RefugeInput(label: String, value: String, multiline: Boolean = false, onChange: (String) -> Unit) {
    OutlinedTextField(value, onChange, label = { Text(label) }, modifier = Modifier.fillMaxWidth().padding(top = 7.dp), shape = RoundedCornerShape(13.dp), minLines = if (multiline) 3 else 1, singleLine = !multiline)
}

@Composable
fun RefugeRequestsScreen() {
    val requests = remember { mutableStateListOf(
        RefugeRequest("Camila Torres", "Luna", "Pendiente", "Tengo experiencia cuidando gatos y vivo en un departamento amplio con red de protección.", "camila@email.com", "+54 11 5555-5555"),
        RefugeRequest("Lucas Fernández", "Toby", "Aprobada", "Trabajo desde casa y tengo patio grande para que pueda jugar.", "lucas@email.com", "+54 11 4444-4444"),
        RefugeRequest("Marina López", "Mora", "Rechazada", "Busco una mascota tranquila para compañía.", "marina@email.com", "+54 11 3333-3333")
    ) }
    var selected by remember { mutableStateOf<RefugeRequest?>(null) }
    var filter by remember { mutableStateOf("Todas") }
    val visible = requests.filter { filter == "Todas" || it.status == filter.dropLastWhile { c -> c == 's' } }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)) {
        ScreenHero("Solicitudes de adopción", "Revisá las solicitudes enviadas por personas interesadas.")
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Todas", "Pendientes", "Aprobadas", "Rechazadas").forEach { item ->
                FilterChip(selected = filter == item, onClick = { filter = item }, label = { Text(item) })
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(7.dp)) {
            MiniMetric("${requests.size}", "Total", Modifier.weight(1f)); MiniMetric("${requests.count { it.status == "Pendiente" }}", "Pend.", Modifier.weight(1f)); MiniMetric("${requests.count { it.status == "Aprobada" }}", "Aprob.", Modifier.weight(1f))
        }
        visible.forEachIndexed { index, request ->
            Card(Modifier.fillMaxWidth().padding(top = 11.dp), shape = RoundedCornerShape(21.dp), colors = CardDefaults.cardColors(Color.White)) {
                Column(Modifier.padding(17.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column { Text(request.user, color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 18.sp); Text("Interesado/a en ${request.pet}", color = PetSitMuted) }
                        StatusPill(request.status.uppercase())
                    }
                    Text("“${request.message}”", color = PetSitMuted, modifier = Modifier.padding(vertical = 12.dp))
                    OutlinedButton(onClick = { selected = request }) { Text("Ver detalle") }
                    if (request.status == "Pendiente") {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { requests[index] = request.copy(status = "Aprobada") }, colors = ButtonDefaults.buttonColors(Color(0xFF248C68))) { Text("Aprobar") }
                            OutlinedButton(onClick = { requests[index] = request.copy(status = "Rechazada") }, colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFC7333E))) { Text("Rechazar") }
                        }
                    }
                }
            }
        }
    }
    selected?.let { request ->
        Dialog(onDismissRequest = { selected = null }) {
            Card(shape = RoundedCornerShape(23.dp), colors = CardDefaults.cardColors(Color.White)) {
                Column(Modifier.padding(20.dp)) {
                    Text("Detalle de solicitud", color = PetSitBrandDark, fontSize = 23.sp, fontWeight = FontWeight.Bold)
                    InfoRow("Persona", request.user); InfoRow("Mascota", request.pet); InfoRow("Estado", request.status); InfoRow("Email", request.email); InfoRow("Teléfono", request.phone)
                    Text(request.message, color = PetSitMuted, modifier = Modifier.padding(vertical = 12.dp))
                    PrimaryButton("Cerrar") { selected = null }
                }
            }
        }
    }
}

@Composable private fun MiniMetric(value: String, label: String, modifier: Modifier) {
    Surface(modifier.padding(top = 10.dp), shape = RoundedCornerShape(15.dp), color = Color.White) { Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) { Text(value, color = PetSitBrand, fontWeight = FontWeight.Bold, fontSize = 20.sp); Text(label, color = PetSitMuted, fontSize = 11.sp) } }
}

@Composable
fun RefugeAdoptionsScreen() {
    val adoptions = remember { listOf(
        RefugeAdoption("Milo", "Lucas Fernández", "12/05/2025", "Finalizada", "Milo encontró una familia con patio grande y compañía diaria. La adaptación fue excelente.", R.drawable.adopt_dog_max, "+54 11 4444-4444", "lucas@email.com"),
        RefugeAdoption("Luna", "Camila Torres", "20/05/2025", "En seguimiento", "Luna está en período de adaptación. La familia envió buenas actualizaciones iniciales.", R.drawable.adopt_cat_luna, "+54 11 5555-5555", "camila@email.com"),
        RefugeAdoption("Toby", "Sofía Ramírez", "02/04/2025", "Finalizada", "Toby se adaptó muy bien a su nuevo hogar y mantiene seguimiento positivo.", R.drawable.adopt_dog_toby, "+54 11 2222-2222", "sofia@email.com")
    ) }
    var selected by remember { mutableStateOf<RefugeAdoption?>(null) }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)) {
        ScreenHero("Historial de adopciones", "Consultá adopciones finalizadas, estados de seguimiento y familias adoptantes.")
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(7.dp)) {
            MiniMetric("18", "Total", Modifier.weight(1f)); MiniMetric("4", "Este mes", Modifier.weight(1f)); MiniMetric("6", "Seguim.", Modifier.weight(1f))
        }
        adoptions.forEach { adoption ->
            Card(Modifier.fillMaxWidth().padding(top = 12.dp).clickable { selected = adoption }, shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(Color.White)) {
                Image(painterResource(adoption.image), adoption.pet, Modifier.fillMaxWidth().height(180.dp), contentScale = ContentScale.Crop)
                Column(Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(adoption.pet, color = PetSitBrandDark, fontSize = 22.sp, fontWeight = FontWeight.Bold); StatusPill(adoption.status.uppercase()) }
                    Text("Adoptado/a por ${adoption.adopter}", color = PetSitMuted)
                    Text(adoption.date, color = PetSitBrand, fontSize = 12.sp, modifier = Modifier.padding(top = 5.dp))
                    Text(adoption.description, color = PetSitMuted, maxLines = 2, modifier = Modifier.padding(top = 8.dp))
                    Text("Ver detalle  ›", color = PetSitBrand, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp))
                }
            }
        }
    }
    selected?.let { adoption ->
        Dialog(onDismissRequest = { selected = null }) {
            Card(shape = RoundedCornerShape(23.dp), colors = CardDefaults.cardColors(Color.White)) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Image(painterResource(adoption.image), adoption.pet, Modifier.fillMaxWidth().height(220.dp), contentScale = ContentScale.Crop)
                    Column(Modifier.padding(18.dp)) {
                        Text(adoption.pet, color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 26.sp)
                        InfoRow("Adoptante", adoption.adopter); InfoRow("Fecha", adoption.date); InfoRow("Estado", adoption.status); InfoRow("Contacto", adoption.phone); InfoRow("Email", adoption.email)
                        Text(adoption.description, color = PetSitMuted, modifier = Modifier.padding(vertical = 10.dp))
                        PrimaryButton("Cerrar") { selected = null }
                    }
                }
            }
        }
    }
}

@Composable
fun RefugeProfileScreen(onLogout: () -> Unit) {
    var name by remember { mutableStateOf("Refugio Patitas") }
    var email by remember { mutableStateOf("contacto@patitas.com") }
    var phone by remember { mutableStateOf("+54 11 1234 5678") }
    var location by remember { mutableStateOf("Buenos Aires, Argentina") }
    var description by remember { mutableStateOf("Refugio dedicado al rescate, tránsito y adopción responsable de perros y gatos.") }
    var saved by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)) {
        ScreenHero("Perfil del refugio", "Actualizá los datos de contacto, ubicación y presentación pública.")
        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(23.dp), colors = CardDefaults.cardColors(Color.White)) {
            Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painterResource(R.drawable.profile_avatar_1), "Perfil", Modifier.size(105.dp).clip(CircleShape), contentScale = ContentScale.Crop)
                Text(name, color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 23.sp, modifier = Modifier.padding(top = 10.dp))
                Text("Refugio verificado", color = PetSitMuted)
                StatusPill("12 MASCOTAS")
            }
        }
        SectionTitle("Información general")
        RefugeInput("Nombre del refugio", name) { name = it }
        RefugeInput("Email", email) { email = it }
        RefugeInput("Teléfono", phone) { phone = it }
        RefugeInput("Ubicación", location) { location = it }
        RefugeInput("Descripción", description, true) { description = it }
        PrimaryButton(if (saved) "✓ Cambios guardados" else "Guardar cambios") { saved = true }
        OutlinedButton(onClick = onLogout, modifier = Modifier.fillMaxWidth().padding(top = 10.dp), colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFC7333E))) { Text("Cerrar sesión", fontWeight = FontWeight.Bold) }
        Spacer(Modifier.height(20.dp))
    }
}
