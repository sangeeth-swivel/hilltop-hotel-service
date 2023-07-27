package com.hilltop.hotel.repository;

import com.hilltop.hotel.domain.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Hotel repository
 */
public interface HotelRepository extends JpaRepository<Hotel, String> {

    /**
     * This method finds if the hotel exists by the given name
     *
     * @param name name
     * @return true/false
     */
    boolean existsByHotelName(String name);

    List<Hotel> findAllByCity(String city);
}
