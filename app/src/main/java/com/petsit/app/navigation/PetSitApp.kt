package com.petsit.app.navigation
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.petsit.app.data.SampleData
import com.petsit.app.screens.admin.*
import com.petsit.app.screens.publicarea.*
import com.petsit.app.screens.refuge.*
import com.petsit.app.screens.user.*
import com.petsit.app.ui.components.PetSitBrandHeader
import com.petsit.app.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun PetSitApp() {
    var current by remember { mutableStateOf(AppDestination.HOME) }
    val history = remember { mutableStateListOf<AppDestination>() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    fun go(destination: AppDestination) {
        if (destination != current) {
            if (destination.area != current.area) history.clear() else history.add(current)
            current = destination
        }
    }
    fun back() {
        if (history.isNotEmpty()) current = history.removeAt(history.lastIndex)
    }
    BackHandler(enabled = history.isNotEmpty()) { back() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Color.White) {
                Column(Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).padding(16.dp)) {
                    PetSitBrandHeader(compact = true); Spacer(Modifier.height(12.dp))
                    Text(
                        current.area.label.uppercase(),
                        color = PetSitBrand,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 14.dp, bottom = 6.dp)
                    )
                    drawerDestinations(current.area).forEach { destination ->
                        NavigationDrawerItem(
                            label = { Text(destination.title) }, selected = destination == current,
                            onClick = { go(destination); scope.launch { drawerState.close() } },
                            colors = NavigationDrawerItemDefaults.colors(selectedContainerColor = PetSitBrandSoft, selectedTextColor = PetSitBrandDark)
                        )
                    }
                    if (current.area != AppArea.PUBLIC) {
                        HorizontalDivider(Modifier.padding(vertical = 12.dp), color = PetSitBrandSoft)
                        NavigationDrawerItem(
                            label = { Text("Cerrar sesión", color = Color(0xFFB42318), fontWeight = FontWeight.Bold) },
                            selected = false,
                            onClick = { go(AppDestination.LOGIN); scope.launch { drawerState.close() } }
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            containerColor = PetSitBackground,
            topBar = {
                TopAppBar(
                    title = { PetSitBrandHeader(compact = true) },
                    navigationIcon = {
                        if (history.isNotEmpty()) TextButton(onClick = ::back) { Text("‹", color = PetSitBrand, fontSize = 36.sp) }
                        else TextButton(onClick = { scope.launch { drawerState.open() } }) { Text("☰", color = PetSitBrand, fontSize = 25.sp) }
                    },
                    actions = {
                        if (history.isNotEmpty()) TextButton(onClick = { scope.launch { drawerState.open() } }) { Text("☰", color = PetSitBrand, fontSize = 23.sp) }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            },
            bottomBar = {
                if (current.area == AppArea.USER) UserBottomBar(current, ::go)
                if (current.area == AppArea.REFUGE) RefugeBottomBar(current, ::go)
            }
        ) { padding ->
            Box(Modifier.padding(padding).fillMaxSize()) {
                DestinationContent(current, ::go)
            }
        }
    }
}

private fun drawerDestinations(area: AppArea): List<AppDestination> = when (area) {
    AppArea.PUBLIC -> listOf(
        AppDestination.HOME,
        AppDestination.ABOUT,
        AppDestination.CONTACT,
        AppDestination.SHELTERS,
        AppDestination.VETS,
        AppDestination.MAP,
        AppDestination.LOST_PETS,
        AppDestination.LOGIN
    )
    AppArea.USER -> listOf(
        AppDestination.USER_HOME,
        AppDestination.MY_PETS,
        AppDestination.USER_VETS,
        AppDestination.USER_SHELTERS,
        AppDestination.ADOPTION,
        AppDestination.USER_MAP,
        AppDestination.REQUESTS,
        AppDestination.PROFILE
    )
    AppArea.REFUGE -> listOf(
        AppDestination.REFUGE_HOME,
        AppDestination.REFUGE_PETS,
        AppDestination.REFUGE_REQUESTS,
        AppDestination.REFUGE_ADOPTIONS,
        AppDestination.REFUGE_PROFILE
    )
    AppArea.ADMIN -> listOf(
        AppDestination.ADMIN_HOME,
        AppDestination.ACTIVITY,
        AppDestination.STATISTICS,
        AppDestination.MODERATION,
        AppDestination.PENDING,
        AppDestination.USERS,
        AppDestination.ADMIN_VETS,
        AppDestination.ADMIN_SHELTERS,
        AppDestination.REPORTS,
        AppDestination.SETTINGS
    )
}

@Composable private fun RefugeBottomBar(current: AppDestination, go: (AppDestination) -> Unit) {
    val items = listOf(
        AppDestination.REFUGE_HOME to "Inicio",
        AppDestination.REFUGE_PETS to "Mascotas",
        AppDestination.REFUGE_REQUESTS to "Solicitudes",
        AppDestination.REFUGE_PROFILE to "Perfil"
    )
    NavigationBar(containerColor = Color.White) {
        items.forEachIndexed { index, pair ->
            NavigationBarItem(
                selected = current == pair.first, onClick = { go(pair.first) },
                icon = { Text(listOf("⌂", "♡", "▣", "◉")[index], fontSize = 20.sp) },
                label = { Text(pair.second, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(indicatorColor = PetSitBrandSoft, selectedIconColor = PetSitBrand)
            )
        }
    }
}

@Composable private fun UserBottomBar(current: AppDestination, go: (AppDestination) -> Unit) {
    val items = listOf(AppDestination.USER_HOME to "Inicio", AppDestination.MY_PETS to "Mascotas", AppDestination.USER_MAP to "Mapa", AppDestination.PROFILE to "Perfil")
    NavigationBar(containerColor = Color.White) {
        items.forEachIndexed { index, pair ->
            NavigationBarItem(selected = current == pair.first, onClick = { go(pair.first) },
                icon = { Text(listOf("⌂","●","⌖","◉")[index], fontSize = 20.sp) },
                label = { Text(pair.second, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(indicatorColor = PetSitBrandSoft, selectedIconColor = PetSitBrand))
        }
    }
}

@Composable private fun DestinationContent(destination: AppDestination, go: (AppDestination) -> Unit) {
    when (destination) {
        AppDestination.HOME -> HomeScreen { go(AppDestination.REGISTER) }
        AppDestination.LOGIN -> LoginScreen(
            onLogin = { go(AppDestination.USER_HOME) },
            onRegister = { go(AppDestination.REGISTER) },
            onRecovery = { go(AppDestination.RECOVERY) },
            onShelterLogin = { go(AppDestination.SHELTER_LOGIN) }
        )
        AppDestination.SHELTER_LOGIN -> ShelterLoginScreen { go(AppDestination.REFUGE_HOME) }
        AppDestination.REGISTER -> RegisterScreen { go(AppDestination.LOGIN) }
        AppDestination.RECOVERY -> RecoveryScreen()
        AppDestination.LOST_PETS -> LostPetsScreen()
        AppDestination.MAP, AppDestination.USER_MAP -> MapScreen()
        AppDestination.VETS, AppDestination.USER_VETS -> VeterinariansScreen()
        AppDestination.SHELTERS, AppDestination.USER_SHELTERS -> SheltersScreen()
        AppDestination.ABOUT -> AboutScreen()
        AppDestination.CONTACT -> ContactScreen()
        AppDestination.USER_HOME -> UserHomeScreen()
        AppDestination.MY_PETS -> MyPetsScreen()
        AppDestination.PET_DETAIL -> PetDetailScreen()
        AppDestination.REPORT_PET -> ReportPetScreen()
        AppDestination.ADOPTION -> AdoptionScreen()
        AppDestination.REQUESTS -> RequestsScreen { go(AppDestination.USER_SHELTERS) }
        AppDestination.PROFILE -> ProfileScreen(
            onMyPets = { go(AppDestination.MY_PETS) },
            onRequests = { go(AppDestination.REQUESTS) },
            onLogout = { go(AppDestination.LOGIN) }
        )
        AppDestination.REFUGE_HOME -> RefugeHomeScreen(
            onPets = { go(AppDestination.REFUGE_PETS) },
            onRequests = { go(AppDestination.REFUGE_REQUESTS) },
            onAdoptions = { go(AppDestination.REFUGE_ADOPTIONS) }
        )
        AppDestination.REFUGE_PETS -> RefugePetsScreen()
        AppDestination.REFUGE_REQUESTS -> RefugeRequestsScreen()
        AppDestination.REFUGE_ADOPTIONS -> RefugeAdoptionsScreen()
        AppDestination.REFUGE_PROFILE -> RefugeProfileScreen(onLogout = { go(AppDestination.SHELTER_LOGIN) })
        AppDestination.ADMIN_HOME -> AdminDashboardScreen()
        AppDestination.STATISTICS -> AdminStatisticsScreen()
        AppDestination.SETTINGS -> AdminSettingsScreen()
        else -> AdminRecordsScreen(destination.title)
    }
}
