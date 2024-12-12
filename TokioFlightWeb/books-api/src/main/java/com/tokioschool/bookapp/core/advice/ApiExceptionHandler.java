package com.tokioschool.bookapp.core.advice;

import com.tokioschool.bookapp.core.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, String> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        return Map.of("message", ex.getMessage(),"request",request.getRequestURI());
    }
    /**
     * Endpoint para caso de error en el objeto de validacion en el metodo post
     * y no tiene el BindingResutl en el argumento del metodo handler
     *
     * @param ex
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message =
                ex.getBindingResult().getFieldErrors()
                        .stream()
                        .map(error -> error.getField()+": "+error.getDefaultMessage())
                        .collect(Collectors.joining(". "));
        return Map.of("message", message);
    }


    /**
     * Endpoint para caso de error en el objeto de validacion en el metodo get
     * o validacion en el metodo post y tiene el BindingResutl en el argumento del metodo handler
     *
     * @param ex
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        return Map.of("message", ex.getMessage(),"request",request.getRequestURI());
    }
}
