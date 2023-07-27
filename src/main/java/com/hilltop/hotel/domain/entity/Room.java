package com.hilltop.hotel.domain.entity;

import com.hilltop.hotel.domain.request.RoomRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Room entity
 */
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Transient
    private static final String ROOM_ID_PREFIX = "rid-";
    @Id
    private String id;
    private int roomNo;
    private String hotelId;
    private int paxCount;
    @ManyToOne
    private RoomType roomType;
    private BigDecimal cost;
    private BigDecimal perNight;
    @ElementCollection
    private List<String> imageUrls;
    private long createdAt;
    private long updatedAt;

    public Room(RoomRequestDto roomRequestDto, RoomType roomType) {
        this.id = ROOM_ID_PREFIX + UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        updateRoom(roomRequestDto, roomType);
    }

    public void updateRoom(RoomRequestDto roomRequestDto, RoomType roomType) {
        this.roomType = roomType;
        this.roomNo = roomRequestDto.getRoomNo();
        this.paxCount = roomRequestDto.getPaxCount();
        this.hotelId = roomRequestDto.getHotelId();
        this.cost = calculateRoomPrice(roomRequestDto.getPerNight(), roomType);
        this.imageUrls = roomRequestDto.getImageUrls();
        this.updatedAt = System.currentTimeMillis();
    }

    private BigDecimal calculateRoomPrice(BigDecimal perNight, RoomType roomType) {
        BigDecimal markupValue = perNight.multiply(BigDecimal.valueOf(roomType.getMarkupPercentage() / 100));
        return perNight.add(markupValue);
    }
}
