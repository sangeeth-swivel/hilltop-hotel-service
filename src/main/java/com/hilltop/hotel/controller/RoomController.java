package com.hilltop.hotel.controller;

import com.hilltop.hotel.domain.request.RoomRequestDto;
import com.hilltop.hotel.domain.request.UpdateRoomRequestDto;
import com.hilltop.hotel.domain.response.ResponseWrapper;
import com.hilltop.hotel.domain.response.RoomListResponseDto;
import com.hilltop.hotel.enums.ErrorMessage;
import com.hilltop.hotel.enums.SuccessMessage;
import com.hilltop.hotel.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/room")
public class RoomController extends BaseController {

    private static final String MISSING_FIELDS = "Required fields missing. data: {}";
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseWrapper> addRoom(@RequestBody RoomRequestDto roomRequestDto) {
        if (!roomRequestDto.isRequiredFieldsAvailable()) {
            log.debug(MISSING_FIELDS, roomRequestDto.toLogJson());
            return getBadRequestErrorResponse(ErrorMessage.MISSING_REQUIRED_FIELDS);
        }
        roomService.addRoom(roomRequestDto);
        log.debug("Successfully added room info.");
        return getSuccessResponse(SuccessMessage.SUCCESSFULLY_ADDED, null, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<ResponseWrapper> updateRoom(@RequestBody UpdateRoomRequestDto updateRoomRequestDto) {
        if (!updateRoomRequestDto.isRequiredFieldsAvailableForUpdate()) {
            log.debug(MISSING_FIELDS, updateRoomRequestDto.toLogJson());
            return getBadRequestErrorResponse(ErrorMessage.MISSING_REQUIRED_FIELDS);
        }
        roomService.updateRoom(updateRoomRequestDto);
        return getSuccessResponse(SuccessMessage.SUCCESSFULLY_UPDATED, null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteRoom(@PathVariable String id) {
        roomService.deleteRoomById(id);
        return getSuccessResponse(SuccessMessage.SUCCESSFULLY_DELETED, null, HttpStatus.OK);
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<ResponseWrapper> listAllRoomsByHotelId(@PathVariable String id,
                                                                 @RequestParam(required = false) String searchTerm) {
        RoomListResponseDto roomListResponseDto =
                new RoomListResponseDto(roomService.getRoomListByHotelIdAndSearchTerm(id, searchTerm));
        log.debug("Successfully returned all rooms.");
        return getSuccessResponse(SuccessMessage.SUCCESSFULLY_RETURNED, roomListResponseDto, HttpStatus.OK);

    }
}
