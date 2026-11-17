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
import com.petsit.app.model.AdoptionRequestUi
import com.petsit.app.ui.components.PetSitPage
import com.petsit.app.ui.theme.*

@Composable
fun RequestsScreen(onOpenShelters: () -> Unit) {
    var requests by remember { mutableStateOf(SampleData.adoptionRequestsDetailed) }
    var filter by remember { mutableStateOf("Todas") }
    var selected by remember { mutableStateOf<AdoptionRequestUi?>(null) }
    var cancelCandidate by remember { mutableStateOf<AdoptionRequestUi?>(null) }
    var notice by remember { mutableStateOf<String?>(null) }
    val visible = requests.filter { filter == "Todas" || (filter == "Pendientes" && it.status == "PENDIENTE") || (filter == "Adoptadas" && it.status == "ADOPTADA") }

    PetSitPage {
        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(PetSitBrandSoft), shape = RoundedCornerShape(25.dp)) {
            Column(Modifier.padding(22.dp)) {
                Text("Mis solicitudes de adopción", color = PetSitBrandDark, fontSize = 30.sp, fontWeight = FontWeight.Bold, lineHeight = 34.sp)
                Box(Modifier.width(180.dp).height(5.dp).clip(RoundedCornerShape(50)).background(Color(0xFFAFD3D0)))
                Text("Acá podés ver el estado de cada solicitud y los mensajes del refugio.", color = PetSitMuted, lineHeight = 21.sp, modifier = Modifier.padding(top = 10.dp))
            }
        }
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(top = 13.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Todas", "Pendientes", "Adoptadas").forEach { label -> RequestFilter(label, filter == label) { filter = label } }
        }
        notice?.let { Card(Modifier.fillMaxWidth().padding(top = 12.dp), colors = CardDefaults.cardColors(Color(0xFFFFF4D6))) { Text(it, color = Color(0xFF785A00), modifier = Modifier.padding(13.dp)) } }
        if (visible.isEmpty()) {
            Card(Modifier.fillMaxWidth().padding(top = 18.dp), colors = CardDefaults.cardColors(Color.White)) {
                Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) { Text("No hay solicitudes en este estado", color = PetSitBrandDark, fontWeight = FontWeight.Bold); Text("Cuando envíes una solicitud, aparecerá acá.", color = PetSitMuted, fontSize = 13.sp) }
            }
        }
        visible.forEach { request -> RequestCard(request) { selected = request } }
    }

    selected?.let { request -> RequestDetailDialog(request, close = { selected = null }, shelter = { selected = null; onOpenShelters() }, cancel = { selected = null; cancelCandidate = request }) }
    cancelCandidate?.let { request -> CancelRequestDialog(request, close = { cancelCandidate = null }) {
        requests = requests.filterNot { it.petName == request.petName && it.sentDate == request.sentDate }
        cancelCandidate = null; notice = "La solicitud de ${request.petName} fue cancelada."
    } }
}

@Composable
private fun RequestFilter(label: String, selected: Boolean, action: () -> Unit) {
    Surface(Modifier.clickable(onClick = action), shape = RoundedCornerShape(50), color = if (selected) PetSitBrand else Color.White) {
        Text(label, color = if (selected) Color.White else PetSitBrandDark, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp))
    }
}

@Composable
private fun RequestCard(request: AdoptionRequestUi, open: () -> Unit) {
    Card(Modifier.fillMaxWidth().padding(top = 17.dp), colors = CardDefaults.cardColors(Color.White), shape = RoundedCornerShape(22.dp), elevation = CardDefaults.cardElevation(3.dp)) {
        Box {
            Image(painterResource(request.imageRes), request.petName, Modifier.fillMaxWidth().height(210.dp), contentScale = ContentScale.Crop)
            Box(Modifier.align(Alignment.BottomStart).padding(13.dp)) { RequestStatus(request.status) }
        }
        Column(Modifier.padding(16.dp)) {
            Text(request.petName, color = PetSitBrandDark, fontSize = 23.sp, fontWeight = FontWeight.Bold)
            Text("▣ Enviada: ${request.sentDate}", color = PetSitMuted, fontSize = 12.sp, modifier = Modifier.padding(top = 5.dp))
            Text("⌂ Refugio: ${request.shelter}", color = PetSitMuted, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
            Card(Modifier.fillMaxWidth().padding(top = 11.dp), colors = CardDefaults.cardColors(Color(0xFFFAFBFB)), border = BorderStroke(1.dp, Color(0xFFE5E9E8))) {
                Column(Modifier.padding(12.dp)) { Text("MENSAJE DEL REFUGIO", color = PetSitBrand, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold); Text(request.message, color = PetSitBrandDark, fontSize = 13.sp, lineHeight = 18.sp, modifier = Modifier.padding(top = 5.dp)) }
            }
            OutlinedButton(onClick = open, modifier = Modifier.fillMaxWidth().padding(top = 12.dp), shape = RoundedCornerShape(22.dp), border = BorderStroke(1.dp, PetSitBrand)) { Text("Ver detalle  →", color = PetSitBrand, fontWeight = FontWeight.Bold) }
        }
    }
}

@Composable
private fun RequestStatus(status: String) {
    val adopted = status == "ADOPTADA"
    Surface(shape = RoundedCornerShape(50), color = if (adopted) Color(0xFFDCEFE5) else Color(0xFFFFE9A8), border = BorderStroke(1.dp, if (adopted) Color(0xFF93C5AA) else Color(0xFFE7BD42))) {
        Text(if (adopted) "♥ Adoptada" else "◷ Pendiente", color = if (adopted) Color(0xFF24633E) else Color(0xFF795D05), fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 11.dp, vertical = 6.dp))
    }
}

