package com.hilltop.hotel.enums;

import lombok.Getter;

/**
 * Error messages.
 */
@Getter
public enum ErrorMessage {

    INTERNAL_SERVER_ERROR("Something went wrong."),
    MISSING_REQUIRED_FIELDS("Required fields are missing."),
    EXISTING_NAME("Hotel name is already existing."),
    DATA_NOT_FOUND("Data not found."),
    INVALID_HOTEL_ID("Invalid hotel ID.");
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
