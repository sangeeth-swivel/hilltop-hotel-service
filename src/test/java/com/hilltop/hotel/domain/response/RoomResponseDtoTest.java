package com.hilltop.hotel.domain.response;

import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.entity.RoomType;
import com.hilltop.hotel.domain.request.HotelRequestDto;
import com.hilltop.hotel.domain.request.RoomRequestDto;
import com.hilltop.hotel.domain.request.RoomTypeRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RoomResponseDtoTest {
    private HotelRequestDto hotelRequestDto;
    private RoomRequestDto roomRequestDto;
    private RoomRequestDto roomRequestDto2;
    private RoomTypeRequestDto roomTypeRequestDto;
    private RoomTypeRequestDto roomTypeRequestDto2;

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

        roomRequestDto2 = new RoomRequestDto();
        roomRequestDto2.setRoomNo(120);
        roomRequestDto2.setHotelId("1234");
        roomRequestDto2.setRoomTypeId("1234");
        roomRequestDto2.setPerNight(BigDecimal.valueOf(2000));

        roomTypeRequestDto = new RoomTypeRequestDto();
        roomTypeRequestDto.setRoomName("Single");
        roomTypeRequestDto.setMarkupPercentage(10);

        roomTypeRequestDto2 = new RoomTypeRequestDto();
        roomTypeRequestDto2.setRoomName("Double");
        roomTypeRequestDto2.setMarkupPercentage(15);
    }

    /**
     * This method is used to returnRoomResponseDtoConstructor()
     */
    @Test
    void Should_ReturnRoomResponseDtoConstructor() {
        int roomNo = 110;
        RoomType roomType = new RoomType(roomTypeRequestDto);
        Room room = new Room(roomRequestDto, roomType);

        RoomResponseDto roomResponseDto = new RoomResponseDto(room);

        assertEquals(roomNo, roomResponseDto.getRoomNo(), "Room number should be set correctly.");
        assertEquals("Single", roomResponseDto.getRoomType(), "Room type should be set correctly.");
    }

    @Test
    void Should_GenerateRoomCostForGivenPeriods() {
        String hotelId = "123";
        int days = 5;
        RoomType roomType = new RoomType(roomTypeRequestDto);
        Room room = new Room(roomRequestDto, roomType);

        List<RoomResponseDto> rooms = new ArrayList<>();
        rooms.add(new RoomResponseDto(room));

        RoomSearchResponseDto responseDto = new RoomSearchResponseDto(hotelId, rooms, days);

        assertEquals(hotelId, responseDto.getHotelId());

        List<RoomResponseDto> updatedRooms = responseDto.getRooms();
        assertEquals(rooms.size(), updatedRooms.size());
        assertEquals(new BigDecimal("5500.0"), updatedRooms.get(0).getCost());
    }

    @Test
    void Should_GenerateResponse() {
        String hotelId1 = "hotel123";
        int days = 3;

        RoomType roomType = new RoomType(roomTypeRequestDto);
        RoomType roomType2 = new RoomType(roomTypeRequestDto2);

        List<Room> rooms1 = Arrays.asList(
                new Room(roomRequestDto, roomType),
                new Room(roomRequestDto2, roomType2)
        );

        Map<String, List<Room>> dataMap = new HashMap<>();
        dataMap.put(hotelId1, rooms1);

        RoomListResponseDto responseDto = new RoomListResponseDto(dataMap, days);

        List<RoomSearchResponseDto> responseList = responseDto.getList();
        assertEquals(1, responseList.size());

        RoomSearchResponseDto roomSearchResponseDto1 = responseList.get(0);
        assertEquals(hotelId1, roomSearchResponseDto1.getHotelId());
        List<RoomResponseDto> roomResponseDtos1 = roomSearchResponseDto1.getRooms();
        assertEquals(2, roomResponseDtos1.size());
        assertEquals(new BigDecimal("3300.0"), roomResponseDtos1.get(0).getCost());
    }

    @Test
    void Should_GenerateRoomResponseList() {
        RoomType roomType = new RoomType(roomTypeRequestDto);
        RoomType roomType2 = new RoomType(roomTypeRequestDto2);
        Room room1 = new Room(roomRequestDto, roomType);
        Room room2 = new Room(roomRequestDto2, roomType2);
        List<Room> roomList = Arrays.asList(room1, room2);
        Page<Room> roomPage = new PageImpl<>(roomList);

        RoomListPageResponseDto responseDto = new RoomListPageResponseDto(roomPage);

        List<RoomResponseDto> responseList = responseDto.getRoomResponses();
        assertEquals(roomList.size(), responseList.size());
        assertEquals(room1.getRoomNo(), responseList.get(0).getRoomNo());
        assertEquals(room2.getRoomNo(), responseList.get(1).getRoomNo());
        assertEquals(room1.getCost(), responseList.get(0).getCost());
        assertEquals(room2.getCost(), responseList.get(1).getCost());
    }
}