package com.hilltop.hotel.domain.response;

import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.request.HotelRequestDto;
import com.hilltop.hotel.domain.request.RoomRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelResponseDtoTest {
    private HotelRequestDto hotelRequestDto;
    private RoomRequestDto roomRequestDto;

    @BeforeEach
    void setUp() {
        hotelRequestDto = new HotelRequestDto();
        hotelRequestDto.setHotelName("Kandy Grand");
        hotelRequestDto.setHotelLocation("Kandy");

        roomRequestDto = new RoomRequestDto();
        roomRequestDto.setRoomNo("102");
        roomRequestDto.setHotelId("5678");
        roomRequestDto.setRoomTypeId("1234");
        roomRequestDto.setCost(250.0);
        roomRequestDto.setMaxPeople(3);
    }

    @Test
    public void TestHotelResponseDtoConstructor() {
        String hotelName = "Kandy Grand";
        String hotelLocation = "Kandy";
        Hotel hotel = new Hotel(hotelRequestDto);

        HotelResponseDto hotelResponseDto = new HotelResponseDto(hotel);

        assertEquals(hotelName, hotelResponseDto.getName(), "Hotel name should be set correctly.");
        assertEquals(hotelLocation, hotelResponseDto.getLocation(), "Hotel location should be set correctly.");

        assertNull(hotelResponseDto.getRooms(), "Rooms list should be initialized as null.");
    }
}