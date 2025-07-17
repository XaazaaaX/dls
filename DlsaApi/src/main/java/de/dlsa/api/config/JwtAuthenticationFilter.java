package de.dlsa.api.config;

import de.dlsa.api.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filter zur Verarbeitung von JWTs, die im Authorization-Header eines HTTP-Requests mitgesendet werden.
 * Wird nur einmal pro Anfrage aufgerufen.
 *
 * Verantwortlich für:
 * - Extraktion des JWT-Tokens
 * - Validierung des Tokens
 * - Setzen des Authentifizierungs-Kontexts in Spring Security
 *
 * @author Benito Ernst
 * @version 01/2024
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Konstruktor zur Initialisierung des Filters mit benötigten Komponenten.
     *
     * @param jwtService Dienst zum Parsen und Validieren von JWT-Tokens
     * @param userDetailsService Spring-Service zur Benutzerabfrage
     * @param handlerExceptionResolver globaler Exception-Handler für Spring Security
     */
    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * Verarbeitet die eingehenden HTTP-Anfragen und prüft,
     * ob ein gültiger JWT vorhanden ist und ob der Benutzer authentifiziert werden kann.
     *
     * @param request eingehender HTTP-Request
     * @param response HTTP-Antwortobjekt
     * @param filterChain Filterkette zur Weitergabe der Anfrage
     * @throws ServletException wenn ein Servletfehler auftritt
     * @throws IOException bei I/O-Problemen
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // JWT aus dem Header extrahieren
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Kein gültiger Authorization-Header -> weiter mit der Filterkette
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // JWT-Token auslesen
            final String jwt = authHeader.substring(7); // "Bearer " entfernen
            final String username = jwtService.extractUsername(jwt);

            // Prüfen, ob Benutzer noch nicht authentifiziert wurde
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (username != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Rollen aus dem Token extrahieren
                List<String> roles = new ArrayList<>();
                roles.add(jwtService.extractRoles(jwt)); // Achtung: extrahiert nur eine Rolle!

                // In Spring-Security-kompatible Autoritäten umwandeln
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // Token validieren und Benutzer authentifizieren
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities // Alternativ: userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Weiter mit der Filterkette
            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            // Fehler an globalen Exception-Handler weitergeben
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
