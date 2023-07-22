package com.hilltop.hotel.domain.response;

import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.request.HotelRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HotelResponseDtoTest {
    private HotelRequestDto hotelRequestDto;

    @BeforeEach
    void setUp() {
        hotelRequestDto = new HotelRequestDto();
        hotelRequestDto.setHotelName("Kandy Grand");
        hotelRequestDto.setHotelLocation("Kandy");
    }

    /**
     * This method is used to returnHotelResponseDtoConstructor()
     */
    @Test
    void Should_ReturnHotelResponseDtoConstructor() {
        String hotelName = "Kandy Grand";
        String hotelLocation = "Kandy";
        Hotel hotel = new Hotel(hotelRequestDto);

        HotelResponseDto hotelResponseDto = new HotelResponseDto(hotel);

        assertEquals(hotelName, hotelResponseDto.getName(), "Hotel name should be set correctly.");
        assertEquals(hotelLocation, hotelResponseDto.getLocation(), "Hotel location should be set correctly.");

        assertNull(hotelResponseDto.getRooms(), "Rooms list should be initialized as null.");
    }
}