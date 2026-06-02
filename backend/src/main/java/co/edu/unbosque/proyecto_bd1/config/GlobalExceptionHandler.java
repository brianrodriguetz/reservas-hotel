package co.edu.unbosque.proyecto_bd1.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(NoSuchElementException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Recurso no encontrado");
        error.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        List<FieldError> camposConError = ex.getBindingResult().getFieldErrors();
        for (int i = 0; i < camposConError.size(); i++) {
            FieldError campo = camposConError.get(i);
            errores.put(campo.getField(), campo.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarGeneral(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Error interno del servidor");
        error.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    @ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<Map<String, String>> manejarArgumentoInvalido(IllegalArgumentException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", "Argumento invalido");
    error.put("mensaje", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
}
}