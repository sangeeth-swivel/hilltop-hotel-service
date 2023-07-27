package com.hilltop.hotel.domain.response;

import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.entity.RoomType;
import com.hilltop.hotel.domain.request.HotelRequestDto;
import com.hilltop.hotel.domain.request.RoomRequestDto;
import com.hilltop.hotel.domain.request.RoomTypeRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RoomResponseDtoTest {
    private HotelRequestDto hotelRequestDto;
    private RoomRequestDto roomRequestDto;
    private RoomTypeRequestDto roomTypeRequestDto;

    @BeforeEach
    void setUp() {
        hotelRequestDto = new HotelRequestDto();
        hotelRequestDto.setHotelName("Kandy Grand");
        hotelRequestDto.setHotelLocation("Kandy");

        roomRequestDto = new RoomRequestDto();
        roomRequestDto.setRoomNo(110);
        roomRequestDto.setHotelId("1234");
        roomRequestDto.setRoomTypeId("5678");
        roomRequestDto.setPerNight(BigDecimal.valueOf(1000));

        roomTypeRequestDto = new RoomTypeRequestDto();
        roomTypeRequestDto.setRoomName("Single");
        roomTypeRequestDto.setMarkupPercentage(10);
    }

    /**
     * This method is used to returnRoomResponseDtoConstructor()
     */
    @Test
    void Should_ReturnRoomResponseDtoConstructor() {
        int roomNo = 110;
        RoomType roomType = new RoomType(roomTypeRequestDto);
        Hotel hotel = new Hotel(hotelRequestDto);
        Room room = new Room(roomRequestDto, roomType);

        RoomResponseDto roomResponseDto = new RoomResponseDto(room);

        assertEquals(roomNo, roomResponseDto.getRoomNo(), "Room number should be set correctly.");
        assertEquals("Single", roomResponseDto.getRoomType(), "Room type should be set correctly.");
    }
}