package com.main.progamming.common.error.advice;

import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class DirectErrorHandler extends ResponseEntityExceptionHandler{
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        System.out.println("Vao day");
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        errorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        DataResponse<?> errorResponse = ResponseMapper.toDataResponse(errors,
                StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<DataResponse<?>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        DataResponse<?> response = ResponseMapper.toDataResponse("Data already exist",
                StatusCode.DATA_CONFLICT, StatusMessage.DATA_CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
