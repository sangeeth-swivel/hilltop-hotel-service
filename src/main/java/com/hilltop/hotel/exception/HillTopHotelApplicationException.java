package com.hilltop.hotel.exception;

public class HillTopHotelApplicationException extends RuntimeException{
    public HillTopHotelApplicationException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
    public HillTopHotelApplicationException(String errorMessage) {
        super(errorMessage);
    }
}
