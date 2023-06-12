package com.hilltop.hotel.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequestDto implements RequestDto {

    private String roomNo;
    private String hotelId;
    private String roomTypeId;
    private int maxPeople;
    private double cost;

    @Override
    public boolean isRequiredFieldsAvailable() {
        return isNonEmpty(hotelId) && isNonEmpty(roomNo) && isNonEmpty(roomTypeId) && maxPeople > 0 && cost > 0;
    }
}