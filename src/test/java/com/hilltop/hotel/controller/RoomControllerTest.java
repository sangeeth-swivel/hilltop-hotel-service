package com.hilltop.hotel.controller;

import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.entity.RoomType;
import com.hilltop.hotel.domain.request.RoomRequestDto;
import com.hilltop.hotel.domain.request.RoomTypeRequestDto;
import com.hilltop.hotel.domain.request.UpdateRoomRequestDto;
import com.hilltop.hotel.enums.ErrorMessage;
import com.hilltop.hotel.enums.SuccessMessage;
import com.hilltop.hotel.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Room controller test
 * Unit tests for {@link  RoomController}
 */
class RoomControllerTest {

    private static final String FAILED = "Failed.";
    private final String ADD_ROOM_URI = "/api/v1/room";
    private final String UPDATE_ROOM_URI = "/api/v1/room";
    private final String DELETE_ROOM_URI = "/api/v1/room/ID";
    private final String LIST_ROOM_URI = "/api/v1/room/hotel/ID";
    private final UpdateRoomRequestDto updateRoomRequestDto = getUpdateRoomRequestDto();
    private final RoomType roomType = new RoomType(getRoomTypeRequestDto());
    private final Room room = getRoom();
    @Mock
    private RoomService roomService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        openMocks(this);
        RoomController roomController = new RoomController(roomService);
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
    }

    /**
     * Unit tests for addRoom() method.
     */
    @Test
    void Should_ReturnOk_When_AddRoomIsSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ADD_ROOM_URI)
                        .content(updateRoomRequestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(SuccessMessage.SUCCESSFULLY_ADDED.getMessage()));
    }

    @Test
    void Should_ReturnBadRequest_When_MissingRequiredFields() throws Exception {
        RoomRequestDto requestDto = updateRoomRequestDto;
        requestDto.setHotelId(null);
        mockMvc.perform(MockMvcRequestBuilders.post(ADD_ROOM_URI)
                        .content(requestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorMessage.MISSING_REQUIRED_FIELDS.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /**
     * Unit tests for updateRoom() method.
     */
    @Test
    void Should_ReturnOk_When_UpdateRoomIsSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_ROOM_URI)
                        .content(updateRoomRequestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SuccessMessage.SUCCESSFULLY_UPDATED.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void Should_ReturnBadRequest_When_UpdateRoomFieldsAreMissing() throws Exception {
        RoomRequestDto requestDto = updateRoomRequestDto;
        requestDto.setHotelId(null);
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_ROOM_URI)
                        .content(requestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorMessage.MISSING_REQUIRED_FIELDS.getMessage()))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    /**
     * Unit tests for deleteRoom() method.
     */
    @Test
    void Should_ReturnOk_When_DeleteRoomIsSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_ROOM_URI)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SuccessMessage.SUCCESSFULLY_DELETED.getMessage()));
    }

    /**
     * Unit tests for listAllRoomsByHotelId() method.
     */
    @Test
    void Should_ReturnOk_When_ListAllRoomsByHotelIdIsSuccessful() throws Exception {
        List<Room> roomList = List.of(room);
        when(roomService.getRoomListByHotelIdAndSearchTerm(anyString(), anyString())).thenReturn(roomList);
        mockMvc.perform(MockMvcRequestBuilders.get(LIST_ROOM_URI)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SuccessMessage.SUCCESSFULLY_RETURNED.getMessage()));
    }

    /**
     * This method is used to mock roomRequestDto.
     *
     * @return updateRoomRequestDto
     */
    private UpdateRoomRequestDto getUpdateRoomRequestDto() {
        UpdateRoomRequestDto updateRoomRequestDto = new UpdateRoomRequestDto();
        updateRoomRequestDto.setId("rid-123");
        updateRoomRequestDto.setRoomNo("R1");
        updateRoomRequestDto.setHotelId("hid-123");
        updateRoomRequestDto.setRoomTypeId("rtid-123");
        updateRoomRequestDto.setMaxPeople(5);
        updateRoomRequestDto.setCost(100);
        return updateRoomRequestDto;
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

    /**
     * This method is used to mock room.
     *
     * @return room
     */
    private Room getRoom() {
        Room room = new Room();
        room.setRoomNo("R1");
        room.setMaxPeople(5);
        room.setRoomType(roomType);
        return room;
    }
}