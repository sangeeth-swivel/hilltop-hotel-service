package com.hilltop.hotel.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * List HotelId RequestDto
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelIdRequestDto implements RequestDto {

    private List<String> hotelIds;

    /**
     * Used to validate required fields.
     *
     * @return true/false
     */
    @Override
    public boolean isRequiredFieldsAvailable() {
        return false;
    }
}