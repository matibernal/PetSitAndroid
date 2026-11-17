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
import com.petsit.app.model.PetUi
import com.petsit.app.ui.components.PetSitPage
import com.petsit.app.ui.components.StatusPill
import com.petsit.app.ui.theme.*

private enum class PetDialog { VIEW, EDIT, QR, REPORT, ADD }

@Composable
fun MyPetsScreen() {
    var selectedPet by remember { mutableStateOf<PetUi?>(null) }
    var dialog by remember { mutableStateOf<PetDialog?>(null) }
    fun open(pet: PetUi?, destination: PetDialog) { selectedPet = pet; dialog = destination }

    PetSitPage {
        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(25.dp), colors = CardDefaults.cardColors(PetSitBrandSoft)) {
            Column(Modifier.padding(22.dp)) {
                Text("Mis mascotas", color = PetSitBrandDark, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Box(Modifier.width(170.dp).height(5.dp).clip(RoundedCornerShape(50)).background(Color(0xFFAFD3D0)))
                Text("Gestioná sus datos y generá un QR para que cualquiera pueda contactarte si la encuentra.", color = PetSitMuted, lineHeight = 21.sp, modifier = Modifier.padding(top = 10.dp))
                Button(onClick = { open(null, PetDialog.ADD) }, colors = ButtonDefaults.buttonColors(PetSitBrand), shape = RoundedCornerShape(24.dp), modifier = Modifier.padding(top = 14.dp)) {
                    Text("⊕  Registrar mascota", fontWeight = FontWeight.Bold)
                }
            }
        }
        SampleData.myPets.forEach { pet ->
            FullPetCard(pet, { open(pet, PetDialog.VIEW) }, { open(pet, PetDialog.EDIT) }, { open(pet, PetDialog.QR) }, { open(pet, PetDialog.REPORT) })
        }
    }

    when (dialog) {
        PetDialog.ADD -> PetFormDialog("Registrar mascota", null) { dialog = null }
        PetDialog.VIEW -> selectedPet?.let { PetViewDialog(it, { dialog = null }, { dialog = PetDialog.EDIT }, { dialog = PetDialog.QR }, { dialog = PetDialog.REPORT }) }
        PetDialog.EDIT -> selectedPet?.let { PetFormDialog("Editar ${it.name}", it) { dialog = null } }
        PetDialog.QR -> selectedPet?.let { PetQrDialog(it) { dialog = null } }
        PetDialog.REPORT -> selectedPet?.let { PetReportDialog(it) { dialog = null } }
        null -> Unit
    }
}

