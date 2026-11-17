package com.petsit.app.screens.user

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import ar.edu.davinci.petsit.mobile.R
import com.petsit.app.ui.components.PetSitPage
import com.petsit.app.ui.theme.*

private data class ProfileNotice(val title: String, val detail: String, var unread: Boolean)

@Composable
fun ProfileScreen(onMyPets: () -> Unit, onRequests: () -> Unit, onLogout: () -> Unit) {
    var editOpen by remember { mutableStateOf(false) }
    var avatarOpen by remember { mutableStateOf(false) }
    var notificationsOpen by remember { mutableStateOf(false) }
    var avatar by remember { mutableIntStateOf(R.drawable.profile_avatar_1) }
    var emailNotices by remember { mutableStateOf(false) }
    var appNotices by remember { mutableStateOf(false) }
    var alerts by remember { mutableStateOf(false) }
    var notice by remember { mutableStateOf<String?>(null) }
    var notifications by remember { mutableStateOf(listOf(
        ProfileNotice("Novedad en veterinarias", "Agregamos nuevas clínicas en tu zona.", true),
        ProfileNotice("Refugio cercano", "Se publicó un nuevo animal en adopción.", true),
        ProfileNotice("Recordatorio", "Actualizá la ficha de tu mascota.", false)
    )) }

    PetSitPage {
        Text("Configuración de tu cuenta", color = PetSitBrandDark, fontSize = 30.sp, fontWeight = FontWeight.Bold, lineHeight = 34.sp)
        Text("Actualizá tus datos, preferencias y alertas en un solo lugar.", color = PetSitMuted, modifier = Modifier.padding(top = 6.dp, bottom = 15.dp))
        notice?.let { Card(Modifier.fillMaxWidth().padding(bottom = 11.dp), colors = CardDefaults.cardColors(Color(0xFFE4F5E5))) { Text("✓ $it", color = Color(0xFF27632A), modifier = Modifier.padding(12.dp)) } }

        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color.White), shape = RoundedCornerShape(23.dp), elevation = CardDefaults.cardElevation(3.dp)) {
            Column(Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painterResource(avatar), "Imagen de perfil", Modifier.size(125.dp).clip(CircleShape), contentScale = ContentScale.Crop)
                OutlinedButton(onClick = { avatarOpen = true }, modifier = Modifier.padding(top = 9.dp), shape = RoundedCornerShape(22.dp)) { Text("▣ Cambiar imagen", color = PetSitBrand, fontWeight = FontWeight.Bold) }
                Text("Perfil completado   80%", color = PetSitBrandDark, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth().padding(top = 13.dp))
                LinearProgressIndicator(progress = .8f, modifier = Modifier.fillMaxWidth().padding(top = 6.dp).height(8.dp).clip(RoundedCornerShape(50)), color = PetSitBrand, trackColor = Color(0xFFE3ECEA))
                Text("Excelente: tu perfil ya está completo.", color = PetSitMuted, fontSize = 11.sp, modifier = Modifier.fillMaxWidth().padding(top = 6.dp))
                HorizontalDivider(Modifier.padding(vertical = 15.dp), color = Color(0xFFE0E8E6))
                Surface(shape = RoundedCornerShape(50), color = PetSitBrandSoft) { Text("Miembro desde 2025", color = PetSitBrandDark, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 11.dp, vertical = 5.dp)) }
                Text("Keila Degregorio", color = PetSitBrandDark, fontSize = 27.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                Text("keila@example.com", color = PetSitMuted)
                Row(Modifier.padding(top = 9.dp), horizontalArrangement = Arrangement.spacedBy(7.dp)) { ProfileTag("✓ Cuenta verificada"); ProfileTag("⌖ Buenos Aires") }
                ProfileInfo("TELÉFONO", "+54 11 5555-5555"); ProfileInfo("CIUDAD", "Buenos Aires")
                Row(Modifier.fillMaxWidth().padding(top = 13.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { editOpen = true }, modifier = Modifier.weight(1f)) { Text("✎ Editar", color = PetSitBrand) }
                    Button(onClick = onLogout, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(Color(0xFFE23748))) { Text("Salir", fontWeight = FontWeight.Bold) }
                }
            }
        }

        Row(Modifier.fillMaxWidth().padding(top = 14.dp), horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            ProfileMetric("3", "Mascotas", Modifier.weight(1f), onMyPets); ProfileMetric("1", "Adopciones", Modifier.weight(1f), onRequests)
        }
        Button(onClick = { notificationsOpen = true }, modifier = Modifier.fillMaxWidth().padding(top = 13.dp), colors = ButtonDefaults.buttonColors(PetSitBrand), shape = RoundedCornerShape(18.dp)) {
            Text("♢ Notificaciones (${notifications.count { it.unread }})", fontWeight = FontWeight.Bold)
        }

        SettingsCard("PREFERENCIAS Y NOTIFICACIONES") {
            ToggleRow("Recibir avisos por correo", emailNotices) { emailNotices = it }
            ToggleRow("Notificaciones de la app", appNotices) { appNotices = it }
            Button(onClick = { notice = "Preferencias guardadas" }, modifier = Modifier.align(Alignment.End).padding(top = 8.dp), colors = ButtonDefaults.buttonColors(PetSitBrand)) { Text("Guardar preferencias") }
        }
        SettingsCard("ALERTAS DE ADOPCIÓN") {
            ProfileInput("Tipo", "Cualquier tipo"); ProfileInput("Tamaño", "Cualquiera"); ProfileInput("Zona", "Barrio / Ciudad")
            ToggleRow("Activar alertas", alerts) { alerts = it }
            Button(onClick = { notice = "Alertas guardadas" }, modifier = Modifier.align(Alignment.End).padding(top = 8.dp), colors = ButtonDefaults.buttonColors(PetSitBrand)) { Text("Guardar alertas") }
        }
    }

    if (editOpen) EditProfileDialog { editOpen = false; if (it) notice = "Perfil actualizado" }
    if (avatarOpen) AvatarDialog(avatar, { avatarOpen = false }) { avatar = it; avatarOpen = false; notice = "Imagen de perfil actualizada" }
    if (notificationsOpen) NotificationsDialog(notifications, { notificationsOpen = false }) { notifications = it }
}

