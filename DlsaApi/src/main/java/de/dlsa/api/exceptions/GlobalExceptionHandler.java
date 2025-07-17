package de.dlsa.api.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Globale Fehlerbehandlung für die gesamte REST-API.
 * Diese Klasse fängt definierte Exceptiontypen ab und liefert passende HTTP-Antworten im ProblemDetail-Format.
 *
 * @author Benito Ernst
 * @version 01/2024
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Universeller Fallback für sicherheits- und datenbankbezogene Fehler.
     * Gibt eine ProblemDetail-Antwort mit passendem HTTP-Status und Beschreibung zurück.
     *
     * @param exception Die aufgetretene Ausnahme
     * @return Objekt mit strukturierten Fehlerdetails
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail = null;

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "Der Benutzername oder das Passwort ist falsch!");
            return errorDetail;
        }

        if (exception instanceof DataIntegrityViolationException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(409), exception.getMessage());
            errorDetail.setProperty("description", "Diese Bezeichnung wird bereits verwendet!");
            return errorDetail;
        }

        if (exception instanceof AccountStatusException || exception instanceof UserDeactivatedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Das Konto ist gesperrt!");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Sie sind nicht berechtigt, auf diese Ressource zuzugreifen!");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Der JWT ist ungültig!");
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Der JWT ist abgelaufen! Bitte melden Sie sich erneut an!");
        }

        // Fallback bei allen anderen nicht behandelten Fehlern
        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unbekannter interner Serverfehler!");
        }

        return errorDetail;
    }

    /**
     * Behandlung für Validierungsfehler von JWT.
     * Gibt eine zusammengefasste Liste aller Validierungsfehler als HTTP 400 zurück.
     *
     * @param exception Ausnahme mit Feldfehlern
     * @return ProblemDetail mit Liste der Fehlerbeschreibungen
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String errorDescriptions = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), "Validierungsfehler");
        errorDetail.setProperty("description", errorDescriptions);

        return errorDetail;
    }
}
