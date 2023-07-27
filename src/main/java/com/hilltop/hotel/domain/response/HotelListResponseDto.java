package com.hilltop.hotel.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Hotel list responseDto
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HotelListResponseDto implements ResponseDto {

    private List<HotelResponseDto> hotelList;
}
