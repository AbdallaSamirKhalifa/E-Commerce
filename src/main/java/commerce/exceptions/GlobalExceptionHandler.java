package commerce.exceptions;

import commerce.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request){
        var status=HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse=ErrorResponse.builder().
                timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build()
                ;
        return new ResponseEntity<>(errorResponse,status);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex, HttpServletRequest request){
        var status=HttpStatus.CONFLICT;

        ErrorResponse errorResponse=ErrorResponse.builder().
                timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build()
                ;
        return new ResponseEntity<>(errorResponse,status);
    }

    @ExceptionHandler(EmptyCartException.class)

    public ResponseEntity<ErrorResponse> handleEmptyCartException(EmptyCartException ex, HttpServletRequest request){
        var status=HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse=ErrorResponse.builder().
                timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build()
                ;
        return new ResponseEntity<>(errorResponse,status);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException ex, HttpServletRequest request){
        var status=HttpStatus.CONFLICT;
        ErrorResponse errorResponse=ErrorResponse.builder().
                timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build()
                ;
        return new ResponseEntity<>(errorResponse,status);
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ErrorResponse> handleInvalidQuantityException(InvalidQuantityException ex, HttpServletRequest request){

        var status=HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse=ErrorResponse.builder().
                timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build()
                ;
        return new ResponseEntity<>(errorResponse,status);
    }



    @ExceptionHandler(ProductUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleProductUnavailableException(ProductUnavailableException ex, HttpServletRequest request){

        var status=HttpStatus.OK;
        ErrorResponse errorResponse=ErrorResponse.builder().
                timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build()
                ;
        return new ResponseEntity<>(errorResponse,status);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessException(UnauthorizedAccessException ex, HttpServletRequest request){

        var status=HttpStatus.FORBIDDEN;
        ErrorResponse errorResponse=ErrorResponse.builder().
                timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build()
                ;
        return new ResponseEntity<>(errorResponse,status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(RuntimeException ex, HttpServletRequest request){

        var status=HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse=ErrorResponse.builder().
                timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build()
                ;
        return new ResponseEntity<>(errorResponse,status);
    }



}

