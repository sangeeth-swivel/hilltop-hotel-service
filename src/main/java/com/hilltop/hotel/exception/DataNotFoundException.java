package com.hilltop.hotel.exception;

public class DataNotFoundException extends HillTopHotelApplicationException {
    public DataNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
