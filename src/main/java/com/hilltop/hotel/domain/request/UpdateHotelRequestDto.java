package com.hilltop.hotel.domain.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Update hotel requestDto
 */
@Getter
@Setter
public class UpdateHotelRequestDto extends HotelRequestDto {

    private String id;
    public boolean isRequiredFieldsAvailableForUpdate() {
        return isNonEmpty(id) && isRequiredFieldsAvailable();
    }
}
