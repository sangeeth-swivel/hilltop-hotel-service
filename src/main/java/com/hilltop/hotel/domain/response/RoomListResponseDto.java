package com.hilltop.hotel.domain.response;

import com.hilltop.hotel.domain.entity.Room;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Room list responseDto
 */
@Getter
public class RoomListResponseDto implements ResponseDto {

    private List<RoomSearchResponseDto> list;
    public RoomListResponseDto(Map<String, List<Room>> dataMap, int days) {
        list = generateResponse(dataMap, days);
    }
    private List<RoomSearchResponseDto> generateResponse(Map<String, List<Room>> dataMap, int days) {
        List<RoomSearchResponseDto> roomSearchResponseDtoList = new ArrayList<>();

        for (Map.Entry<String, List<Room>> entry : dataMap.entrySet()) {
            String id = entry.getKey();
            List<RoomResponseDto> roomsList = entry.getValue()
                    .stream()
                    .map(RoomResponseDto::new)
                    .toList();
            RoomSearchResponseDto roomSearchResponseDto = new RoomSearchResponseDto(id, roomsList ,days);
            roomSearchResponseDtoList.add(roomSearchResponseDto);
        }
        return roomSearchResponseDtoList;
    }
}