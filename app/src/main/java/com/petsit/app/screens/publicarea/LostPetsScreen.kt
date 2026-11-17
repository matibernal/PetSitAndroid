package com.petsit.app.screens.publicarea
import androidx.compose.runtime.Composable
import com.petsit.app.data.SampleData
import com.petsit.app.ui.components.*
@Composable fun LostPetsScreen() = PetSitPage {
    ScreenHero("Mascotas perdidas", "Cada reporte compartido aumenta las posibilidades de volver a casa.")
    FilterChips(listOf("Todos", "Perros", "Gatos", "Recientes"))
    SampleData.lostPets.forEach { PetCard(it) }
}

