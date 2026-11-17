package com.petsit.app.screens.publicarea

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.petsit.app.ui.components.*
import com.petsit.app.ui.theme.*

@Composable
fun ContactScreen() = PetSitPage {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(27.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(22.dp)) {
            Text("Contactanos", color = PetSitBrandDark, fontSize = 34.sp, fontWeight = FontWeight.Bold)
            Box(
                Modifier.width(220.dp).height(7.dp).padding(top = 2.dp)
                    .clip(RoundedCornerShape(50)).background(Color(0xFFBAD8D5))
            )

            Spacer(Modifier.height(22.dp))
            Text("Nombre", color = PetSitBrandDark, fontWeight = FontWeight.Bold)
            StaticField("Tu nombre")

            Spacer(Modifier.height(5.dp))
            Text("Email", color = PetSitBrandDark, fontWeight = FontWeight.Bold)
            StaticField("nombre@correo.com")

            Spacer(Modifier.height(5.dp))
            Text("Mensaje", color = PetSitBrandDark, fontWeight = FontWeight.Bold)
            StaticField("Contanos en qué podemos ayudarte", multiline = true)

            Button(
                onClick = {},
                modifier = Modifier.width(145.dp).height(51.dp).padding(top = 6.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(PetSitBrand)
            ) {
                Text("Enviar", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(28.dp))
            Text("UBICANOS", color = PetSitBrandDark, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
            Spacer(Modifier.height(10.dp))
            ContactMap()
        }
    }
}

@Composable
private fun ContactMap() {
    Box(
        Modifier.fillMaxWidth().height(285.dp).clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFE8E5DD))
    ) {
        repeat(5) { index ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .offset(y = (44 + index * 51).dp)
                    .background(if (index % 2 == 0) Color.White else Color(0xFFD5DDE1))
            )
        }
        repeat(4) { index ->
            Box(
                Modifier
                    .fillMaxHeight()
                    .width(3.dp)
                    .offset(x = (48 + index * 70).dp)
                    .background(if (index % 2 == 0) Color.White else Color(0xFFD5DDE1))
            )
        }

        Card(
            Modifier.fillMaxWidth(.78f).padding(10.dp),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(Modifier.padding(11.dp)) {
                Text("Avenida Corrientes", color = PetSitInk, fontWeight = FontWeight.Bold)
                Text("Av. Corrientes, Cdad. Autónoma de Buenos Aires", color = PetSitMuted, fontSize = 11.sp)
            }
        }

        Column(
            Modifier.align(Alignment.Center).offset(y = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("●", color = Color(0xFFD93025), fontSize = 36.sp, lineHeight = 28.sp)
            Surface(color = Color.White.copy(alpha = .9f), shape = RoundedCornerShape(6.dp)) {
                Text("Av. Corrientes", color = Color(0xFFC5221F), fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp))
            }
        }

        Text(
            "Mapa de ubicación",
            color = PetSitMuted,
            fontSize = 10.sp,
            modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
        )
    }
}

