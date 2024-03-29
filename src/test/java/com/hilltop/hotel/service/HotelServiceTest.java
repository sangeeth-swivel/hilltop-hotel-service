package com.hilltop.hotel.service;

import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.request.UpdateHotelRequestDto;
import com.hilltop.hotel.domain.response.CityListResponseDto;
import com.hilltop.hotel.exception.ExistingNameException;
import com.hilltop.hotel.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        return hotel;
    }

    /**
     * This method is used to mock room.
     *
     * @return room
     */
    private Room getRoom() {
        Room room = new Room();
        room.setRoomNo(110);
        room.setPaxCount(3);
        return room;
    }

    /**
     * This method is used to returnExistingNameException()
     */
    @Test
    void Should_ReturnExistingNameException() {
        String errorMessage = "Name already exists!";
        ExistingNameException exception = new ExistingNameException(errorMessage);
        assertEquals(errorMessage, exception.getMessage(), "Hotel name exist in database.");
    }

    @Test
    void Should_ReturnCities() {
        List<String> cityNames = Arrays.asList("New York", "Los Angeles", "Chicago");
        CityListResponseDto responseDto = new CityListResponseDto(cityNames);
        Set<String> expectedCities = new HashSet<>(cityNames);
        assertEquals(expectedCities, responseDto.getCities());
    }

    @Test
    void Should_ReturnEmptyCityList() {
        List<String> emptyCityList = Arrays.asList();
        CityListResponseDto responseDto = new CityListResponseDto(emptyCityList);
        assertTrue(responseDto.getCities().isEmpty());
    }
}