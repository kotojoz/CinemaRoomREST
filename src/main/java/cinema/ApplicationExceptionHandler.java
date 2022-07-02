package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(SoldOutException.class)
    public ResponseEntity<Map<String,String>> soldOutException(RuntimeException e){
        Map<String, String> errorResponse = Map.of("error","The ticket has been already purchased!");
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfBoundsException.class)
    public ResponseEntity<Map<String,String>> outOfBoundsException(RuntimeException e){
        Map<String, String> errorResponse = Map.of("error","The number of a row or a column is out of bounds!");
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadTokenException.class)
    public ResponseEntity<Map<String,String>> badToken(RuntimeException e){
        Map<String, String> errorResponse = Map.of("error","Wrong token!");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadPasswordException.class)
    public ResponseEntity<Map<String,String>> badPassword(RuntimeException e){
        Map<String, String> errorResponse = Map.of("error","The password is wrong!");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
