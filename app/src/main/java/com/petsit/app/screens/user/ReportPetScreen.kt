package com.petsit.app.screens.user
import androidx.compose.runtime.Composable
import com.petsit.app.ui.components.FormLayout
@Composable fun ReportPetScreen() = FormLayout("Reportar una mascota", listOf("Nombre", "Especie", "Zona donde se perdió", "Descripción"), "Publicar reporte")

