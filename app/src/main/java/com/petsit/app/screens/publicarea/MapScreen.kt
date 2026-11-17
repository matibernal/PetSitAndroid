package com.petsit.app.screens.publicarea

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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import ar.edu.davinci.petsit.mobile.R
import com.petsit.app.ui.components.StaticField
import com.petsit.app.ui.theme.*

private data class MapPet(
    val name: String,
    val status: String,
    val species: String,
    val breed: String,
    val zone: String,
    val date: String,
    val description: String,
    val reporter: String,
    val image: Int,
    val x: Dp,
    val y: Dp
)

@Composable
fun MapScreen() {
    val pets = remember {
        listOf(
            MapPet("Sin nombre", "PERDIDO", "Perro", "Mestizo", "Boedo", "07/09/2025", "Lo vi sin correa, blanco y negro, oreja parada. No se deja agarrar.", "Encontrador", R.drawable.map_pet_unknown, 155.dp, 280.dp),
            MapPet("Toby", "ENCONTRADO", "Perro", "Beagle", "San Telmo", "01/09/2025", "Lo vi cerca de Defensa y Chile. Con collar negro, olfateando todo.", "Encontrador", R.drawable.map_pet_toby, 255.dp, 410.dp),
            MapPet("Bruno", "PERDIDO", "Perro", "Mestizo", "Recoleta", "29/08/2025", "Se perdió cerca de la plaza. Es sociable y tiene collar azul.", "Familia", R.drawable.map_pet_bruno, 240.dp, 155.dp)
        )
    }
    var selectedPet by remember { mutableStateOf<MapPet?>(null) }
    var reportMenu by remember { mutableStateOf(false) }
    var reportType by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(PetSitBackground)) {
        Column(Modifier.padding(14.dp)) {
            OutlinedTextField(
                value = "", onValueChange = {},
                placeholder = { Text("⌕  Buscar por nombre, zona o señas") },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp), singleLine = true
            )
            Row(Modifier.fillMaxWidth().padding(top = 9.dp).horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                MapFilter("Tipo: todos")
                MapFilter("Zona: todas")
                MapFilter("Más recientes")
            }
            Box(Modifier.fillMaxWidth().padding(top = 9.dp)) {
                Button(
                    onClick = { reportMenu = !reportMenu },
                    modifier = Modifier.align(Alignment.CenterEnd),
                    colors = ButtonDefaults.buttonColors(PetSitBrand),
                    shape = RoundedCornerShape(24.dp)
                ) { Text("+  Reportar  ▾", fontWeight = FontWeight.ExtraBold) }

                DropdownMenu(expanded = reportMenu, onDismissRequest = { reportMenu = false }, modifier = Modifier.align(Alignment.TopEnd)) {
                    DropdownMenuItem(text = { Text("🔴  Perdido") }, onClick = { reportMenu = false; reportType = "PERDIDO" })
                    DropdownMenuItem(text = { Text("🟢  Encontrado") }, onClick = { reportMenu = false; reportType = "ENCONTRADO" })
                }
            }
        }

        MapCanvas(pets = pets, onMarkerClick = { selectedPet = it })
    }

    selectedPet?.let { PetMapDialog(it) { selectedPet = null } }
    reportType?.let { type -> ReportDialog(type = type, onDismiss = { reportType = null }) }
}

@Composable
private fun MapFilter(text: String) {
    Surface(shape = RoundedCornerShape(22.dp), color = Color.White, border = BorderStroke(1.dp, Color(0xFFDDE6E4))) {
        Text(text + " ⌄", color = PetSitInk, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp))
    }
}

@Composable
private fun MapCanvas(pets: List<MapPet>, onMarkerClick: (MapPet) -> Unit) {
    Box(
        Modifier.fillMaxWidth().height(650.dp).background(Color(0xFFE6E4D9))
    ) {
        Box(Modifier.align(Alignment.CenterEnd).fillMaxHeight().width(94.dp).background(Color(0xFFA9D4E1)))
        repeat(8) { index ->
            Box(
                Modifier.width(510.dp).height(if (index % 3 == 0) 6.dp else 3.dp)
                    .offset(x = (-65).dp, y = (45 + index * 72).dp)
                    .graphicsLayer(rotationZ = if (index % 2 == 0) -10f else 7f)
                    .background(if (index % 3 == 0) Color(0xFFE9B78A) else Color.White)
            )
        }
        repeat(6) { index ->
            Box(
                Modifier.height(760.dp).width(if (index % 3 == 0) 6.dp else 3.dp)
                    .offset(x = (32 + index * 57).dp, y = (-45).dp)
                    .graphicsLayer(rotationZ = if (index % 2 == 0) 12f else -8f)
                    .background(if (index % 3 == 0) Color(0xFFE9B78A) else Color.White)
            )
        }

        listOf(
            Triple("Villa Urquiza", 35.dp, 85.dp), Triple("Palermo", 220.dp, 105.dp),
            Triple("Recoleta", 225.dp, 210.dp), Triple("Caballito", 75.dp, 340.dp),
            Triple("Boedo", 165.dp, 390.dp), Triple("San Telmo", 235.dp, 485.dp)
        ).forEach { (name, x, y) ->
            Text(name, color = Color(0xFF596461), fontSize = 11.sp, modifier = Modifier.offset(x, y))
        }

        Column(Modifier.padding(8.dp)) {
            SmallFloatingActionButton(onClick = {}, containerColor = Color.White) { Text("+", color = PetSitInk, fontSize = 22.sp) }
            Spacer(Modifier.height(5.dp))
            SmallFloatingActionButton(onClick = {}, containerColor = Color.White) { Text("−", color = PetSitInk, fontSize = 22.sp) }
        }

        pets.forEach { pet ->
            Box(
                Modifier.offset(pet.x, pet.y).size(43.dp).clip(CircleShape)
                    .background(if (pet.status == "PERDIDO") Color(0xFFE34D59) else Color(0xFF249B69))
                    .clickable { onMarkerClick(pet) },
                contentAlignment = Alignment.Center
            ) {
                Text(if (pet.status == "PERDIDO") "!" else "✓", color = Color.White, fontWeight = FontWeight.ExtraBold)
            }
        }

        Surface(
            Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(13.dp),
            shape = RoundedCornerShape(18.dp), color = Color.White.copy(alpha = .94f), shadowElevation = 4.dp
        ) {
            Text("Tocá un marcador para ver la ficha de la mascota", color = PetSitBrandDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(15.dp))
        }
    }
}

