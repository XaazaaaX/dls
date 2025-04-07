package de.dlsa.api.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Klasse zur Behandlung von global auftretenden Exceptions
 *
 * @author Benito Ernst
 * @version  01/2024
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Behandelt ausgewählte Exceptions
     *
     * @param exception Geworfene Exception
     * @return Details über die Exception
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
            errorDetail.setProperty("description", "Der Benutzername wird bereits verwendet!");

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
            errorDetail.setProperty("description", "Der JWT ist abgelaufen!");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unbekannter interner Serverfehler!");
        }

        return errorDetail;
    }
}
