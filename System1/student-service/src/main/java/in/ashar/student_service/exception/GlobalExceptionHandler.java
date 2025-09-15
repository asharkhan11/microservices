package in.ashar.student_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> unAuthorizedExceptionHandler(UnAuthorizedException e){

        return new ResponseEntity<>(ErrorResponse.builder().code("UNAUTHORIZED").detail(e.getMessage()).build(), HttpStatus.FORBIDDEN);

    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException e){

        return new ResponseEntity<>(ErrorResponse.builder().code("NOT FOUND").detail(e.getMessage()).build(), HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> alreadyExistsExceptionHandler(AlreadyExistsException e){

        return new ResponseEntity<>(ErrorResponse.builder().code("DUPLICATE ENTRY").detail(e.getMessage()).build(), HttpStatus.CONFLICT);

    }

}
