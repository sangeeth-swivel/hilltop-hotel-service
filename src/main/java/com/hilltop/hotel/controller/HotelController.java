package com.hilltop.hotel.controller;

import com.hilltop.hotel.configuration.Translator;
import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.request.HotelRequestDto;
import com.hilltop.hotel.domain.request.UpdateHotelRequestDto;
import com.hilltop.hotel.domain.response.*;
import com.hilltop.hotel.enums.ErrorMessage;
import com.hilltop.hotel.enums.SuccessMessage;
import com.hilltop.hotel.exception.ExistingNameException;
import com.hilltop.hotel.exception.InvalidHotelException;
import com.hilltop.hotel.service.HotelService;
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

/**
 * Hotel controller
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/hotel")
public class HotelController extends BaseController {

    private final HotelService hotelService;

    public HotelController(Translator translator, HotelService hotelService) {
        super(translator);
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
        } catch (ExistingNameException e) {
            log.error("Hotel name already exists. Cannot add duplicate.", e);
            return getBadRequestErrorResponse(ErrorMessage.EXISTING_NAME);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getHotel(@PathVariable String id) {
        try {
            var hotel = hotelService.getHotelById(id);
            var hotelResponseDto = new HotelResponseDto(hotel);
            return getSuccessResponse(SuccessMessage.READ_HOTEL, hotelResponseDto, HttpStatus.OK);
        } catch (InvalidHotelException e) {
            log.error("Invalid hotel id to get hotel details.");
            return getBadRequestErrorResponse(ErrorMessage.INVALID_HOTEL_ID);
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseWrapper> getHotelList(@Min(DEFAULT_PAGE) @RequestParam int page,
                                                        @Positive @Max(PAGE_MAX_SIZE) @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(DEFAULT_SORT).descending());
        Page<Hotel> allHotelPage = hotelService.getAllHotel(pageable);
        var hotelListPageResponseDto = new HotelListPageResponseDto(allHotelPage);
        return getSuccessResponse(SuccessMessage.READ_HOTEL_LIST, hotelListPageResponseDto, HttpStatus.OK);
    }

    @GetMapping("/cities")
    public ResponseEntity<ResponseWrapper> getHotelCites() {
        List<String> allCities = hotelService.getAllCities();
        var cityListResponseDto = new CityListResponseDto(allCities);
        return getSuccessResponse(SuccessMessage.READ_HOTEL_CITIES, cityListResponseDto, HttpStatus.OK);
    }

    @GetMapping("city/{city}")
    public ResponseEntity<ResponseWrapper> getHotelsByCity(@PathVariable String city) {
        var hotelsByCity = hotelService.getHotelsByCity(city);
        var hotelListResponseDto = new HotelListResponseDto(hotelsByCity);
        return getSuccessResponse(SuccessMessage.READ_HOTELS_BY_CITY, hotelListResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteHotel(@PathVariable String id) {
        try {
            hotelService.deleteHotel(id);
            return getSuccessResponse(SuccessMessage.DELETE_HOTEL, null, HttpStatus.OK);
        } catch (InvalidHotelException e) {
            log.error("Invalid hotel id to get hotel details.");
            return getBadRequestErrorResponse(ErrorMessage.INVALID_HOTEL_ID);
        }
    }
}
