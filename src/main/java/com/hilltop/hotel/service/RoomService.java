package com.hilltop.hotel.service;

import com.hilltop.hotel.domain.entity.Room;
import com.hilltop.hotel.domain.entity.RoomType;
import com.hilltop.hotel.domain.request.RoomRequestDto;
import com.hilltop.hotel.domain.request.UpdateRoomRequestDto;
import com.hilltop.hotel.exception.DataNotFoundException;
import com.hilltop.hotel.exception.HillTopHotelApplicationException;
import com.hilltop.hotel.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Room service
 */
@Service
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelService hotelService;
    private final RoomTypeService roomTypeService;

    public RoomService(RoomRepository roomRepository, HotelService hotelService, RoomTypeService roomTypeService) {
        this.roomRepository = roomRepository;
        this.hotelService = hotelService;
        this.roomTypeService = roomTypeService;
    }

    /**
     * This method is used to add room detail.
     *
     * @param roomRequestDto roomRequestDto
     */
    public void addRoom(RoomRequestDto roomRequestDto) {
        RoomType roomType = roomTypeService.getRoomTypeById(roomRequestDto.getRoomTypeId());
        roomRepository.save(new Room(roomRequestDto, roomType));
        log.debug("Successfully added room data.");
    }

    /**
     * This method is used to update room detail.
     *
     * @param updateRoomRequestDto updateRoomRequestDto
     */
    public void updateRoom(UpdateRoomRequestDto updateRoomRequestDto) {
        Room room = getRoomById(updateRoomRequestDto.getId());
        RoomType roomType = roomTypeService.getRoomTypeById(updateRoomRequestDto.getRoomTypeId());
        room.updateRoom(updateRoomRequestDto, roomType);
        roomRepository.save(room);
        log.debug("Successfully updated room data.");
    }

    /**
     * This method is used to delete room detail.
     *
     * @param roomId roomId
     */
    public void deleteRoomById(String roomId) {
        roomRepository.deleteById(roomId);
        log.debug("Successfully deleted room.");
    }

    /**
     * This method is used to get room list by hotelId.
     *
     * @param hotelId    hotelId
     * @param searchTerm searchTerm
     * @return room list.
     */
    public List<Room> getRoomListByHotelIdAndSearchTerm(String hotelId, String searchTerm) {
        if (searchTerm == null)
            return roomRepository.findAllByHotelId(hotelId);
        return roomRepository.findAllByHotelIdAndRoomNoContaining(hotelId, searchTerm);
    }

    /**
     * This method is used to get room detail by roomId.
     *
     * @param roomId roomId
     * @return room details.
     */
    public Room getRoomById(String roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new DataNotFoundException("Room not found for roomId: " + roomId));
    }

    public Page<Room> getRoomPageByHotelId(Pageable pageable, String hotelId) {
        return roomRepository.findByHotelId(pageable, hotelId);
    }

    public Map<String, List<Room>> getRoomsForPaxCountAndHotelIds(int paxCount, List<String> hotelIds) {
        try {
            Map<String, List<Room>> hotelAndRoomsMap = new HashMap<>();
            for (String id : hotelIds) {
                List<Room> rooms = getRoomsByHotelId(id);
                List<Room> searchList = rooms
                        .stream().filter(room -> room.getPaxCount() == paxCount).toList();
                if (searchList.isEmpty()) {
                    searchList = findRoomsForExtraPaxCount(new HashSet<>(rooms), paxCount);
                }
                if (!searchList.isEmpty()) {
                    hotelAndRoomsMap.put(id, searchList);
                }
            }
            return hotelAndRoomsMap;
        } catch (DataAccessException e) {
            throw new HillTopHotelApplicationException("Failed to get room list by hotel ids and pax count from database.", e);
        }
    }

    /**
     * This method used to get room list by extra pax count.
     * This method returns room that can occupy extra pax count : Eg: Pax count is 5 then
     * this method will return rooms for that can occupy pax count: 6
     *
     * @param roomSet  roomSet
     * @param paxCount paxCount
     * @return Room List
     */
    public List<Room> findRoomsForExtraPaxCount(Set<Room> roomSet, int paxCount) {
        List<Room> searchList = roomSet.stream()
                .filter(room -> room.getPaxCount() >
                        paxCount && room.getPaxCount() <= paxCount + 1)
                .toList();
        if (searchList.isEmpty()) {
            Optional<Room> optionalRoom = roomSet
                    .stream().filter(room -> room.getPaxCount() < paxCount).max(Comparator.comparing(Room::getPaxCount));
            if (optionalRoom.isPresent()) {
                searchList = findMultipleRoomsForPaxCount(optionalRoom.get(), roomSet, paxCount);
            }
        }
        return searchList;
    }

    /**
     * This method return the combination of rooms:
     * Eg: PaxCount = 3 ; Then room will return pax count 1 and 2 rooms.
     *
     * @param maximumPaxRoom maximumPaxRoom
     * @param roomSet        roomSet
     * @param paxCount       paxCount
     * @return Room List
     */
    public List<Room> findMultipleRoomsForPaxCount(Room maximumPaxRoom, Set<Room> roomSet, int paxCount) {
        List<Room> searchRoomList = new ArrayList<>();
        searchRoomList.add(maximumPaxRoom);
        int totalPaxCount = maximumPaxRoom.getPaxCount();
        List<Room> sortedRoomList = roomSet.stream()
                .filter(room -> !room.equals(maximumPaxRoom))
                .sorted(Comparator.comparing(Room::getPaxCount).reversed())
                .toList();
        for (Room room : sortedRoomList) {
            int roomPaxCount = room.getPaxCount();
            if (totalPaxCount + roomPaxCount <= paxCount) {
                searchRoomList.add(room);
                totalPaxCount += roomPaxCount;
                if (totalPaxCount == paxCount) {
                    break;
                }
            }
        }
        return totalPaxCount == paxCount ? searchRoomList : Collections.emptyList();
    }

    public List<Room> getRoomsByHotelId(String hotelId) {
        try {
            return roomRepository.findByHotelId(hotelId);
        } catch (DataAccessException e) {
            log.error("Failed to get room by hotel id: {}", hotelId);
            throw new HillTopHotelApplicationException("Failed to get rooms by hotel id.", e);
        }
    }
}
