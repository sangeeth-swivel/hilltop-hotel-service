package com.hilltop.hotel.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hilltop.hotel.domain.entity.Hotel;
import lombok.Getter;

import java.util.List;

/**
 * Hotel responseDto
 */
@Getter
public class HotelResponseDto implements ResponseDto {

    private String id;
    private String hotelName;
    private String hotelLocation;
    private String city;
    private List<String> imageUrl;
    private String contact;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RoomResponseDto> rooms;

    public HotelResponseDto(Hotel hotel) {
        this.id = hotel.getId();
        this.hotelName = hotel.getHotelName();
        this.hotelLocation = hotel.getHotelLocation();
        this.city = hotel.getCity();
        this.imageUrl = hotel.getImageUrl();
        this.contact = hotel.getContact();
        this.email = hotel.getEmail();
    }
}
