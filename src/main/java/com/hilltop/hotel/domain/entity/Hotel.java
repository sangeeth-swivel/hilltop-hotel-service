package com.hilltop.hotel.domain.entity;

import com.hilltop.hotel.domain.request.HotelRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
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
    @Column(nullable = false)
    private String hotelName;
    @Column(nullable = false)
    private String hotelLocation;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    @ElementCollection
    private List<String> imageUrl;
    private String contact;
    private String email;
    private long createdAt;
    private long updatedAt;

    public Hotel(HotelRequestDto hotelRequestDto) {
        this.id = HOTEL_ID_PREFIX + UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        updateHotel(hotelRequestDto);
    }

    public void updateHotel(HotelRequestDto hotelRequestDto) {
        this.hotelName = hotelRequestDto.getHotelName();
        this.hotelLocation = hotelRequestDto.getHotelLocation();
        this.city = hotelRequestDto.getCity();
        this.imageUrl = hotelRequestDto.getImageUrl();
        this.contact = hotelRequestDto.getContact();
        this.email = hotelRequestDto.getEmail();
        this.updatedAt = System.currentTimeMillis();
    }
}
