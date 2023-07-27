package com.hilltop.hotel.exception;

/**
 * InvalidHotelException
 */
public class InvalidHotelException extends HillTopHotelApplicationException{
    public InvalidHotelException(String errorMessage) {
        super(errorMessage);
    }
}