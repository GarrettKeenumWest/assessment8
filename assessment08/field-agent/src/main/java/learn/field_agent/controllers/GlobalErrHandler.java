package learn.field_agent.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalErrHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex){
        Logger.getLogger("some-logger").log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity<>("Whoops Sorry, Not Sorry", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<String> handleMissingPathVariable(MissingPathVariableException ex){
        return new ResponseEntity<>("Missing PathVariable", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<String> handleBadSqlGrammar(BadSqlGrammarException ex){
        return new ResponseEntity<>("SQL Syntax Not Quite Right", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpError(HttpMessageNotReadableException ex){
        return new ResponseEntity<>("Error With JSON In HTTP", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleJdbcConnection(SQLException ex){
        return new ResponseEntity<>("Trouble Connecting...", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
