package ar.edu.davinci.PetSit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * ESTE ARCHIVO REEMPLAZA A src/main/java/ar/edu/davinci/PetSit/security/SecurityConfig.java
 * en el proyecto real. Los únicos cambios respecto al original son:
 *
 *   1. Se agrega "/petsit/api/**" a la lista de rutas exceptuadas de CSRF
 *      (la app Android no manda token CSRF).
 *   2. Se agregan a permitAll(): login/registro de la app y los listados
 *      públicos de refugios/veterinarias vía la nueva API JSON
 *      (/petsit/api/auth/login, /petsit/api/usuarios/registro,
 *      /petsit/api/refugios/**, /petsit/api/veterinarias/**).
 *      OJO: /petsit/api/mascotas/** y /petsit/api/usuarios/me NO se agregan
 *      a permitAll a propósito -> quedan protegidos por
 *      anyRequest().authenticated(), igual que antes.
 *   3. Se agrega el bean AuthenticationManager, que hace falta para poder
 *      autenticar manualmente desde ApiAuthController (antes no estaba
 *      expuesto como bean porque solo lo usaba el .formLogin() interno).
 *
 * Todo lo demás (formLogin, logout, rutas admin, etc.) queda igual.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/petsit/api/mapa/**",
                                "/petsit/api/**"
                        )
                )
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/petsit/admin/**").hasRole("ADMINISTRADOR")

                        .requestMatchers(
                                "/", "/petsit/", "/petsit/index",
                                "/petsit/home/login",
                                "/petsit/home/registro",
                                "/petsit/home/contacto",
                                "/petsit/home/nosotros",
                                "/petsit/home/recuperarpass",
                                "/petsit/usuarios/save",
                                // Registro público de vet y refugio
                                "/petsit/registro/veterinaria",
                                "/petsit/registro/veterinaria/save",
                                "/petsit/registro/veterinaria/gracias",
                                "/petsit/registro/refugio",
                                "/petsit/registro/refugio/save",
                                "/petsit/registro/refugio/gracias",
                                // Listados públicos
                                "/petsit/refugios/list",
                                "/petsit/refugios/index",
                                "/petsit/veterinarias/list",
                                "/petsit/veterinarias/index",
                                "/petsit/mascotas/list",
                                // API mapa pública
                                "/petsit/api/mapa/**",
                                // --- Nuevo: API JSON para la app Android ---
                                "/petsit/api/auth/login",
                                "/petsit/api/usuarios/registro",
                                "/petsit/api/refugios/**",
                                "/petsit/api/veterinarias/**",
                                // Recursos estáticos
                                "/css/**", "/js/**", "/images/**", "/assets/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/petsit/home/login")
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler)
                        .failureUrl("/petsit/home/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/petsit/home/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .permitAll()
                );

        return http.build();
    }
}
