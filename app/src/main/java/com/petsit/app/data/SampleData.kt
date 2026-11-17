package com.petsit.app.data
import ar.edu.davinci.petsit.mobile.R
import com.petsit.app.model.*
object SampleData {
    val myPets = listOf(
        PetUi("Luna", "Siamés · 9 años · Hembra", "ACTIVA", R.drawable.my_pet_luna, "Siamés", "9 años", "Hembra", "Ojos azules y collar celeste"),
        PetUi("Toby", "Golden Retriever · 10 meses · Macho", "ACTIVA", R.drawable.my_pet_toby, "Golden Retriever", "10 meses", "Macho", "Collar marrón"),
        PetUi("Mora", "Caniche · 2 años · Hembra", "ACTIVA", R.drawable.my_pet_mora, "Caniche", "2 años", "Hembra", "Pelaje blanco"),
        PetUi("Nina", "Mestiza · 3 años · Hembra", "ADOPTADA", R.drawable.my_pet_nina, "Mestiza", "3 años", "Hembra", "Mancha blanca en el pecho")
    )
    val adoptionPets = listOf(
        PetUi("Luna", "Cachorra · Luján", "EN ADOPCIÓN", R.drawable.adopt_one),
        PetUi("Simón", "2 años · Moreno", "EN ADOPCIÓN", R.drawable.adopt_two),
        PetUi("Olivia", "1 año · Pilar", "EN ADOPCIÓN", R.drawable.adopt_three)
    )
    val lostPets = listOf(
        PetUi("Bruno", "Visto en zona céntrica", "SE BUSCA", R.drawable.lost_one),
        PetUi("Koko", "Visto cerca de la plaza", "SE BUSCA", R.drawable.lost_two),
        PetUi("Nube", "Visto en el barrio norte", "SE BUSCA", R.drawable.lost_three)
    )
    val vets = listOf("Del Parque", "San Martín", "Pet Care", "Central", "Los Tilos")
        .mapIndexed { index, name -> PlaceUi("Veterinaria " + name, "A " + (index + 1) + "," + (index + 2) + " km · Buenos Aires") }
    val shelters = listOf("Vida Animal", "Arca de Peludos", "América", "Friends", "Petland")
        .mapIndexed { index, name -> PlaceUi("Refugio " + name, "A " + (index + 1) + "," + (index + 2) + " km · Buenos Aires") }
    val requests = listOf(
        RequestUi("Solicitud de adopción · Luna", "EN REVISIÓN", "Actualizado hoy"),
        RequestUi("Registro de mascota · Milo", "APROBADA", "Actualizado ayer"),
        RequestUi("Contacto con refugio", "PENDIENTE", "Actualizado recientemente")
    )
    val adminMetrics = listOf(
        AdminMetricUi("1.248", "Usuarios"), AdminMetricUi("86", "Reportes"),
        AdminMetricUi("42", "Pendientes"), AdminMetricUi("73%", "Resueltos")
    )
    val sheltersDetailed = listOf(
        ShelterUi("Lost & Found", "Villa Devoto", "Av. San Martín 7450", "+54 11 4501-2210", "Lun a Vie: 10–18 hs", R.drawable.shelter_arca_peludos),
        ShelterUi("Animal Resource Center", "Caballito", "Av. Rivadavia 5100", "+54 11 4902-8841", "Lun a Sáb: 9–18 hs", R.drawable.shelter_cielo_mascotas),
        ShelterUi("America Dog & Cat Hotel", "Colegiales", "Av. Elcano 3320", "+54 11 4555-1978", "Todos los días: 9–20 hs", R.drawable.shelter_america),
        ShelterUi("Animal Care", "Belgrano", "Av. Cabildo 2200", "+54 11 4781-3460", "Lun a Vie: 9–19 hs", R.drawable.shelter_animal_care),
        ShelterUi("Friends", "Recoleta", "Juncal 1850", "+54 11 4812-5520", "Lun a Sáb: 10–18 hs", R.drawable.shelter_friends),
        ShelterUi("Greenville", "Caballito", "José M. Moreno 740", "+54 11 4922-1144", "Lun a Vie: 10–19 hs", R.drawable.shelter_greenville),
        ShelterUi("Petland", "Palermo", "Av. Santa Fe 3950", "+54 11 4832-6755", "Todos los días: 10–20 hs", R.drawable.shelter_petland),
        ShelterUi("The Pets Hotel", "Villa Urquiza", "Av. Triunvirato 4400", "+54 11 4523-7821", "Todos los días: 8–21 hs", R.drawable.shelter_pets_hotel),
        ShelterUi("Vida Animal", "Belgrano", "Virrey del Pino 2750", "+54 11 4781-5566", "Lun a Vie: 10–18 hs · Sáb: 10–14 hs", R.drawable.shelter_vida_animal)
    )
    val veterinariansDetailed = listOf(
        VeterinarianUi("BlackDog", "Almagro", "Corrientes 3560", "+54 11 4777-1212", "Lun a Sáb 9–21hs", true, false, R.drawable.vet_blackdog),
        VeterinarianUi("Clínica Veterinaria", "Belgrano", "Moldes 2345", "+54 11 4555-1111", "Lun a Dom 24hs", true, true, R.drawable.vet_clinica),
        VeterinarianUi("Galia Vet", "Recoleta", "Azcuénaga 1450", "+54 11 4511-9090", "Lun a Dom 24hs", false, true, R.drawable.vet_galia),
        VeterinarianUi("Hub City", "Palermo", "Honduras 4901", "+54 11 4899-2200", "Lun a Vie 10–20hs · Sáb 10–14hs", true, false, R.drawable.vet_hub_city),
        VeterinarianUi("Love Pets", "Palermo", "Av. Santa Fe 123", "+54 11 4000-7777", "Lun a Dom 8–22hs", true, false, R.drawable.vet_love_pets),
        VeterinarianUi("Master-Animal", "Villa Urquiza", "Av. Monroe 5200", "+54 11 4521-7788", "Lun a Sáb 9–20hs", true, false, R.drawable.vet_master_animal),
        VeterinarianUi("Patitas", "Caballito", "Hidalgo 987", "+54 11 4321-3333", "Lun a Sáb 9–20hs", false, true, R.drawable.vet_patitas),
        VeterinarianUi("Pet World", "Saavedra", "Balbín 4700", "+54 11 4701-3344", "Lun a Dom 8–23hs", false, true, R.drawable.vet_pet_world)
    )
    val adoptionPetsDetailed = listOf(
        AdoptionPetUi("Luna", "Gato", "Villa Devoto", "Tranquila y mimosera. Usa arenero.", "Lost & Found", "06/09/2025", "10 meses", "3.6 kg", 2, 5, 3, 5, 5, R.drawable.adopt_cat_luna),
        AdoptionPetUi("Mishi", "Gato", "Belgrano", "Castrado. Sociable con visitas.", "Animal Care", "05/09/2025", "2 años", "4.4 kg", 4, 4, 2, 5, 5, R.drawable.adopt_cat_mishi),
        AdoptionPetUi("Simba", "Gato", "Colegiales", "Curioso, activo. Le gustan los juguetes.", "America Dog & Cat Hotel", "04/09/2025", "1 año", "4.0 kg", 5, 3, 3, 4, 5, R.drawable.adopt_cat_simba),
        AdoptionPetUi("Copito", "Gato", "Recoleta", "Pelo largo. Cepillado recomendado.", "Friends", "03/09/2025", "3 años", "4.8 kg", 2, 4, 3, 5, 5, R.drawable.adopt_cat_copito),
        AdoptionPetUi("Toby", "Perro", "Palermo", "Ideal para una familia. Aprende rápido.", "Petland", "02/09/2025", "3 años", "18 kg", 4, 5, 4, 3, 4, R.drawable.adopt_dog_toby),
        AdoptionPetUi("Greta", "Perro", "Colegiales", "Tranquila, buen manejo con correa.", "America Dog & Cat Hotel", "02/09/2025", "5 años", "22 kg", 2, 4, 3, 3, 4, R.drawable.adopt_dog_greta),
        AdoptionPetUi("Duna", "Perro", "Caballito", "Energía moderada. Se adapta rápido.", "Greenville", "01/09/2025", "2 años", "12 kg", 3, 4, 3, 5, 3, R.drawable.adopt_dog_duna),
        AdoptionPetUi("Rocky", "Perro", "Villa Urquiza", "Cariñoso con niños. Tamaño grande.", "The Pets Hotel", "01/09/2025", "4 años", "32 kg", 4, 5, 3, 2, 3, R.drawable.adopt_dog_rocky),
        AdoptionPetUi("Bruno", "Perro", "Belgrano", "Dormilón y simpático. Tamaño mediano.", "Vida Animal", "31/08/2025", "6 años", "20 kg", 1, 4, 3, 4, 3, R.drawable.adopt_dog_bruno),
        AdoptionPetUi("Max", "Perro", "Caballito", "Ágil y obediente. Le gustan los paseos.", "Greenville", "30/08/2025", "1 año", "14 kg", 5, 4, 4, 3, 4, R.drawable.adopt_dog_max)
    )
    val adoptionRequestsDetailed = listOf(
        AdoptionRequestUi("Nina", "Lost & Found", "ADOPTADA", "28/01/2026", "01/02/2026", "¡Felicitaciones! La adopción fue aprobada y finalizada.", R.drawable.my_pet_nina),
        AdoptionRequestUi("Rocky", "The Pets Hotel", "PENDIENTE", "12/02/2026", message = "Estamos revisando tu solicitud. Si necesitamos más información, te contactamos por mail.", imageRes = R.drawable.adopt_dog_rocky)
    )
}