@Composable private fun ProfileTag(label: String) = Surface(shape = RoundedCornerShape(50), color = Color.White, border = BorderStroke(1.dp, Color(0xFFB8D8D5))) { Text(label, color = PetSitBrandDark, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp)) }

@Composable private fun ProfileInfo(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(top = 12.dp)) { Text(label, color = PetSitMuted, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.weight(1f)); Text(value, color = PetSitBrandDark, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.5f)) }
}

@Composable private fun ProfileMetric(value: String, label: String, modifier: Modifier, action: () -> Unit) {
    Card(modifier.clickable(onClick = action), colors = CardDefaults.cardColors(Color.White), shape = RoundedCornerShape(18.dp), elevation = CardDefaults.cardElevation(2.dp)) { Column(Modifier.padding(15.dp)) { Text(value, color = PetSitBrandDark, fontSize = 25.sp, fontWeight = FontWeight.Bold); Text(label, color = PetSitMuted, fontSize = 12.sp); Text("Ver →", color = PetSitBrand, fontWeight = FontWeight.Bold, fontSize = 11.sp, modifier = Modifier.padding(top = 7.dp)) } }
}

@Composable private fun SettingsCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(Modifier.fillMaxWidth().padding(top = 14.dp), colors = CardDefaults.cardColors(Color.White), shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(2.dp)) { Column(Modifier.padding(17.dp)) { Text(title, color = PetSitBrandDark, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold); HorizontalDivider(Modifier.padding(vertical = 9.dp), color = Color(0xFFE0E8E6)); content() } }
}

@Composable private fun ToggleRow(label: String, checked: Boolean, change: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) { Text(label, color = PetSitBrandDark, modifier = Modifier.weight(1f)); Switch(checked = checked, onCheckedChange = change, colors = SwitchDefaults.colors(checkedTrackColor = PetSitBrand)) }
}

@Composable private fun ProfileInput(label: String, hint: String) {
    var value by remember { mutableStateOf("") }
    Text(label, color = PetSitBrandDark, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 6.dp))
    OutlinedTextField(value, { value = it }, Modifier.fillMaxWidth(), placeholder = { Text(hint) }, singleLine = true, shape = RoundedCornerShape(14.dp))
}

