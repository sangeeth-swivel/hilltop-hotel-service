package com.hilltop.hotel.domain.response;

import com.hilltop.hotel.domain.entity.Room;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Room responseDto
 */
@Getter
@Setter
public class RoomResponseDto implements ResponseDto {

    private String id;
    private int roomNo;
    private String hotelId;
    private String roomType;
    private int paxCount;
    private List<String> imageUrls;
    private BigDecimal cost;

    public RoomResponseDto(Room room) {
        this.id = room.getId();
        this.roomNo = room.getRoomNo();
        this.hotelId = room.getHotelId();
        this.roomType = room.getRoomType().getRoomName();
        this.paxCount = room.getPaxCount();
        this.imageUrls = room.getImageUrls();
        cost = room.getCost();
    }
}
