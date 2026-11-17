package com.petsit.app.model
import androidx.annotation.DrawableRes
data class PetUi(
    val name: String,
    val detail: String,
    val status: String,
    @DrawableRes val imageRes: Int,
    val breed: String = "",
    val age: String = "",
    val sex: String = "",
    val marks: String = ""
)
data class PlaceUi(val name: String, val detail: String)
data class RequestUi(val title: String, val status: String, val updated: String)
data class AdminMetricUi(val value: String, val label: String)
data class ShelterUi(
    val name: String,
    val neighborhood: String,
    val address: String,
    val phone: String,
    val hours: String,
    @DrawableRes val imageRes: Int
)
data class VeterinarianUi(
    val name: String,
    val neighborhood: String,
    val address: String,
    val phone: String,
    val hours: String,
    val homeCare: Boolean,
    val emergency24h: Boolean,
    @DrawableRes val imageRes: Int
)
data class AdoptionPetUi(
    val name: String,
    val type: String,
    val neighborhood: String,
    val description: String,
    val shelter: String,
    val date: String,
    val age: String,
    val weight: String,
    val energy: Int,
    val people: Int,
    val otherPets: Int,
    val apartment: Int,
    val habits: Int,
    @DrawableRes val imageRes: Int
)
data class AdoptionRequestUi(
    val petName: String,
    val shelter: String,
    val status: String,
    val sentDate: String,
    val resolvedDate: String = "",
    val message: String,
    @DrawableRes val imageRes: Int
)
