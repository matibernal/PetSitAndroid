package com.petsit.app.screens.admin
import androidx.compose.runtime.Composable
import com.petsit.app.ui.components.FormLayout
@Composable fun AdminSettingsScreen() = FormLayout("Configuración", listOf("Nombre de la plataforma", "Correo de contacto", "Moderación automática"), "Guardar cambios")

