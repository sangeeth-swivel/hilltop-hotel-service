package com.hilltop.hotel.controller;

import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.request.HotelRequestDto;
import com.hilltop.hotel.domain.request.UpdateHotelRequestDto;
import com.hilltop.hotel.domain.response.HotelListResponseDto;
import com.hilltop.hotel.domain.response.ResponseWrapper;
import com.hilltop.hotel.enums.ErrorMessage;
import com.hilltop.hotel.enums.SuccessMessage;
import com.hilltop.hotel.exception.ExistingNameException;
import com.hilltop.hotel.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Hotel controller
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/hotel")
public class HotelController extends BaseController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseWrapper> addHotel(@RequestBody HotelRequestDto hotelRequestDto) {
        try {
            if (!hotelRequestDto.isRequiredFieldsAvailable()) {
                log.debug("Required fields missing. data: {}", hotelRequestDto.toLogJson());
                return getBadRequestErrorResponse(ErrorMessage.MISSING_REQUIRED_FIELDS);
            }
            hotelService.addHotel(hotelRequestDto);
            return getSuccessResponse(SuccessMessage.SUCCESSFULLY_ADDED, null, HttpStatus.CREATED);
        } catch (ExistingNameException e) {
            log.error("Hotel name already exists. Cannot add duplicate.", e);
            return getBadRequestErrorResponse(ErrorMessage.EXISTING_NAME);
        }
    }

    @PutMapping("")
    public ResponseEntity<ResponseWrapper> updateHotel(@RequestBody UpdateHotelRequestDto updateHotelRequestDto) {
        try {
            if (!updateHotelRequestDto.isRequiredFieldsAvailableForUpdate()) {
                log.debug("Required fields missing. data: {}", updateHotelRequestDto.toLogJson());
                return getBadRequestErrorResponse(ErrorMessage.MISSING_REQUIRED_FIELDS);
            }
            hotelService.updateHotel(updateHotelRequestDto);
            return getSuccessResponse(SuccessMessage.SUCCESSFULLY_UPDATED, null, HttpStatus.OK);
        }catch (ExistingNameException e) {
            log.error("Hotel name already exists. Cannot add duplicate.", e);
            return getBadRequestErrorResponse(ErrorMessage.EXISTING_NAME);
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseWrapper> listOrSearchHotels(@RequestParam(required = false) String location,
                                                              @RequestParam(required = false) Integer paxCount) {
        HotelListResponseDto hotelListResponseDto;
        if (location == null || paxCount == null)
            hotelListResponseDto = new HotelListResponseDto(hotelService.getHotelList());
        else {
            Map<Hotel, List<Room>> hotelAndRoomsMap =
                    hotelService.getHotelsByLocationAndPaxCount(location, paxCount);
            hotelListResponseDto = new HotelListResponseDto(hotelAndRoomsMap);
        }
        log.debug("Successfully returned all hotels.");
        return getSuccessResponse(SuccessMessage.SUCCESSFULLY_RETURNED, hotelListResponseDto, HttpStatus.OK);

    }
}
