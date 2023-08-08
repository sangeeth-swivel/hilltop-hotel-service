package com.hilltop.hotel.domain.response;

import com.hilltop.hotel.domain.entity.Hotel;
import com.hilltop.hotel.domain.request.HotelRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelResponseDtoTest {
    private HotelRequestDto hotelRequestDto;

    @BeforeEach
    void setUp() {
        hotelRequestDto = new HotelRequestDto();
        hotelRequestDto.setHotelName("Kandy Grand");
        hotelRequestDto.setHotelLocation("Kandy");
    }

    /**
     * This method is used to returnHotelResponseDtoConstructor()
     */
    @Test
    void Should_ReturnHotelResponseDtoConstructor() {
        String hotelName = "Kandy Grand";
        String hotelLocation = "Kandy";
        Hotel hotel = new Hotel(hotelRequestDto);

        HotelResponseDto hotelResponseDto = new HotelResponseDto(hotel);

        assertEquals(hotelName, hotelResponseDto.getHotelName(), "Hotel name should be set correctly.");
        assertEquals(hotelLocation, hotelResponseDto.getHotelLocation(), "Hotel location should be set correctly.");

        assertNull(hotelResponseDto.getRooms(), "Rooms list should be initialized as null.");
    }

    @Test
    public void Should_ReturnHotelList() {
        List<HotelResponseDto> hotels = new ArrayList<>();
        Hotel hotel = new Hotel(hotelRequestDto);
        hotels.add(new HotelResponseDto(hotel));
        HotelListResponseDto responseDto = new HotelListResponseDto(hotels);
        assertEquals(hotels, responseDto.getHotelList());
    }

    @Test
    public void Should_ReturnHotelResponsePagable() {
        Hotel hotel1 = new Hotel(hotelRequestDto);
        List<Hotel> hotelList = Arrays.asList(hotel1);
        Page<Hotel> hotelPage = new PageImpl<>(hotelList);

        HotelListPageResponseDto responseDto = new HotelListPageResponseDto(hotelPage);

        assertEquals(hotelList.size(), responseDto.getHotelResponseDto().size());
        assertEquals(hotel1.getHotelName(), responseDto.getHotelResponseDto().get(0).getHotelName());
    }
}