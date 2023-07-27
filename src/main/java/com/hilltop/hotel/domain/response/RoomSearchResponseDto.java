package com.hilltop.hotel.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Room Search ResponseDto
 */
@Getter
@Setter
public class RoomSearchResponseDto implements ResponseDto{

    private String hotelId;
    private List<RoomResponseDto> rooms;

    public RoomSearchResponseDto(String hotelId, List<RoomResponseDto> rooms, int days) {
        this.hotelId = hotelId;
        this.rooms = generateRoomCostForGivenPeriods(rooms, days);
    }

    private List<RoomResponseDto> generateRoomCostForGivenPeriods(List<RoomResponseDto> rooms, int days) {
        rooms.forEach(roomResponseDto -> roomResponseDto.setCost(roomResponseDto.getCost().multiply(BigDecimal.valueOf(days))));
        return rooms;
    }
}