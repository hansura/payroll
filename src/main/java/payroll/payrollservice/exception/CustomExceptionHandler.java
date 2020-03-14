package payroll.payrollservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler  extends Exception {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<CustomNotFoundException> NotFoundException(CustomNotFoundException exception ,WebRequest webRequest ){
        ApplicationErrorMessage error  = new ApplicationErrorMessage();
        error.setStatusCode(404);
        error.setErrorMessage(exception.getMessage());

       return new ResponseEntity( error, HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomInternalServerErrorException.class)
    public ResponseEntity<CustomInternalServerErrorException> internalServerErrorException(CustomInternalServerErrorException exception ,WebRequest webRequest  ){
        ApplicationErrorMessage error  = new ApplicationErrorMessage();
        error.setStatusCode(500);
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity( error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomUnauthoirzedAccessException.class)
    public ResponseEntity<CustomUnauthoirzedAccessException> unAuthorizedException(CustomUnauthoirzedAccessException exception ,WebRequest webRequest  ){
        ApplicationErrorMessage error  = new ApplicationErrorMessage();
        error.setStatusCode(401);
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity( error, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ExceptionHandler(CustomNullException.class)
    public ResponseEntity<CustomNullException> nullException(CustomNullException exception  ,WebRequest webRequest){
        ApplicationErrorMessage error  = new ApplicationErrorMessage();
        error.setStatusCode(211);
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity( error, HttpStatus.NO_CONTENT);
    }




    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomBadRequestException.class )
    public ResponseEntity<CustomBadRequestException> nullException(CustomBadRequestException exception  ,WebRequest webRequest) {
        ApplicationErrorMessage error = new ApplicationErrorMessage();
        error.setStatusCode(400);
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpMessageNotReadableException> messageNotReadableException(CustomBadRequestException exception , WebRequest webRequest){
        ApplicationErrorMessage error  = new ApplicationErrorMessage();
        error.setStatusCode(400);
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity( error, HttpStatus.BAD_REQUEST);
    }



}
