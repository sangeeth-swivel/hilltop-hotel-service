package com.hilltop.hotel.controller;

import com.hilltop.hotel.configuration.Translator;
import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.request.RoomRequestDto;
import com.hilltop.hotel.domain.request.UpdateRoomRequestDto;
import com.hilltop.hotel.domain.response.ResponseWrapper;
import com.hilltop.hotel.domain.response.RoomListPageResponseDto;
import com.hilltop.hotel.domain.response.RoomListResponseDto;
import com.hilltop.hotel.enums.ErrorMessage;
import com.hilltop.hotel.enums.SuccessMessage;
import com.hilltop.hotel.exception.HillTopHotelApplicationException;
import com.hilltop.hotel.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/room")
public class RoomController extends BaseController {

    private static final String MISSING_FIELDS = "Required fields missing. data: {}";
    private final RoomService roomService;

    public RoomController(Translator translator, RoomService roomService) {
        super(translator);
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

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<ResponseWrapper> getRoomListForHotel(@PathVariable String hotelId,
                                                               @Min(DEFAULT_PAGE) @RequestParam int page,
                                                               @Positive @Max(PAGE_MAX_SIZE) @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(DEFAULT_SORT).descending());
        Page<Room> roomPageByHotelId = roomService.getRoomPageByHotelId(pageable, hotelId);
        var roomListPageResponseDto = new RoomListPageResponseDto(roomPageByHotelId);
        return getSuccessResponse(SuccessMessage.SUCCESSFULLY_RETURNED, roomListPageResponseDto, HttpStatus.OK);
    }

    /**
     * This endpoint used to get hotel room for the search.
     *
     * @param count    count
     * @param days     days
     * @param hotelIds hotelIds
     * @return searchRoomListResponseDto
     */
    @GetMapping("/list-hotel-room-by")
    public ResponseEntity<ResponseWrapper> getHotelRooms(@RequestParam int count,
                                                         @RequestParam int days,
                                                         @RequestParam List<String> hotelIds) {
        var rooms = roomService.getRoomsForPaxCountAndHotelIds(count, hotelIds);
        RoomListResponseDto roomListResponseDto = new RoomListResponseDto(rooms, days);
        log.info("Successfully returned the hotel rooms for pax count :{} and day count: {} ", count, days);
        return getSuccessResponse(SuccessMessage.SUCCESSFULLY_RETURNED, roomListResponseDto, HttpStatus.OK);
    }
}