@Composable
private fun FullPetCard(pet: PetUi, onView: () -> Unit, onEdit: () -> Unit, onQr: () -> Unit, onReport: () -> Unit) {
    Card(Modifier.fillMaxWidth().padding(top = 18.dp), shape = RoundedCornerShape(23.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(3.dp)) {
        Box {
            Image(painterResource(pet.imageRes), pet.name, Modifier.fillMaxWidth().height(210.dp), contentScale = ContentScale.Crop)
            Box(Modifier.align(Alignment.BottomStart).padding(13.dp)) { StatusPill(pet.status) }
        }
        Column(Modifier.padding(16.dp)) {
            Text(pet.name, color = PetSitBrandDark, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(pet.detail, color = PetSitMuted, modifier = Modifier.padding(top = 3.dp, bottom = 12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PetAction("◉  Ver", Modifier.weight(1f), onView); PetAction("✎  Editar", Modifier.weight(1f), onEdit)
            }
            Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PetAction("▦  QR", Modifier.weight(1f), onQr)
                OutlinedButton(onClick = onReport, modifier = Modifier.weight(1f), shape = RoundedCornerShape(22.dp), colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFFFECEE)), border = BorderStroke(1.dp, Color(0xFFF3BAC0))) {
                    Text("⚑  Reportar", color = Color(0xFFC52B3B), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun PetAction(label: String, modifier: Modifier, action: () -> Unit) {
    OutlinedButton(onClick = action, modifier = modifier, shape = RoundedCornerShape(22.dp), border = BorderStroke(1.dp, Color(0xFFB8D8D5))) {
        Text(label, color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

@Composable
private fun DialogShell(title: String, onClose: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Dialog(onDismissRequest = onClose) {
        Card(Modifier.fillMaxWidth().heightIn(max = 720.dp), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(Color.White)) {
            Column(Modifier.verticalScroll(rememberScrollState()).padding(18.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(title, color = PetSitBrandDark, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                    Text("✕", fontSize = 24.sp, modifier = Modifier.clickable(onClick = onClose))
                }
                HorizontalDivider(Modifier.padding(vertical = 12.dp), color = Color(0xFFB8D8D5)); content()
            }
        }
    }
}

@Composable
private fun PetViewDialog(pet: PetUi, onClose: () -> Unit, onEdit: () -> Unit, onQr: () -> Unit, onReport: () -> Unit) = DialogShell("▤  Ficha de ${pet.name}", onClose) {
    Image(painterResource(pet.imageRes), pet.name, Modifier.fillMaxWidth().height(240.dp).clip(RoundedCornerShape(15.dp)), contentScale = ContentScale.Crop)
    Text(pet.name, color = PetSitBrandDark, fontSize = 27.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 13.dp))
    Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        DetailTile("RAZA", pet.breed, Modifier.weight(1f)); DetailTile("EDAD", pet.age, Modifier.weight(1f))
    }
    Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        DetailTile("SEXO", pet.sex, Modifier.weight(1f)); DetailTile("ESTADO", pet.status.lowercase().replaceFirstChar { it.uppercase() }, Modifier.weight(1f))
    }
    Row(Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        PetAction("✎ Editar", Modifier.weight(1f), onEdit); PetAction("▦ QR", Modifier.weight(1f), onQr)
    }
    OutlinedButton(onClick = onReport, modifier = Modifier.fillMaxWidth().padding(top = 7.dp), colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFFFECEE))) {
        Text("⚑ Reportar", color = Color(0xFFC52B3B), fontWeight = FontWeight.Bold)
    }
    Text("Tip: si completás la ficha, el QR sirve como tarjeta para que te contacten rápido.", color = PetSitMuted, fontSize = 12.sp, lineHeight = 17.sp, modifier = Modifier.padding(top = 9.dp))
}

@Composable
private fun DetailTile(label: String, value: String, modifier: Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(Color.White), border = BorderStroke(1.dp, Color(0xFFE2E8E7)), shape = RoundedCornerShape(14.dp)) {
        Column(Modifier.padding(12.dp)) { Text(label, color = PetSitMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold); Text(value, color = PetSitBrandDark, fontWeight = FontWeight.Bold, fontSize = 14.sp) }
    }
}

@Composable
private fun PetFormDialog(title: String, pet: PetUi?, onClose: () -> Unit) = DialogShell("⊕  $title", onClose) {
    var name by remember(pet) { mutableStateOf(pet?.name.orEmpty()) }
    var age by remember(pet) { mutableStateOf(pet?.age?.filter(Char::isDigit).orEmpty()) }
    var breed by remember(pet) { mutableStateOf(pet?.breed.orEmpty()) }
    var sex by remember(pet) { mutableStateOf(pet?.sex ?: "Hembra") }
    var marks by remember(pet) { mutableStateOf(pet?.marks.orEmpty()) }
    FormField("Nombre", name, { name = it }, "Ej: Luna"); FormField("Edad", age, { age = it }, "Años")
    FormField("Raza", breed, { breed = it }, "Ej: Mestizo"); FormField("Sexo", sex, { sex = it }, "Hembra")
    FormField("Señas particulares", marks, { marks = it }, "Collar, manchas, etc.", 3)
    OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().padding(top = 5.dp), shape = RoundedCornerShape(14.dp)) { Text("▣  Seleccionar foto", color = PetSitBrand, fontWeight = FontWeight.Bold) }
    Row(Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(onClick = onClose, modifier = Modifier.weight(1f)) { Text("Cancelar") }
        Button(onClick = onClose, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(PetSitBrand)) { Text("✓ Guardar", fontWeight = FontWeight.Bold) }
    }
}

@Composable
private fun FormField(label: String, value: String, onChange: (String) -> Unit, placeholder: String, minLines: Int = 1) {
    Text(label, color = PetSitBrandDark, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 7.dp))
    OutlinedTextField(value = value, onValueChange = onChange, modifier = Modifier.fillMaxWidth(), placeholder = { Text(placeholder) }, shape = RoundedCornerShape(14.dp), minLines = minLines, singleLine = minLines == 1)
}

@Composable
private fun PetQrDialog(pet: PetUi, onClose: () -> Unit) = DialogShell("▦  QR de ${pet.name}", onClose) {
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color(0xFFF8FBFA)), border = BorderStroke(1.dp, Color(0xFFB8D8D5))) {
        Column(Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            FakeQr(pet.name); Text("Escanealo para ver la ficha y contactarte con la familia.", color = PetSitMuted, fontSize = 12.sp, modifier = Modifier.padding(top = 12.dp))
        }
    }
    Text("Link del QR", color = PetSitBrandDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 14.dp))
    Surface(Modifier.fillMaxWidth().padding(top = 6.dp), shape = RoundedCornerShape(12.dp), color = PetSitBrandSoft) { Text("petsit.app/mascota/${pet.name.lowercase()}", color = PetSitBrandDark, fontSize = 12.sp, modifier = Modifier.padding(13.dp)) }
    Row(Modifier.fillMaxWidth().padding(top = 10.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = {}, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(PetSitBrand)) { Text("Descargar", fontSize = 12.sp) }
        OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) { Text("Imprimir", fontSize = 12.sp) }
    }
    Text("QR activo", color = PetSitBrandDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 14.dp))
    Button(onClick = {}, modifier = Modifier.fillMaxWidth().padding(top = 8.dp), colors = ButtonDefaults.buttonColors(Color(0xFFE23748))) { Text("↻ Regenerar QR") }
}

