package ua.com.kalinichev.microservices.lab1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleInvalidObjectsExceptions(MethodArgumentNotValidException e){
        Map<String,String> map = new HashMap<>();
        map.put("success","false");
        StringBuilder s = new StringBuilder();
        for(FieldError error: e.getFieldErrors()){
            s.append(error.getDefaultMessage()).append('\n');
        }
        map.put("error",s.toString());
        return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleInvalidParamsExceptions(ConstraintViolationException e){
        Map<String,String> map = new HashMap<>();
        map.put("success","false");
        StringBuilder s = new StringBuilder();
        for(ConstraintViolation<?> violation: e.getConstraintViolations()){
            s.append(violation.getMessage()).append('\n');
        }
        map.put("error",s.toString());
        return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Map<String, String> handleException(NoSuchElementException ex){
        Map<String, String> result = new HashMap<>();
        result.put("error", ex.getMessage());
        return result;
    }

    @ExceptionHandler(value = {IncorrectRequestBodyException.class, InvalidArgumentsException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleIncorrectRequestBodyAndInvalidArgumentsException(RuntimeException e){
        Map<String,String> map = new HashMap<>();
        map.put("success","false");
        map.put("error",e.getMessage());
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ElementExistsException.class})
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity<Map<String,String>> handleIncorrectRequestBodyAndInvalidArgumentsException(ElementExistsException e){
        Map<String,String> map = new HashMap<>();
        map.put("success","false");
        map.put("error",e.getMessage());
        return new ResponseEntity<>(map,HttpStatus.CONFLICT);
    }

}
