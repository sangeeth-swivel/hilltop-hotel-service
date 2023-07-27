package com.hilltop.hotel.service;

import com.hilltop.hotel.domain.entity.RoomType;
import com.hilltop.hotel.domain.request.RoomTypeRequestDto;
import com.hilltop.hotel.exception.DataNotFoundException;
import com.hilltop.hotel.repository.RoomTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Room type service
 */
@Service
@Slf4j
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    /**
     * This method is used to add room type.
     *
     * @param roomTypeRequestDto roomTypeRequestDto
     * @return roomType.
     */
    public RoomType addRoomType(RoomTypeRequestDto roomTypeRequestDto) {
            RoomType roomType = new RoomType(roomTypeRequestDto);
            roomTypeRepository.save(roomType);
            log.debug("Successfully added room type.");
            return roomType;
    }

    /**
     * This method is used to get room type by id.
     *
     * @param id roomTypeId
     * @return roomType.
     */
    public RoomType getRoomTypeById(String id) {
            return roomTypeRepository.findById(id)
                    .orElseThrow(() -> new DataNotFoundException("Room type not found for id: " + id));
    }
}
