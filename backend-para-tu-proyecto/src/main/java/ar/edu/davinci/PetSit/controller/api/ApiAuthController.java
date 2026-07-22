package ar.edu.davinci.PetSit.controller.api;

import ar.edu.davinci.PetSit.domain.Usuario;
import ar.edu.davinci.PetSit.dto.LoginRequestDTO;
import ar.edu.davinci.PetSit.dto.UsuarioDTO;
import ar.edu.davinci.PetSit.service.Usuario.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login pensado para la app Android: recibe y devuelve JSON (a diferencia
 * del /login de siempre, que espera un form-post y redirige a una vista
 * Thymeleaf). Autentica manualmente contra el AuthenticationManager y
 * persiste el resultado en la sesión HTTP (cookie JSESSIONID), así el
 * resto de los endpoints /petsit/api/** siguen funcionando con las mismas
 * reglas de sesión que ya usa el sitio web (ver SecurityConfig).
 *
 * Requiere el bean AuthenticationManager agregado en SecurityConfig.java
 * de este mismo paquete de ejemplo.
 */
@RestController
@RequestMapping("/petsit/api/auth")
public class ApiAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        if (body.getCorreo() == null || body.getContrasena() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            Authentication authRequest =
                    new UsernamePasswordAuthenticationToken(body.getCorreo(), body.getContrasena());
            Authentication authResult = authenticationManager.authenticate(authRequest);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authResult);
            SecurityContextHolder.setContext(context);
            // Esto es lo que deja la sesión "logueada" para los próximos
            // requests con la misma cookie JSESSIONID.
            securityContextRepository.saveContext(context, request, response);

            Usuario usuario = usuarioService.findByCorreo(body.getCorreo());
            return ResponseEntity.ok(new UsuarioDTO(usuario));
        } catch (Exception e) {
            // Credenciales inválidas o usuario inexistente -> 401 genérico
            // (no distinguimos para no filtrar qué correos existen).
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
