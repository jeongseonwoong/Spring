package JWT.JWT_prac.controller;

import JWT.JWT_prac.base.ErrorCode;
import JWT.JWT_prac.base.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.security.sasl.AuthenticationException;

@ControllerAdvice //DispatcherServlet 이후, 컨트롤러 처리 단계에서만 작동. filter에서 발생하는 예외는 못잡음.
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e){
        ErrorResponse response = ErrorResponse.of(ErrorCode.AUTH_ERROR);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
