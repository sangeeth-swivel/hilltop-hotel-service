package com.hilltop.hotel.domain.entity;

import com.hilltop.hotel.domain.request.HotelRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Hotel entity
 */
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Hotel {

    @Transient
    private static final String HOTEL_ID_PREFIX = "hid-";

    @Id
    private String id;
    private String hotelName;
    private String hotelLocation;
    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER)
    private Set<Room> rooms;

    public Hotel(HotelRequestDto hotelRequestDto) {
        this.id = HOTEL_ID_PREFIX + UUID.randomUUID();
        updateHotel(hotelRequestDto);
    }

    public void updateHotel(HotelRequestDto hotelRequestDto) {
        this.hotelName = hotelRequestDto.getHotelName();
        this.hotelLocation = hotelRequestDto.getHotelLocation();
    }
}
