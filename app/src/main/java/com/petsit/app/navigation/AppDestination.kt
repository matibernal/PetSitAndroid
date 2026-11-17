package com.petsit.app.navigation
enum class AppArea(val label: String) { PUBLIC("Público"), USER("Usuario"), REFUGE("Refugio"), ADMIN("Administración") }
enum class AppDestination(val title: String, val area: AppArea) {
    HOME("Inicio", AppArea.PUBLIC), LOGIN("Iniciar sesión", AppArea.PUBLIC), REGISTER("Registrarse", AppArea.PUBLIC),
    SHELTER_LOGIN("Acceso de refugios", AppArea.PUBLIC),
    RECOVERY("Recuperar contraseña", AppArea.PUBLIC), LOST_PETS("Mascotas perdidas", AppArea.PUBLIC),
    MAP("Mapa", AppArea.PUBLIC), VETS("Veterinarias", AppArea.PUBLIC), SHELTERS("Refugios", AppArea.PUBLIC),
    ABOUT("Quiénes somos", AppArea.PUBLIC), CONTACT("Contacto", AppArea.PUBLIC),
    USER_HOME("Panel de usuario", AppArea.USER), MY_PETS("Mis mascotas", AppArea.USER),
    PET_DETAIL("Detalle de mascota", AppArea.USER), REPORT_PET("Reportar mascota", AppArea.USER),
    ADOPTION("Adoptar", AppArea.USER), USER_MAP("Mapa del usuario", AppArea.USER),
    USER_VETS("Veterinarias del usuario", AppArea.USER), USER_SHELTERS("Refugios del usuario", AppArea.USER),
    REQUESTS("Mis solicitudes", AppArea.USER), PROFILE("Mi perfil", AppArea.USER),
    REFUGE_HOME("Panel del refugio", AppArea.REFUGE), REFUGE_PETS("Mascotas", AppArea.REFUGE),
    REFUGE_REQUESTS("Solicitudes", AppArea.REFUGE), REFUGE_ADOPTIONS("Adopciones", AppArea.REFUGE),
    REFUGE_PROFILE("Perfil del refugio", AppArea.REFUGE),
    ADMIN_HOME("Panel administrativo", AppArea.ADMIN), ACTIVITY("Actividad", AppArea.ADMIN),
    STATISTICS("Estadísticas", AppArea.ADMIN), MODERATION("Moderación", AppArea.ADMIN),
    PENDING("Pendientes", AppArea.ADMIN), USERS("Usuarios", AppArea.ADMIN),
    ADMIN_VETS("Veterinarias admin", AppArea.ADMIN), ADMIN_SHELTERS("Refugios admin", AppArea.ADMIN),
    REPORTS("Reportes", AppArea.ADMIN), SETTINGS("Configuración", AppArea.ADMIN)
}
