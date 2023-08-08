package com.hilltop.hotel.service;

import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.request.HotelRequestDto;
import com.hilltop.hotel.domain.request.UpdateHotelRequestDto;
import com.hilltop.hotel.domain.response.HotelResponseDto;
import com.hilltop.hotel.exception.DataNotFoundException;
import com.hilltop.hotel.exception.ExistingNameException;
import com.hilltop.hotel.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Hotel service
 */
@Service
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * This method saves new hotel into database
     *
     * @param hotelRequestDto hotelRequestDto
     */
    public void addHotel(HotelRequestDto hotelRequestDto) {
        String hotelName = hotelRequestDto.getHotelName();
        boolean existsByName = isHotelNameExist(hotelName);
        if (existsByName) {
            log.debug("Hotel with the name '{}' already exists. Cannot add duplicate.", hotelName);
            throw new ExistingNameException("Hotel name exist in database");
        }
        hotelRepository.save(new Hotel(hotelRequestDto));
        log.debug("Successfully added hotel data.");
    }

    /**
     * This method finds if the hotel exists by the given name
     *
     * @param hotelName hotelName
     * @return true/false
     */
    private boolean isHotelNameExist(String hotelName) {
        return hotelRepository.existsByHotelName(hotelName);
    }

    public void updateHotel(UpdateHotelRequestDto updateHotelRequestDto) {
        Hotel hotel = getHotelById(updateHotelRequestDto.getId());
        String hotelName = updateHotelRequestDto.getHotelName();
        boolean existsByName = isHotelNameExist(hotelName);
        if (existsByName) {
            log.debug("Hotel with the name '{}' already exists. Cannot add duplicate.", hotelName);
            throw new ExistingNameException("Hotel name exist in database");
        }
        hotel.updateHotel(updateHotelRequestDto);
        hotelRepository.save(hotel);
        log.debug("Successfully updated hotel data.");
    }

    public Hotel getHotelById(String id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Hotel not found for id: " + id));
    }

    public Page<Hotel> getAllHotel(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    public List<String> getAllCities() {
        List<Hotel> allHotels = hotelRepository.findAll();
        return allHotels.stream().map(Hotel::getCity).toList();
    }

    public List<HotelResponseDto> getHotelsByCity(String city) {
        List<Hotel> allByCity = hotelRepository.findAllByCity(city);
        return allByCity.stream().map(HotelResponseDto::new).toList();
    }

    public void deleteHotel(String id) {
        var hotel = getHotelById(id);
        hotelRepository.delete(hotel);
        log.info("Successfully deleted the hotel by id: {}", id);
    }

}
