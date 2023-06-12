package com.hilltop.hotel.domain.entity;

import com.hilltop.hotel.domain.request.RoomTypeRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.UUID;

/**
 * Room type entity
 */
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class RoomType {

    @Transient
    private static final String ROOM_TYPE_ID_PREFIX = "rtid-";

    @Id
    private String id;
    private String roomName;
    private double markupPercentage;

    public RoomType(RoomTypeRequestDto roomTypeRequestDto) {
        this.id = ROOM_TYPE_ID_PREFIX + UUID.randomUUID();
        this.roomName = roomTypeRequestDto.getRoomName();
        this.markupPercentage = roomTypeRequestDto.getMarkupPercentage();
    }
}
