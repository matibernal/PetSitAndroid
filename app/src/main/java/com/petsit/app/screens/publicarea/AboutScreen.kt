package com.petsit.app.screens.publicarea

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import ar.edu.davinci.petsit.mobile.R
import com.petsit.app.ui.components.PetSitPage
import com.petsit.app.ui.theme.*

private data class AboutItem(val icon: Int, val title: String, val description: String)
private data class TeamMember(val image: Int, val name: String, val role: String, val description: String)

@Composable
fun AboutScreen() = PetSitPage {
    Surface(shape = RoundedCornerShape(50), color = PetSitBrandSoft) {
        Text("Nosotros", color = PetSitBrand, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp))
    }

    Text(
        "Ayudamos a que mascotas y familias se reencuentren",
        color = PetSitBrandDark, fontSize = 35.sp, lineHeight = 39.sp, fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 16.dp)
    )
    Box(Modifier.width(220.dp).height(7.dp).clip(RoundedCornerShape(50)).background(Color(0xFFBAD8D5)))

    Text(
        "Somos estudiantes de la Escuela Da Vinci y nos apasionan los animales. PetSit surge como un proyecto para construir comunidad alrededor del cuidado y la protección de las mascotas.",
        color = PetSitInk, fontSize = 15.sp, lineHeight = 23.sp, modifier = Modifier.padding(top = 20.dp)
    )
    Text(
        "Queremos vincular refugios, veterinarias y vecinos en un mismo lugar: facilitar adopciones, agilizar el reencuentro de mascotas perdidas y promover la tenencia responsable con herramientas simples y confiables.",
        color = PetSitInk, fontSize = 15.sp, lineHeight = 23.sp, modifier = Modifier.padding(top = 12.dp)
    )

    OutlinedButton(onClick = {}, shape = RoundedCornerShape(25.dp), modifier = Modifier.padding(top = 18.dp)) {
        Text("Conocé el equipo  ↓", color = PetSitBrand, fontWeight = FontWeight.Bold)
    }

    Box(Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
        Box(Modifier.size(230.dp).offset(x = 22.dp, y = (-20).dp).clip(RoundedCornerShape(75.dp)).background(Color(0xFFD5E6E4)))
        Box(Modifier.size(205.dp).offset(x = (-22).dp, y = 45.dp).clip(CircleShape).background(Color(0xFFF0E8D9)))
        Image(painterResource(R.drawable.about_hero_dog), null, Modifier.fillMaxWidth().height(290.dp), contentScale = ContentScale.Fit)
    }

    AboutSectionTitle("Nuestros pilares", "Lo que guía cada decisión en PetSit.")
    val pillars = listOf(
        AboutItem(R.drawable.icon_mision, "Misión", "Impulsar el bienestar animal y la reunificación rápida de mascotas, promoviendo una comunidad solidaria y responsable."),
        AboutItem(R.drawable.icon_vision, "Visión", "Una sociedad donde ninguna mascota se quede sin hogar y el cuidado responsable sea parte de la cultura."),
        AboutItem(R.drawable.icon_etica, "Ética", "Transparencia, responsabilidad y foco en el interés de los animales en cada decisión."),
        AboutItem(R.drawable.icon_objetivo, "Objetivo", "Crear una comunidad comprometida con la protección, la adopción responsable y la reunificación de mascotas con sus familias.")
    )
    pillars.forEach { PillarCard(it) }

    Spacer(Modifier.height(25.dp))
    AboutSectionTitle("Nuestros servicios", "Todo lo que necesitás para cuidar y localizar a tus mascotas.")
    listOf(
        AboutItem(R.drawable.icon_registro_qr, "Registro y QR", "Código QR único con datos de contacto para volver a casa más rápido."),
        AboutItem(R.drawable.icon_servicio_refugio, "Refugios", "Conocé refugios cercanos."),
        AboutItem(R.drawable.icon_servicio_veterinaria, "Veterinarias", "Clínicas cercanas, guardias y visitas a domicilio."),
        AboutItem(R.drawable.icon_mascota_perdida, "Mascotas perdidas", "Publicá o buscá avisos para reencontrarse cuanto antes."),
        AboutItem(R.drawable.icon_adopcion, "Adopciones", "Adoptá con responsabilidad y encontrá tu nuevo compañero.")
    ).forEach { ServiceCard(it) }

    Spacer(Modifier.height(25.dp))
    AboutSectionTitle("Nuestro equipo", "Personas que diseñan, construyen y cuidan la experiencia.")
    listOf(
        TeamMember(R.drawable.team_agustin, "Agustín Segovia", "Desarrollo Back-End · Dirección de Proyecto", "Lidera la estrategia técnica y garantiza una base sólida y segura para la plataforma."),
        TeamMember(R.drawable.team_maia, "Maia Cohen", "Desarrollo Back-End · Dirección de Proyecto", "Optimiza la eficiencia del back-end y coordina hitos para entregar funcionalidades clave."),
        TeamMember(R.drawable.team_matias, "Matías Bernal", "Seguridad · Documentación · Representación", "Asegura la seguridad y la documentación clara del proyecto; apoya acciones de marketing técnico."),
        TeamMember(R.drawable.team_keila, "Keila Degregorio", "Diseño · Front-End · Marketing", "Define la identidad visual y UX; lidera el front-end y la estrategia de marketing del producto.")
    ).forEach { TeamCard(it) }
}