@Composable
private fun PetMapDialog(pet: MapPet, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(23.dp), colors = CardDefaults.cardColors(Color.White)) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(if (pet.status == "PERDIDO") "Mascota perdida" else "Mascota encontrada", color = PetSitBrandDark, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                    Text("✕", color = PetSitMuted, fontSize = 22.sp, modifier = Modifier.clickable(onClick = onDismiss))
                }
                HorizontalDivider(color = Color(0xFFBED9D7))
                Image(painterResource(pet.image), pet.name, Modifier.fillMaxWidth().height(230.dp), contentScale = ContentScale.Crop)
                Column(Modifier.padding(17.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(pet.name, color = PetSitBrandDark, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.weight(1f))
                        Surface(shape = RoundedCornerShape(50), color = if (pet.status == "PERDIDO") Color(0xFFFFE7E5) else Color(0xFFE0F6EC)) {
                            Text(pet.status, color = if (pet.status == "PERDIDO") Color(0xFFC92E2E) else Color(0xFF087E55), fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                        }
                    }
                    Text(pet.species + " · " + pet.breed, color = PetSitMuted)
                    Text("⌖  Zona: " + pet.zone, color = PetSitBrandDark, modifier = Modifier.padding(top = 13.dp))
                    Text("□  Fecha: " + pet.date, color = PetSitBrandDark, modifier = Modifier.padding(top = 8.dp))
                    Text("♟  Reportado por: " + pet.reporter, color = PetSitBrandDark, modifier = Modifier.padding(top = 8.dp))
                    Text("▤  " + pet.description, color = PetSitMuted, lineHeight = 20.sp, modifier = Modifier.padding(top = 10.dp))
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(PetSitBrand), modifier = Modifier.padding(top = 13.dp)) {
                        Text("☎  Llamar", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun ReportDialog(type: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(23.dp), colors = CardDefaults.cardColors(Color.White)) {
            Column(Modifier.heightIn(max = 720.dp).verticalScroll(rememberScrollState()).padding(18.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Reportar mascota " + type.lowercase(), color = PetSitBrandDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(if (type == "PERDIDO") "Mientras más datos, más chances de que vuelva a casa." else "Ayudá a que su familia la encuentre lo antes posible.", color = PetSitMuted, fontSize = 12.sp)
                    }
                    Text("✕", color = PetSitMuted, fontSize = 22.sp, modifier = Modifier.clickable(onClick = onDismiss))
                }
                if (type == "ENCONTRADO") {
                    Text("¿Qué situación es?", color = PetSitBrandDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AssistChip(onClick = {}, label = { Text("La tengo conmigo") })
                        AssistChip(onClick = {}, label = { Text("Solo la vi") })
                    }
                }
                Spacer(Modifier.height(9.dp))
                OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Seleccionar foto") }
                StaticField("Nombre (opcional)")
                StaticField("Especie *")
                StaticField("Raza (opcional)")
                StaticField("Tamaño (opcional)")
                StaticField("Barrio / Zona *")
                StaticField("Fecha *")
                StaticField("Dirección aproximada")
                OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("⌖  Marcar en mapa", color = PetSitBrand) }
                StaticField("Señas / descripción *", multiline = true)
                StaticField("Teléfono / WhatsApp *")
                StaticField("Preferencia de contacto")
                Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth().padding(top = 12.dp), colors = ButtonDefaults.buttonColors(PetSitBrand)) {
                    Text("➤  Publicar reporte", fontWeight = FontWeight.ExtraBold)
                }
                OutlinedButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text("Cancelar", color = PetSitInk) }
            }
        }
    }
}
