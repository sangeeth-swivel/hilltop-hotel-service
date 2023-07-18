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
            if (sortedRoomList.isEmpty())
                sortedRoomList = getPossibleRoomsForPaxCount(hotel.getRooms(), paxCount);
            if (!sortedRoomList.isEmpty())
                hotelAndRoomsMap.put(hotel, sortedRoomList);
        }
        return hotelAndRoomsMap;
    }

    /**
     * This method is used to get possible rooms for pax count.
     *
     * @param roomSet  roomSet
     * @param paxCount paxCount
     * @return room list
     */
    private List<Room> getPossibleRoomsForPaxCount(Set<Room> roomSet, int paxCount) {
        List<Room> sortedRoomList = roomSet.stream()
                .filter(room -> room.getMaxPeople() > paxCount && room.getMaxPeople() <= paxCount + 2)
                .collect(Collectors.toList());
        if (sortedRoomList.isEmpty()) {
            Optional<Room> optionalRoom = roomSet.stream()
                    .filter(room -> room.getMaxPeople() < paxCount)
                    .findFirst();
            if (optionalRoom.isPresent()) {
                sortedRoomList = getRoomCombination(optionalRoom.get(), roomSet, paxCount);
            }
        }
        return sortedRoomList;
    }

    /**
     * This method is used to return multiple rooms to fulfill pax count.
     *
     * @param possibleMaximumPaxRoom possibleMaximumPaxRoom
     * @param roomSet                roomSet
     * @param paxCount               paxCount
     * @return room list
     */
    private List<Room> getRoomCombination(Room possibleMaximumPaxRoom, Set<Room> roomSet, int paxCount) {
        List<Room> sortedRoomList = new ArrayList<>();
        sortedRoomList.add(possibleMaximumPaxRoom);
        int totalPaxCount = possibleMaximumPaxRoom.getMaxPeople();
        List<Room> paxCountDescendingRoomList = roomSet.stream()
                .sorted(Comparator.comparing(Room::getMaxPeople).reversed())
                .filter(room -> !room.equals(possibleMaximumPaxRoom))
                .collect(Collectors.toList());
        for (Room room : paxCountDescendingRoomList) {
            while (totalPaxCount + room.getMaxPeople() <= paxCount) {
                sortedRoomList.add(room);
                totalPaxCount += room.getMaxPeople();
                if (totalPaxCount == paxCount)
                    return sortedRoomList;
            }
        }
        return List.of();
    }

}
