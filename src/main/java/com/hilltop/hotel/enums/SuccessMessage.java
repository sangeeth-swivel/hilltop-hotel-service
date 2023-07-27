package com.hilltop.hotel.enums;

import lombok.Getter;

/**
 * Success messages.
 */
@Getter
public enum SuccessMessage {

    SUCCESSFULLY_ADDED("Successfully added."),
    SUCCESSFULLY_UPDATED("Successfully updated."),
    SUCCESSFULLY_RETURNED("Successfully returned data."),
    SUCCESSFULLY_DELETED("Successfully deleted."),
    READ_HOTEL("Successfully returned the hotel by id."),
    READ_HOTEL_LIST("Successfully returned the hotel list."),
    READ_HOTEL_CITIES("Successfully returned the hotel cities."),
    READ_HOTELS_BY_CITY("Successfully returned the hotel list by city."),
    DELETE_HOTEL("Successfully delete the hotel.");
    private final String message;

    SuccessMessage(String message) {
        this.message = message;
    }
}