@Composable private fun ProfileDialog(title: String, close: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Dialog(onDismissRequest = close) { Card(Modifier.fillMaxWidth().heightIn(max = 720.dp), colors = CardDefaults.cardColors(Color.White), shape = RoundedCornerShape(23.dp)) { Column(Modifier.verticalScroll(rememberScrollState()).padding(18.dp)) { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(title, color = PetSitBrandDark, fontSize = 20.sp, fontWeight = FontWeight.Bold); Text("✕", fontSize = 23.sp, modifier = Modifier.clickable(onClick = close)) }; HorizontalDivider(Modifier.padding(vertical = 12.dp)); content() } } }
}

@Composable private fun EditProfileDialog(close: (Boolean) -> Unit) = ProfileDialog("✎ Editar perfil", { close(false) }) {
    var name by remember { mutableStateOf("Keila Degregorio") }; var email by remember { mutableStateOf("keila@example.com") }; var phone by remember { mutableStateOf("+54 11 5555-5555") }; var city by remember { mutableStateOf("Buenos Aires") }
    EditField("Nombre", name) { name = it }; EditField("Email", email) { email = it }; EditField("Teléfono", phone) { phone = it }; EditField("Ciudad", city) { city = it }
    Row(Modifier.fillMaxWidth().padding(top = 13.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) { OutlinedButton(onClick = { close(false) }, modifier = Modifier.weight(1f)) { Text("Cancelar") }; Button(onClick = { close(true) }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(PetSitBrand)) { Text("Guardar") } }
}

@Composable private fun EditField(label: String, value: String, change: (String) -> Unit) { Text(label, color = PetSitBrandDark, fontSize = 12.sp, modifier = Modifier.padding(top = 7.dp)); OutlinedTextField(value, change, Modifier.fillMaxWidth(), singleLine = true, shape = RoundedCornerShape(14.dp)) }

@Composable private fun AvatarDialog(@DrawableRes current: Int, close: () -> Unit, choose: (Int) -> Unit) = ProfileDialog("◉ Imagen de perfil", close) {
    val avatars = listOf(R.drawable.profile_avatar_1, R.drawable.profile_avatar_2, R.drawable.profile_avatar_3, R.drawable.profile_avatar_4, R.drawable.profile_avatar_5, R.drawable.profile_avatar_6, R.drawable.profile_avatar_7, R.drawable.profile_avatar_8, R.drawable.profile_avatar_9, R.drawable.profile_avatar_10, R.drawable.profile_avatar_11, R.drawable.profile_avatar_12, R.drawable.profile_avatar_13, R.drawable.profile_avatar_14)
    Text("Elegí un avatar", color = PetSitMuted, modifier = Modifier.padding(bottom = 9.dp))
    avatars.chunked(2).forEach { row -> Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) { row.forEach { image -> Image(painterResource(image), "Avatar", Modifier.weight(1f).aspectRatio(1f).padding(vertical = 5.dp).clip(RoundedCornerShape(15.dp)).border(if (image == current) 4.dp else 1.dp, if (image == current) PetSitBrand else Color(0xFFE1E6E5), RoundedCornerShape(15.dp)).clickable { choose(image) }, contentScale = ContentScale.Crop) }; if (row.size == 1) Spacer(Modifier.weight(1f)) } }
}

@Composable private fun NotificationsDialog(items: List<ProfileNotice>, close: () -> Unit, update: (List<ProfileNotice>) -> Unit) = ProfileDialog("♢ Notificaciones", close) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) { OutlinedButton(onClick = { update(items.map { it.copy(unread = false) }) }, modifier = Modifier.weight(1f)) { Text("Marcar leídas", fontSize = 11.sp) }; Button(onClick = { update(emptyList()) }, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(Color(0xFFC62828))) { Text("Borrar todas", fontSize = 11.sp) } }
    if (items.isEmpty()) Text("No tenés notificaciones.", color = PetSitMuted, modifier = Modifier.padding(vertical = 25.dp))
    items.forEachIndexed { index, item -> Card(Modifier.fillMaxWidth().padding(top = 10.dp), colors = CardDefaults.cardColors(if (item.unread) Color(0xFFFFFBF0) else Color(0xFFF8FAF9)), shape = RoundedCornerShape(15.dp)) { Column(Modifier.padding(13.dp)) { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(item.title, color = PetSitBrandDark, fontWeight = FontWeight.Bold); Text("✕", color = Color(0xFFC62828), modifier = Modifier.clickable { update(items.filterIndexed { i, _ -> i != index }) }) }; Text(item.detail, color = PetSitMuted, fontSize = 12.sp); if (item.unread) Text("Nuevo", color = Color(0xFFC62828), fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 5.dp)) } } }
}
