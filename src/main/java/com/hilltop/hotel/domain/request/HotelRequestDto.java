package com.hilltop.hotel.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelRequestDto implements RequestDto {

    private String hotelName;
    private String hotelLocation;
    @Override
    public boolean isRequiredFieldsAvailable() {
        return isNonEmpty(hotelName) && isNonEmpty(hotelLocation);
    }

}
