package com.hilltop.hotel.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDto implements RequestDto {

    private int roomNo;
    private String hotelId;
    private String roomTypeId;
    private int paxCount;
    private BigDecimal perNight;
    private List<String> imageUrls;
    private String city;
    @Override
    public boolean isRequiredFieldsAvailable() {
        return roomNo > 0 && isNonEmpty(hotelId) && paxCount > 0 && isNonEmpty(roomTypeId);
    }
}