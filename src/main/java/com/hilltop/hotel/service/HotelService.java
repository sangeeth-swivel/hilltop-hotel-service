package com.hilltop.hotel.service;

import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.request.HotelRequestDto;
import com.hilltop.hotel.domain.request.UpdateHotelRequestDto;
import com.hilltop.hotel.exception.DataNotFoundException;
import com.hilltop.hotel.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public void addHotel(HotelRequestDto hotelRequestDto) {
        hotelRepository.save(new Hotel(hotelRequestDto));
        log.debug("Successfully added hotel data.");
    }

    public void updateHotel(UpdateHotelRequestDto updateHotelRequestDto) {
        Hotel hotel = getHotelById(updateHotelRequestDto.getId());
        hotel.updateHotel(updateHotelRequestDto);
        hotelRepository.save(hotel);
        log.debug("Successfully updated hotel data.");
    }

    public List<Hotel> getHotelList() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(String id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Hotel not found for id: " + id));
    }

    public Map<Hotel, List<Room>> getHotelsByLocationAndPaxCount(String location, int paxCount) {
        List<Hotel> hotelList = hotelRepository.findByHotelLocation(location);
        Map<Hotel, List<Room>> hotelAndRoomsMap = new HashMap<>();
        for (Hotel hotel : hotelList) {
            List<Room> sortedRoomList = hotel.getRooms().stream()
                    .filter(room -> room.getMaxPeople() == paxCount).collect(Collectors.toList());
        }
        return hotelAndRoomsMap;
    }
}
