package com.hilltop.hotel.service;

import com.hilltop.hotel.domain.request.RoomTypeRequestDto;
import com.hilltop.hotel.exception.DataNotFoundException;
import com.hilltop.hotel.exception.HillTopHotelApplicationException;
import com.hilltop.hotel.repository.RoomTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Room type service test
 * Unit tests for {@link  RoomTypeService}
 */
class RoomTypeServiceTest {

    private static final String FAILED = "Failed.";
    private final RoomTypeRequestDto roomTypeRequestDto = getRoomTypeRequestDto();
    @Mock
    private RoomTypeRepository roomTypeRepository;
    private RoomTypeService roomTypeService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        roomTypeService = new RoomTypeService(roomTypeRepository);
    }

    /**
     * Unit tests for addRoomType() method.
     */
    @Test
    void Should_SaveRoomTypeDetailOnDatabase_When_ValidDataIsGiven() {
        roomTypeService.addRoomType(roomTypeRequestDto);
        verify(roomTypeRepository, times(1)).save(any());
    }

    /**
     * Unit tests for getRoomTypeById() method.
     */
    @Test
    void Should_ThrowDataNotFoundException_When_FailedToGetRoomTypeById() {
        when(roomTypeRepository.findById(anyString())).thenReturn(Optional.empty());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> roomTypeService.getRoomTypeById("rtid-123"));
        assertEquals("Room type not found for id: rtid-123", exception.getMessage());
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