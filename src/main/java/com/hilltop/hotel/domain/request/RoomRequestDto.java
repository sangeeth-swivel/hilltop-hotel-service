package com.hilltop.hotel.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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