package com.example.spring_jdbc.exception;

import com.example.spring_jdbc.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(value = Exception.class)
ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){
ApiResponse apiResponse=new ApiResponse();
apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
apiResponse.setMessenger(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());



    return ResponseEntity.badRequest().body(apiResponse);
}

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
    ErrorCode errorCode=exception.getErrorCode();
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessenger(errorCode.getMessage());



        return ResponseEntity.badRequest().body(apiResponse);
    }
@ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse>handlingvadiEXception(MethodArgumentNotValidException exception){
    String enumkey=exception.getFieldError().getDefaultMessage();




    ErrorCode errorCode=ErrorCode.INVALID_KEY;
    try {
        errorCode=ErrorCode.valueOf(enumkey);
    } catch (IllegalArgumentException e){
    }

    ApiResponse apiResponse=new ApiResponse();
    apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessenger(errorCode.getMessage());



    return ResponseEntity.badRequest().body(apiResponse);
}


}
