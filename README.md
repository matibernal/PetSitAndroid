## Qué incluye

- Login y registro de usuario, contra la sesión real de Spring Security
  (cookie `JSESSIONID`), usando dos endpoints REST nuevos que hay que sumar
  al backend (ver "Paso obligatorio" abajo).
- Home con navegación inferior (BottomNavigationView) entre 4 secciones,
  igual que el menú interno del sitio (`usuariomenu.html`):
  - **Mis mascotas**: listado + detalle + alta de mascota.
  - **Refugios**: listado (solo aprobados) + detalle con Llamar / Ver en Maps.
  - **Veterinarias**: listado (solo aprobadas) + detalle con Llamar / Ver en Maps.
  - **Perfil**: datos del usuario + cerrar sesión.
- Capa de red con Retrofit + Gson + OkHttp, con un `CookieJar` propio
  (`PersistentCookieJar`) que persiste la cookie de sesión en
  `SharedPreferences` — así no hace falta loguearse de nuevo cada vez que
  se reabre la app (mientras la sesión no expire en el servidor).
- Sesión simple guardada en SharedPreferences (`SessionManager`), solo
  como caché de nombre/correo para mostrar en pantalla — quien manda de
  verdad es la cookie de sesión.
