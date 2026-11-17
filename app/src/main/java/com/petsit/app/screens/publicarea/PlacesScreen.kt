package com.petsit.app.screens.publicarea
import androidx.compose.runtime.Composable
import com.petsit.app.model.PlaceUi
import com.petsit.app.ui.components.*
@Composable fun PlacesScreen(title: String, subtitle: String, places: List<PlaceUi>) = PetSitPage {
    ScreenHero(title, subtitle); StaticField("Buscar por nombre o localidad")
    FilterChips(listOf("Cerca mío", "Abierto ahora", "24 horas"))
    places.forEach { PlaceCard(it) }
}

