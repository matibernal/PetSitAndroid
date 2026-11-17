package com.petsit.app.screens.publicarea

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import ar.edu.davinci.petsit.mobile.R
import com.petsit.app.data.SampleData
import com.petsit.app.model.VeterinarianUi
import com.petsit.app.ui.components.PetSitPage
import com.petsit.app.ui.theme.*

@Composable
fun VeterinariansScreen() {
    var selected by remember { mutableStateOf<VeterinarianUi?>(null) }

    PetSitPage {
        VeterinariansHero()
        Row(
            Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ServiceShortcut("⌂", "A domicilio", "Atención en tu casa", Modifier.weight(1f))
            ServiceShortcut("▦", "Guardia 24hs", "Siempre disponibles", Modifier.weight(1f))
        }

        OutlinedTextField(
            value = "", onValueChange = {},
            placeholder = { Text("⌕  Buscar por nombre o barrio") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            shape = RoundedCornerShape(30.dp), singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White)
        )
        Text("8 veterinaria(s) encontradas", color = PetSitMuted, fontSize = 13.sp, modifier = Modifier.padding(top = 15.dp, bottom = 4.dp))

        SampleData.veterinariansDetailed.forEach { veterinarian ->
            VeterinarianCard(veterinarian) { selected = veterinarian }
        }
    }

    selected?.let { veterinarian ->
        VeterinarianDialog(veterinarian) { selected = null }
    }
}

@Composable
private fun VeterinariansHero() {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(PetSitBrandSoft),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(horizontal = 22.dp, vertical = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(shape = RoundedCornerShape(50), color = Color(0xFFD7E8E6)) {
                Text("♥  CUIDADO ANIMAL", color = PetSitBrand, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp))
            }
            Text("Veterinarias", color = PetSitBrandDark, fontSize = 36.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 7.dp))
            Box(Modifier.width(190.dp).height(5.dp).clip(RoundedCornerShape(50)).background(Color(0xFFAFD3D0)))
            Text("Clínicas, guardias y atención a domicilio para cuidar a quienes más querés.", color = PetSitMuted, lineHeight = 21.sp, modifier = Modifier.padding(top = 12.dp))
            Image(painterResource(R.drawable.vet_hero), "Perro veterinario", Modifier.fillMaxWidth().height(185.dp), contentScale = ContentScale.Fit)
            Text("¿Querés ser parte de PetSit?", color = PetSitBrandDark, fontWeight = FontWeight.Bold)
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(PetSitBrand), shape = RoundedCornerShape(24.dp), modifier = Modifier.padding(top = 7.dp)) {
                Text("⊕  Sumate", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ServiceShortcut(icon: String, title: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(modifier, shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(3.dp)) {
        Column(Modifier.padding(14.dp)) {
            Surface(shape = RoundedCornerShape(13.dp), color = PetSitBrandSoft) {
                Text(icon, color = PetSitBrand, fontSize = 22.sp, modifier = Modifier.padding(10.dp))
            }
            Text(title, color = PetSitBrandDark, fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 9.dp))
            Text(subtitle, color = PetSitMuted, fontSize = 11.sp, lineHeight = 15.sp, modifier = Modifier.padding(top = 3.dp))
        }
    }
}

@Composable
private fun VeterinarianCard(veterinarian: VeterinarianUi, onMore: () -> Unit) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 9.dp),
        shape = RoundedCornerShape(23.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Image(painterResource(veterinarian.imageRes), veterinarian.name, Modifier.fillMaxWidth().height(190.dp), contentScale = ContentScale.Crop)
        Column(Modifier.padding(17.dp)) {
            Text(veterinarian.name, color = PetSitBrandDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("⌖  ${veterinarian.neighborhood}", color = PetSitMuted, modifier = Modifier.padding(top = 5.dp))
            Row(Modifier.padding(top = 11.dp), horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                if (veterinarian.homeCare) ServiceTag("A domicilio")
                if (veterinarian.emergency24h) ServiceTag("Guardia 24 h")
            }
            OutlinedButton(onClick = onMore, modifier = Modifier.fillMaxWidth().padding(top = 14.dp), shape = RoundedCornerShape(25.dp)) {
                Text("Ver más", color = PetSitBrand, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@Composable
private fun ServiceTag(label: String) {
    Surface(shape = RoundedCornerShape(50), color = PetSitBrandSoft, border = BorderStroke(1.dp, Color(0xFFB8D8D5))) {
        Text(label, color = PetSitBrandDark, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
    }
}

@Composable
private fun VeterinarianDialog(veterinarian: VeterinarianUi, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(Color.White)) {
            Column(Modifier.padding(18.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Veterinaria", color = PetSitBrandDark, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                    Text("✕", color = PetSitMuted, fontSize = 23.sp, modifier = Modifier.clickable(onClick = onDismiss))
                }
                HorizontalDivider(Modifier.padding(vertical = 11.dp), color = Color(0xFFB8D8D5))
                Image(painterResource(veterinarian.imageRes), veterinarian.name, Modifier.fillMaxWidth().height(165.dp).clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)
                Text(veterinarian.name, color = PetSitBrandDark, fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp))
                ServiceTag(veterinarian.neighborhood)
                Card(Modifier.fillMaxWidth().padding(top = 10.dp), colors = CardDefaults.cardColors(Color.White), border = BorderStroke(1.dp, Color(0xFFE0E7E5))) {
                    Column(Modifier.padding(13.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                        Text("⌖  ${veterinarian.address}", color = PetSitBrandDark)
                        Text("☎  ${veterinarian.phone}", color = PetSitBrandDark)
                        Text("◷  ${veterinarian.hours}", color = PetSitBrandDark, fontSize = 13.sp)
                    }
                }
                Row(Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) { Text("⌖  Ver en Maps", color = PetSitBrand, fontSize = 12.sp) }
                    Button(onClick = {}, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(Color(0xFFA2DB91))) { Text("WhatsApp", color = PetSitInk, fontSize = 12.sp) }
                }
                Button(onClick = {}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFF7DB5B4))) {
                    Text("☎  Llamar", color = PetSitInk, fontWeight = FontWeight.Bold)
                }
                Text("Prototipo visual: los botones se conectarán en la etapa funcional.", color = PetSitMuted, fontSize = 10.sp, modifier = Modifier.padding(top = 7.dp))
            }
        }
    }
}
