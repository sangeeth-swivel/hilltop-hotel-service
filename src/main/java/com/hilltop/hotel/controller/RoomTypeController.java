package com.hilltop.hotel.controller;

import com.hilltop.hotel.configuration.Translator;
import com.hilltop.hotel.domain.entity.RoomType;
import com.hilltop.hotel.domain.request.RoomTypeRequestDto;
import com.hilltop.hotel.domain.response.ResponseWrapper;
import com.hilltop.hotel.domain.response.RoomTypeResponseDto;
import com.hilltop.hotel.enums.ErrorMessage;
import com.hilltop.hotel.enums.SuccessMessage;
import com.hilltop.hotel.service.RoomTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/roomType")
public class RoomTypeController extends BaseController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(Translator translator, RoomTypeService roomTypeService) {
        super(translator);
        this.roomTypeService = roomTypeService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseWrapper> addRoomType(@RequestBody RoomTypeRequestDto roomTypeRequestDto) {
        if (!roomTypeRequestDto.isRequiredFieldsAvailable()) {
            log.debug("Required fields missing. data: {}", roomTypeRequestDto.toLogJson());
            return getBadRequestErrorResponse(ErrorMessage.MISSING_REQUIRED_FIELDS);
        }
        RoomType roomType = roomTypeService.addRoomType(roomTypeRequestDto);
        log.debug("Successfully added room type.");
        return getSuccessResponse(SuccessMessage.SUCCESSFULLY_ADDED,
                new RoomTypeResponseDto(roomType), HttpStatus.CREATED);
    }
}