@Composable
private fun FakeQr(seed: String) {
    val cells = remember(seed) { List(17 * 17) { index -> ((index * 31 + seed.hashCode()) % 7 + 7) % 7 < 3 } }
    Column(Modifier.size(225.dp).background(Color.White).padding(12.dp)) {
        repeat(17) { row -> Row(Modifier.weight(1f)) { repeat(17) { column -> Box(Modifier.weight(1f).fillMaxHeight().background(if (cells[row * 17 + column]) Color.Black else Color.White)) } } }
    }
}

@Composable
private fun PetReportDialog(pet: PetUi, onClose: () -> Unit) = DialogShell("⚑  Reportar a ${pet.name}", onClose) {
    var event by remember { mutableStateOf("Se perdió") }; var zone by remember { mutableStateOf("") }; var details by remember { mutableStateOf("") }
    FormField("¿Qué pasó?", event, { event = it }, "Se perdió"); FormField("Zona", zone, { zone = it }, "Barrio / Localidad")
    FormField("Detalles", details, { details = it }, "Última vez visto, collar, contacto alternativo, etc.", 4)
    Row(Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(onClick = onClose, modifier = Modifier.weight(1f)) { Text("Cancelar") }
        Button(onClick = onClose, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(Color(0xFFE23748))) { Text("⚑ Publicar", fontWeight = FontWeight.Bold) }
    }
    Text("Prototipo visual: la publicación real se conectará al backend.", color = PetSitMuted, fontSize = 10.sp, modifier = Modifier.padding(top = 8.dp))
}
