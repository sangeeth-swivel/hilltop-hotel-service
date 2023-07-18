package com.hilltop.hotel.service;

import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.request.UpdateHotelRequestDto;
import com.hilltop.hotel.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Hotel service test
 * Unit tests for {@link  HotelService}
 */
class HotelServiceTest {

    private static final String FAILED = "Failed.";
    private final UpdateHotelRequestDto updateHotelRequestDto = getUpdateHotelRequestDto();
    private final Hotel hotel = new Hotel(getUpdateHotelRequestDto());
    @Mock
    private HotelRepository hotelRepository;
    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        hotelService = new HotelService(hotelRepository);
    }

    /**
     * Unit tests for addHotel() method.
     */
    @Test
    void Should_SaveHotelDetailOnDatabase_When_ValidDataIsGiven() {
        hotelService.addHotel(updateHotelRequestDto);
        verify(hotelRepository, times(1)).save(any());
    }

    /**
     * Unit tests for updateHotel() method.
     */
    @Test
    void Should_UpdateHotelDetailOnDatabase_When_ValidDataIsGiven() {
        when(hotelRepository.findById(any())).thenReturn(Optional.of(hotel));
        hotelService.updateHotel(updateHotelRequestDto);
        verify(hotelRepository, times(1)).save(any());
    }

    /**
     * Unit tests for getHotelList() method
     */
    @Test
    void Should_RunFindAllQuery_When_GetHotelListIsCalled() {
        hotelService.getHotelList();
        verify(hotelRepository, times(1)).findAll();
    }

    /**
     * This method is used to mock hotelRequestDto.
     *
     * @return updateHotelRequestDto
     */
    private UpdateHotelRequestDto getUpdateHotelRequestDto() {
        UpdateHotelRequestDto updateHotelRequestDto = new UpdateHotelRequestDto();
        updateHotelRequestDto.setHotelName("Hotel");
        updateHotelRequestDto.setHotelLocation("Colombo");
        return updateHotelRequestDto;
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
        hotel.setRooms(Set.of(getRoom()));
        return hotel;
    }

    /**
     * This method is used to mock room.
     *
     * @return room
     */
    private Room getRoom() {
        Room room = new Room();
        room.setRoomNo("R1");
        room.setMaxPeople(3);
        return room;
    }

}