@Composable
private fun AboutSectionTitle(title: String, subtitle: String) {
    Text(title, color = PetSitBrandDark, fontSize = 29.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
    Box(Modifier.width(220.dp).height(6.dp).clip(RoundedCornerShape(50)).background(Color(0xFFBAD8D5)))
    Text(subtitle, color = PetSitMuted, modifier = Modifier.padding(top = 11.dp, bottom = 12.dp))
}

@Composable
private fun PillarCard(item: AboutItem) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 7.dp), shape = RoundedCornerShape(23.dp),
        colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(74.dp).clip(RoundedCornerShape(22.dp)).background(PetSitBrandSoft), contentAlignment = Alignment.Center) {
                Image(painterResource(item.icon), null, Modifier.size(48.dp), contentScale = ContentScale.Fit)
            }
            Text(item.title, color = PetSitBrandDark, fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 9.dp))
            Text(item.description, color = PetSitMuted, textAlign = androidx.compose.ui.text.style.TextAlign.Center, lineHeight = 21.sp, modifier = Modifier.padding(top = 7.dp))
        }
    }
}

@Composable
private fun ServiceCard(item: AboutItem) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 7.dp), shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(58.dp).clip(RoundedCornerShape(18.dp)).background(PetSitBrandSoft), contentAlignment = Alignment.Center) {
                Image(painterResource(item.icon), null, Modifier.size(38.dp), contentScale = ContentScale.Fit)
            }
            Column(Modifier.padding(start = 15.dp)) {
                Text(item.title, color = PetSitBrandDark, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                Text(item.description, color = PetSitMuted, lineHeight = 20.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

@Composable
private fun TeamCard(member: TeamMember) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(23.dp),
        colors = CardDefaults.cardColors(Color.White), elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(Modifier.padding(19.dp)) {
            Image(painterResource(member.image), member.name, Modifier.size(74.dp).clip(RoundedCornerShape(21.dp)), contentScale = ContentScale.Crop)
            Text(member.description, color = PetSitInk, lineHeight = 21.sp, modifier = Modifier.padding(top = 14.dp, bottom = 13.dp))
            HorizontalDivider(color = Color(0xFFD9E5E3))
            Text(member.name, color = PetSitBrandDark, fontWeight = FontWeight.ExtraBold, fontSize = 17.sp, modifier = Modifier.padding(top = 12.dp))
            Text(member.role, color = PetSitMuted, lineHeight = 20.sp, modifier = Modifier.padding(top = 3.dp))
        }
    }
}

