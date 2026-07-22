# Notas técnicas (leer antes de conectar el backend)

Esta versión de la base ya está construida contra el **código real** de tu
backend (lo leí directo de https://github.com/AgustinS2/PetSit, no del
HTML del prototipo como en la primera versión). Aun así hay pasos manuales
que tenés que hacer vos, y algunas decisiones que tomé y que conviene que
revisen entre los cuatro.

## 1. Por qué hace falta agregar controllers nuevos

Encontré `controller/PetSitRest.java`:

```java
@RestController
@RequestMapping("/petsit/api")
public abstract class PetSitRest {
}
```

Está vacío — es una clase base, no expone nada. O sea que **tu backend hoy
no tiene ninguna API REST real**, solo controllers Thymeleaf que devuelven
vistas HTML (`MascotaController`, `UsuarioController`, etc.), pensados para
que los use un browser con sesión, no una app.

Por eso armé `backend-para-tu-proyecto/`, con controllers REST nuevos que
**reutilizan tus services existentes** (`UsuarioService`, `MascotaService`,
`RefugioService`, `VeterinariaService` — no toqué la lógica de negocio, ni
tus repositorios, ni tus entidades). Nombres de endpoint elegidos:

| Método | Ruta                          | Qué hace                                            | Auth |
|--------|-------------------------------|------------------------------------------------------|------|
| POST   | `/petsit/api/auth/login`      | Login (ver punto 2)                                  | público |
| GET    | `/petsit/api/usuarios/me`     | Usuario logueado actual                              | sesión |
| POST   | `/petsit/api/usuarios/registro` | Alta de usuario, siempre tipo `DUENO`              | público |
| GET    | `/petsit/api/mascotas/mias`   | Mascotas del usuario logueado                        | sesión |
| GET    | `/petsit/api/mascotas/{id}`   | Detalle de una mascota                               | sesión |
| POST   | `/petsit/api/mascotas`        | Alta de mascota (dueño = usuario logueado)           | sesión |
| GET    | `/petsit/api/refugios`        | Listado de refugios **aprobados** (`listAprobadas()`)| público |
| GET    | `/petsit/api/refugios/{id}`   | Detalle de un refugio                                | público |
| GET    | `/petsit/api/veterinarias`    | Listado de veterinarias **aprobadas**                | público |
| GET    | `/petsit/api/veterinarias/{id}` | Detalle de una veterinaria                         | público |

Todos devuelven JSON plano (a través de DTOs, ver punto 3), nunca HAL ni
las entidades JPA directamente.

## 2. Cómo funciona el login (y por qué no es tan simple como parece)

Tu `SecurityConfig.java` usa `formLogin()` con `loginProcessingUrl("/login")`
— pensado para un `<form method="post">` de Thymeleaf que hace un redirect
después de loguear. Eso no sirve para un cliente que quiere mandar JSON y
recibir JSON de vuelta (200 con el usuario, o 401 si está mal).

`ApiAuthController.login()` hace el login **a mano**:

1. Arma un `UsernamePasswordAuthenticationToken` con correo/contraseña.
2. Se lo pasa al `AuthenticationManager` (que usa tu
   `CustomUserDetailsService` + `BCryptPasswordEncoder`, sin tocarlos).
3. Si la autenticación es válida, guarda el resultado en
   `SecurityContextHolder` y lo persiste en la sesión HTTP con
   `HttpSessionSecurityContextRepository` — esto es lo que deja la cookie
   `JSESSIONID` "logueada" para los próximos requests.
4. Devuelve el `Usuario` (como DTO) con 200, o 401 si falla.

Para que esto funcione hace falta un bean `AuthenticationManager`, que tu
`SecurityConfig` original no exponía (Spring Security lo arma internamente
para el `formLogin()`, pero no lo publica como `@Bean` inyectable). Por eso
el `SecurityConfig.java` de `backend-para-tu-proyecto/` agrega:

```java
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
}
```

**La app depende de que OkHttp guarde la cookie `JSESSIONID`** que el
servidor manda en la respuesta del login, y la reenvíe en cada request
siguiente. Retrofit/OkHttp no hacen esto solos — por eso
`app/.../util/PersistentCookieJar.java` implementa `CookieJar` y además la
guarda en `SharedPreferences` para que sobreviva a que cierres la app.

## 3. CSRF y por qué agregué DTOs