@Composable
private fun RequestDetailDialog(request: AdoptionRequestUi, close: () -> Unit, shelter: () -> Unit, cancel: () -> Unit) {
    Dialog(onDismissRequest = close) {
        Card(Modifier.fillMaxWidth().heightIn(max = 720.dp), colors = CardDefaults.cardColors(Color.White), shape = RoundedCornerShape(24.dp)) {
            Column(Modifier.verticalScroll(rememberScrollState()).padding(18.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) { Text("▣ Detalle — ${request.petName}", color = PetSitBrandDark, fontSize = 20.sp, fontWeight = FontWeight.Bold); Text("✕", fontSize = 24.sp, modifier = Modifier.clickable(onClick = close)) }
                HorizontalDivider(Modifier.padding(vertical = 12.dp), color = Color(0xFFB8D8D5))
                Box { Image(painterResource(request.imageRes), request.petName, Modifier.fillMaxWidth().height(280.dp).clip(RoundedCornerShape(15.dp)), contentScale = ContentScale.Crop); Box(Modifier.padding(12.dp)) { RequestStatus(request.status) } }
                Text(request.petName, color = PetSitBrandDark, fontSize = 27.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 13.dp))
                RequestInfo("ENVIADA", request.sentDate); RequestInfo("REFUGIO", request.shelter)
                if (request.resolvedDate.isNotBlank()) RequestInfo("FINALIZADA", request.resolvedDate)
                Card(Modifier.fillMaxWidth().padding(top = 10.dp), colors = CardDefaults.cardColors(Color(0xFFFAFBFB)), border = BorderStroke(1.dp, Color(0xFFE5E9E8))) { Column(Modifier.padding(13.dp)) { Text("MENSAJE DEL REFUGIO", color = PetSitBrand, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold); Text(request.message, color = PetSitBrandDark, fontSize = 13.sp, lineHeight = 19.sp, modifier = Modifier.padding(top = 5.dp)) } }
                OutlinedButton(onClick = shelter, modifier = Modifier.fillMaxWidth().padding(top = 11.dp)) { Text("⌂ Ver refugio", color = PetSitBrand) }
                if (request.status == "PENDIENTE") OutlinedButton(onClick = cancel, modifier = Modifier.fillMaxWidth().padding(top = 6.dp), colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFFFECEE)), border = BorderStroke(1.dp, Color(0xFFF0AAB3))) { Text("⊗ Cancelar solicitud", color = Color(0xFFC52B3B), fontWeight = FontWeight.Bold) }
            }
        }
    }
}

@Composable private fun RequestInfo(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(top = 9.dp)) { Text(label, color = PetSitBrand, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.weight(1f)); Text(value, color = PetSitBrandDark, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.8f)) }
}

@Composable
private fun CancelRequestDialog(request: AdoptionRequestUi, close: () -> Unit, confirm: () -> Unit) {
    AlertDialog(onDismissRequest = close, title = { Text("⚠ ¿Cancelar solicitud?", color = PetSitBrandDark, fontWeight = FontWeight.Bold) }, text = { Text("Vas a cancelar la solicitud de ${request.petName} y el refugio no la seguirá evaluando. Podés volver a solicitar más adelante.") }, dismissButton = { OutlinedButton(onClick = close) { Text("No, volver") } }, confirmButton = { Button(onClick = confirm, colors = ButtonDefaults.buttonColors(Color(0xFFE23748))) { Text("Sí, cancelar") } })
}
