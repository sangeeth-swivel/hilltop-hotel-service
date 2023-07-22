package com.hilltop.hotel.domain.response;

import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.entity.RoomType;
import com.hilltop.hotel.domain.request.HotelRequestDto;
import com.hilltop.hotel.domain.request.RoomRequestDto;
import com.hilltop.hotel.domain.request.RoomTypeRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        roomRequestDto.setRoomNo("101");
        roomRequestDto.setHotelId("1234");
        roomRequestDto.setRoomTypeId("5678");
        roomRequestDto.setCost(150.0);
        roomRequestDto.setMaxPeople(2);

        roomTypeRequestDto = new RoomTypeRequestDto();
        roomTypeRequestDto.setRoomName("Single");
        roomTypeRequestDto.setMarkupPercentage(10);
    }

    @Test
    public void TestIsRequiredFieldsAvailable() {
        // Arrange
        String roomNo = "101";
        String hotelId = "1234";
        String roomTypeId = "5678";
        int maxPeople = 2;
        double cost = 150.0;

        RoomRequestDto validDto = new RoomRequestDto(roomNo, hotelId, roomTypeId, maxPeople, cost);

        assertTrue(validDto.isRequiredFieldsAvailable(), "All required fields are available, should return true.");

        RoomRequestDto missingRoomNoDto = new RoomRequestDto(null, hotelId, roomTypeId, maxPeople, cost);
        assertFalse(missingRoomNoDto.isRequiredFieldsAvailable(), "Missing roomNo, should return false.");

        RoomRequestDto missingHotelIdDto = new RoomRequestDto(roomNo, null, roomTypeId, maxPeople, cost);
        assertFalse(missingHotelIdDto.isRequiredFieldsAvailable(), "Missing hotelId, should return false.");

        RoomRequestDto missingRoomTypeIdDto = new RoomRequestDto(roomNo, hotelId, null, maxPeople, cost);
        assertFalse(missingRoomTypeIdDto.isRequiredFieldsAvailable(), "Missing roomTypeId, should return false.");

        RoomRequestDto invalidMaxPeopleDto = new RoomRequestDto(roomNo, hotelId, roomTypeId, 0, cost);
        assertFalse(invalidMaxPeopleDto.isRequiredFieldsAvailable(), "Invalid maxPeople, should return false.");

        RoomRequestDto invalidCostDto = new RoomRequestDto(roomNo, hotelId, roomTypeId, maxPeople, -50.0);
        assertFalse(invalidCostDto.isRequiredFieldsAvailable(), "Invalid cost, should return false.");
    }

    @Test
    public void testRoomResponseDtoConstructor() {
        String roomNo = "101";
        RoomType roomType = new RoomType(roomTypeRequestDto);
        Hotel hotel = new Hotel(hotelRequestDto);
        int maxPeople = 2;
        double price = 165.0;
        Room room = new Room(roomRequestDto, hotel, roomType);

        RoomResponseDto roomResponseDto = new RoomResponseDto(room);

        assertEquals(roomNo, roomResponseDto.getRoomNo(), "Room number should be set correctly.");
        assertEquals("Single", roomResponseDto.getRoomType(), "Room type should be set correctly.");
        assertEquals(maxPeople, roomResponseDto.getMaxPeople(), "Max people should be set correctly.");
        assertEquals(price, roomResponseDto.getPrice(), 0.01, "Price should be set correctly.");
    }
}