package com.hilltop.hotel.domain.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Update room requestDto
 */
@Getter
@Setter
public class UpdateRoomRequestDto extends RoomRequestDto {

    private String id;

    public boolean isRequiredFieldsAvailableForUpdate() {
        return isNonEmpty(id) && isRequiredFieldsAvailable();
    }
}
