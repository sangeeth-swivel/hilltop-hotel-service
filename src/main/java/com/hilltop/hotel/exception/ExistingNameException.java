package com.hilltop.hotel.exception;

/**
 * Existing Name Exception
 */
public class ExistingNameException extends HillTopHotelApplicationException{
    /**
     * ExistingNameException Exception with error message.
     *
     * @param errorMessage error message.
     */
    public ExistingNameException(String errorMessage) {
        super(errorMessage);
    }
}
