package com.hilltop.hotel.controller;

import com.hilltop.hotel.domain.response.ResponseWrapper;
import com.hilltop.hotel.exception.DataNotFoundException;
import com.hilltop.hotel.exception.HillTopHotelApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final BaseController baseController;

    public GlobalControllerExceptionHandler(BaseController baseController) {
        this.baseController = baseController;
    }

    @ExceptionHandler(HillTopHotelApplicationException.class)
    public ResponseEntity<ResponseWrapper> hillTopUserApplicationException(HillTopHotelApplicationException exception) {
        log.debug("Internal Server Error. {}", exception.getMessage());
        return baseController.getInternalServerError();
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseWrapper> hillTopUserApplicationException(DataNotFoundException exception) {
        log.debug("Data not found. {}", exception.getMessage());
        return baseController.getInternalServerError();
    }

}
