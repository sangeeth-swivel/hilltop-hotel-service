package com.hilltop.hotel.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomTypeRequestDto implements RequestDto {

    private String roomName;
    private double markupPercentage;

    @Override
    public boolean isRequiredFieldsAvailable() {
        return isNonEmpty(roomName) && markupPercentage > 0;
    }

}