- **CSRF:** tu `SecurityConfig` original solo eximía `/petsit/api/mapa/**`
  de CSRF. La app Android no manda token CSRF (no tiene forma fácil de
  conseguirlo sin una vista Thymeleaf), así que el `SecurityConfig.java` de
  reemplazo agrega `/petsit/api/**` a `csrf().ignoringRequestMatchers(...)`.
  Esto es razonable porque esos endpoints ya están protegidos por sesión +
  CORS/origen del emulador, pero si más adelante exponen esta API a un
  frontend web público, revisen si conviene volver a exigir CSRF ahí.

- **DTOs (`dto/UsuarioDTO.java`, etc.):** si devolviera tus entidades JPA
  directamente con Jackson, se rompería por la relación bidireccional
  `Usuario.mascotas` ↔ `Mascota.dueno` (recursión infinita al serializar), y
  además expondría `Usuario.contrasena` (el hash bcrypt) en el JSON. Los
  DTOs evitan ambos problemas — cada uno tiene un constructor que recibe la
  entidad y copia solo los campos que la app necesita.

## 4. ⚠️ Ojo con la contraseña: no la encripten dos veces

`UsuarioServiceImpl.save()` **ya encripta la contraseña con
`BCryptPasswordEncoder`** antes de guardar (lo confirmé leyendo el código).
Por eso `ApiUsuarioController.registro()` manda la contraseña **en texto
plano** al `Usuario` que arma, y deja que `save()` la encripte — si la
encriptan también en el controller, quedaría doble-encriptada y el login
después iba a fallar (bcrypt de un hash bcrypt no matchea con el password
real). Si en algún momento cambian `UsuarioServiceImpl`, revisen este
punto.

## 5. Campos que quedaron afuera a propósito (MVP)

La entidad real `Mascota` tiene `edad` (Integer) y `fechaNacimiento`
(LocalDate) que **no** están en `model/Mascota.java` (Android) ni en
`MascotaDTO`. Building rápido para no explotar el alcance — si los
necesitan:

1. Agregar `edad`/`fechaNacimiento` a `dto/MascotaDTO.java` (constructor +
   getters/setters) en el backend.
2. Agregar los mismos campos a `app/.../model/Mascota.java` (Android), con
   el mismo nombre exacto (Gson mapea por nombre de campo).
3. Si quieren que se puedan cargar desde el form de "Registrar mascota",
   agregar los inputs en `activity_agregar_mascota.xml` y leerlos en
   `AgregarMascotaActivity.java`.

Mismo caso con `Refugio.activa` / `Refugio.estadoAprobacion` y
`Veterinaria.activa` / `Veterinaria.estadoAprobacion`: no los expuse porque
los endpoints públicos (`/petsit/api/refugios`, `/petsit/api/veterinarias`)
ya filtran por `listAprobadas()` en el service, así que la app solo ve
refugios/veterinarias aprobados y no necesita mostrar el estado. Si más
adelante quieren una pantalla de admin en la app para aprobar/rechazar,
ahí sí van a necesitar esos campos.

## 6. Limitación conocida: sesión expirada

Si la sesión del servidor expira (timeout de Spring Session) mientras la
app sigue "logueada" localmente (`SessionManager.estaLogueado()` sigue en
`true` porque es solo una caché local), un request a un endpoint protegido
va a devolver el login HTML de Thymeleaf en vez de JSON — Retrofit/Gson va
a tirar una excepción de parseo, y el usuario va a ver el mensaje de error
genérico en vez de "por favor iniciá sesión de nuevo". Quedó así por
tiempo; si quieren, se puede agregar un `Interceptor` de OkHttp que
detecte una respuesta no-JSON o un 302 al login y fuerce
`SessionManager.cerrarSesion()` + volver a `LoginActivity`.

## 7. Pantallas no incluidas en esta base

Para no hacer la primera entrega interminable, dejé afuera: Adoptar,
Mapa/Perdidos, Solicitudes, Notificaciones, QR de mascota y reportar
mascota perdida. El backend real ya tiene las entidades y repositorios
(`Adopcion`, `Alerta`, `Reporte`, `Postulacion`, con sus
`*Repository.java`), así que agregar cada pantalla es: DTO + controller
REST (mismo patrón que `ApiMascotaController`) + Fragment/Activity +
Adapter en Android, reusando `ApiClient`/`ApiService` como ya está armado.
