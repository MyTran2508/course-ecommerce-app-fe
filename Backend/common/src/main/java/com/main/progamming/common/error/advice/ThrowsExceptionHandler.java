package com.main.progamming.common.error.advice;

import com.main.progamming.common.error.exception.*;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ThrowsExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DataResponse<?>> handeResourceNotFoundException(final RuntimeException e) {
        DataResponse<?> errorResponse = ResponseMapper.toDataResponse(e.getMessage(),
                StatusCode.DATA_NOT_FOUND, StatusMessage.DATA_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<DataResponse<?>> handleDataAlreadyExistException(final RuntimeException e) {
        DataResponse<?> errorResponse = ResponseMapper.toDataResponse(e.getMessage(),
                StatusCode.DATA_CONFLICT, StatusMessage.DATA_CONFLICT);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<DataResponse<?>> handleDataNotFoundException(final RuntimeException e) {
        DataResponse<?> errorResponse = ResponseMapper.toDataResponse(e.getMessage(),
                StatusCode.DATA_NOT_FOUND, StatusMessage.DATA_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<DataResponse<?>> handleDataConflictException(final RuntimeException e) {
        DataResponse<?> errorResponse = ResponseMapper.toDataResponse(e.getMessage(),
                StatusCode.DATA_CONFLICT, StatusMessage.DATA_CONFLICT);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(NotPermissionException.class)
    public ResponseEntity<DataResponse<?>> handleNotPermissionException(final RuntimeException e) {
        DataResponse<?> errorResponse = ResponseMapper.toDataResponse(e.getMessage(),
                StatusCode.NOT_PERMISSION, StatusMessage.NOT_PERMISSION);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
