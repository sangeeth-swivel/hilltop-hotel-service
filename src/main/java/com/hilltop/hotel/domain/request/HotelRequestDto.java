package com.hilltop.hotel.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HotelRequestDto implements RequestDto {

    private String hotelName;
    private String hotelLocation;
    private String city;
    private List<String> imageUrl;
    private String contact;
    private String email;

    @Override
    public boolean isRequiredFieldsAvailable() {
        return isNonEmpty(hotelName) && isNonEmpty(hotelLocation) && isNonEmpty(city) && isNonEmpty(contact);
    }
}
