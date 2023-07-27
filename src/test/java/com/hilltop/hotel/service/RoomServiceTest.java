package com.hilltop.hotel.service;

import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.entity.RoomType;
import com.hilltop.hotel.domain.request.RoomTypeRequestDto;
import com.hilltop.hotel.domain.request.UpdateRoomRequestDto;
import com.hilltop.hotel.exception.HillTopHotelApplicationException;
import com.hilltop.hotel.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Room service test
 * Unit tests for {@link  RoomService}
 */
class RoomServiceTest {

    private static final String ROOM_ID = "rid-123";
    private static final String FAILED = "Failed.";
    private final UpdateRoomRequestDto updateRoomRequestDto = getUpdateRoomRequestDto();
    private final Room room = getRoom();
    private final Hotel hotel = getHotel();
    private final RoomType roomType = new RoomType(getRoomTypeRequestDto());
    private RoomService roomService;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private RoomTypeService roomTypeService;
    @Mock
    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        roomService = new RoomService(roomRepository, hotelService, roomTypeService);
    }

    /**
     * Unit tests for addRoom() method.
     */
    @Test
    void Should_SaveRoomDetailOnDatabase_When_ValidDataIsGiven() {
        when(hotelService.getHotelById(anyString())).thenReturn(hotel);
        when(roomTypeService.getRoomTypeById(anyString())).thenReturn(roomType);
        roomService.addRoom(updateRoomRequestDto);
        verify(roomRepository, times(1)).save(any());
    }

    /**
     * Unit tests for updateRoom() method.
     */
    @Test
    void Should_updateRoomDetailOnDatabase_When_ValidDataIsGiven() {
        when(roomRepository.findById(anyString())).thenReturn(Optional.of(room));
        when(hotelService.getHotelById(anyString())).thenReturn(hotel);
        when(roomTypeService.getRoomTypeById(anyString())).thenReturn(roomType);
        roomService.updateRoom(updateRoomRequestDto);
        verify(roomRepository, times(1)).save(any());
    }

    /**
     * Unit tests for deleteRoomById() method.
     */
    @Test
    void Should_DeleteRoomDetailFromDatabase_When_QueryIsValid() {
        roomService.deleteRoomById(ROOM_ID);
        verify(roomRepository, times(1)).deleteById(anyString());
    }

    /**
     * Unit tests for getRoomListByHotelIdAndSearchTerm() method.
     */
    @Test
    void Should_RunFindByHotelIdQuery_When_GetRoomListByHotelIdIsCalled() {
        roomService.getRoomListByHotelIdAndSearchTerm(anyString(), null);
        verify(roomRepository, times(1)).findAllByHotelId(anyString());
    }

    @Test
    void Should_RunFindByHotelIdAndRoomSearchQuery_When_GetRoomListByHotelIdIsCalled() {
        roomService.getRoomListByHotelIdAndSearchTerm(anyString(), anyString());
        verify(roomRepository, times(1))
                .findAllByHotelIdAndRoomNoContaining(anyString(), anyString());
    }

    /**
     * This method is used to mock roomRequestDto.
     *
     * @return updateRoomRequestDto
     */
    private UpdateRoomRequestDto getUpdateRoomRequestDto() {
        UpdateRoomRequestDto updateRoomRequestDto = new UpdateRoomRequestDto();
        updateRoomRequestDto.setId(ROOM_ID);
        updateRoomRequestDto.setRoomNo(110);
        updateRoomRequestDto.setHotelId("hid-123");
        updateRoomRequestDto.setRoomTypeId("rtid-123");
        updateRoomRequestDto.setPerNight(BigDecimal.valueOf(2000));
        return updateRoomRequestDto;
    }

    /**
     * This method is used to mock room.
     *
     * @return room
     */
    private Room getRoom() {
        Room room = new Room();
        room.setRoomNo(110);
        room.setPaxCount(5);
        return room;
    }

    /**
     * This method is used to mock hotel.
     *
     * @return hotel
     */
    private Hotel getHotel() {
        Hotel hotel = new Hotel();
        hotel.setHotelName("Hotel");
        hotel.setHotelLocation("Colombo");
        return hotel;
    }

    /**
     * This method is used to mock roomTypeRequestDto.
     *
     * @return roomTypeRequestDto
     */
    private RoomTypeRequestDto getRoomTypeRequestDto() {
        RoomTypeRequestDto roomTypeRequestDto = new RoomTypeRequestDto();
        roomTypeRequestDto.setRoomName("Gold");
        roomTypeRequestDto.setMarkupPercentage(5);
        return roomTypeRequestDto;
    }

}