package com.petsit.app.screens.publicarea

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
import com.petsit.app.model.ShelterUi
import com.petsit.app.ui.components.PetSitPage
import com.petsit.app.ui.theme.*

@Composable
fun SheltersScreen() {
    var selected by remember { mutableStateOf<ShelterUi?>(null) }

    PetSitPage {
        Card(
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(PetSitBrandSoft),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(Modifier.padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(shape = RoundedCornerShape(50), color = Color(0xFFD7E8E6)) {
                    Text("♥  PROTECCIÓN ANIMAL", color = PetSitBrand, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp))
                }
                Text("Refugios", color = PetSitBrandDark, fontSize = 37.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                Box(Modifier.width(190.dp).height(6.dp).clip(RoundedCornerShape(50)).background(Color(0xFFAFD3D0)))
                Text("Encontrá refugios cercanos, buscá por nombre o ciudad y mirá su ficha de contacto.", color = PetSitMuted, lineHeight = 21.sp, modifier = Modifier.padding(top = 13.dp))
                Image(painterResource(R.drawable.shelter_hero), null, Modifier.fillMaxWidth().height(190.dp), contentScale = ContentScale.Fit)
            }
        }

        Card(
            Modifier.fillMaxWidth().padding(top = 18.dp),
            shape = RoundedCornerShape(19.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            OutlinedTextField(
                value = "", onValueChange = {},
                placeholder = { Text("⌕  Buscar refugios (nombre o ciudad)") },
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                shape = RoundedCornerShape(30.dp), singleLine = true
            )
        }

        Text("9 refugio(s) encontrados", color = PetSitMuted, fontSize = 13.sp, modifier = Modifier.padding(top = 18.dp, bottom = 4.dp))

        SampleData.sheltersDetailed.forEach { shelter ->
            ShelterCard(shelter) { selected = shelter }
        }
    }

    selected?.let { shelter ->
        ShelterDialog(shelter) { selected = null }
    }
}

@Composable
private fun ShelterCard(shelter: ShelterUi, onMore: () -> Unit) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 9.dp),
        shape = RoundedCornerShape(23.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Image(painterResource(shelter.imageRes), shelter.name, Modifier.fillMaxWidth().height(190.dp), contentScale = ContentScale.Crop)
        Column(Modifier.padding(17.dp)) {
            Text(shelter.name, color = PetSitBrandDark, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("⌖  " + shelter.neighborhood, color = PetSitMuted, modifier = Modifier.padding(top = 6.dp))
            OutlinedButton(onClick = onMore, modifier = Modifier.fillMaxWidth().padding(top = 14.dp), shape = RoundedCornerShape(25.dp)) {
                Text("Ver más", color = PetSitBrand, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@Composable
private fun ShelterDialog(shelter: ShelterUi, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(Modifier.padding(18.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Refugio", color = PetSitBrandDark, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                    Text("✕", color = PetSitMuted, fontSize = 23.sp, modifier = Modifier.clickable(onClick = onDismiss))
                }
                HorizontalDivider(Modifier.padding(vertical = 11.dp), color = Color(0xFFB8D8D5))
                Image(painterResource(shelter.imageRes), shelter.name, Modifier.fillMaxWidth().height(165.dp).clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)
                Text(shelter.name, color = PetSitBrandDark, fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp))
                Surface(shape = RoundedCornerShape(50), color = PetSitBrandSoft) {
                    Text(shelter.neighborhood, color = PetSitBrand, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                }
                Card(Modifier.fillMaxWidth().padding(top = 10.dp), colors = CardDefaults.cardColors(Color.White), border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E7E5))) {
                    Column(Modifier.padding(13.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                        Text("⌖  " + shelter.address, color = PetSitBrandDark)
                        Text("⌕  " + shelter.phone, color = PetSitBrandDark)
                        Text("◷  " + shelter.hours, color = PetSitBrandDark, fontSize = 13.sp)
                    }
                }
                Row(Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) { Text("Maps", color = PetSitBrand) }
                    Button(onClick = {}, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(Color(0xFFA2DB91))) { Text("WhatsApp", color = PetSitInk) }
                }
                Button(onClick = {}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color(0xFF7DB5B4))) {
                    Text("☎  Llamar", color = PetSitInk, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

