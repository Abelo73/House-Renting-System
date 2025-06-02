package org.act.houserentingsystem.service;

import org.act.houserentingsystem.dto.HouseDTO;
import org.act.houserentingsystem.entity.House;

import java.util.List;
import java.util.Optional;

public interface HouseService {

    HouseDTO addHouse(House house, Long ownerId);

    HouseDTO updateHouse(Long id, House house);

    void deleteHouse(Long id);

    List<HouseDTO> getAllHouses();

    Optional<HouseDTO> getHouseById(Long id);

    // HouseService.java (interface)
    List<HouseDTO> filterHouses(Double minPrice, Double maxPrice, String location, List<String> amenities);
}
