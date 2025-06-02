package org.act.houserentingsystem.service;

import lombok.RequiredArgsConstructor;
import org.act.houserentingsystem.dto.HouseDTO;
import org.act.houserentingsystem.dto.OwnerDTO;
import org.act.houserentingsystem.entity.House;
import org.act.houserentingsystem.entity.User;
import org.act.houserentingsystem.repository.HouseRepository;
import org.act.houserentingsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    @Override
    public HouseDTO addHouse(House house, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner with id " + ownerId + " not found"));
        house.setOwner(owner);
        House savedHouse = houseRepository.save(house);
        return mapToHouseDTO(savedHouse);
    }

    @Override
    public HouseDTO updateHouse(Long id, House house) {
        House existingHouse = houseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("House with id " + id + " not found"));

        existingHouse.setTitle(house.getTitle());
        existingHouse.setDescription(house.getDescription());
        existingHouse.setPrice(house.getPrice());
        existingHouse.setLocation(house.getLocation());
        existingHouse.setBedrooms(house.getBedrooms());
        existingHouse.setNegotiable(house.isNegotiable());
        existingHouse.setAmenities(house.getAmenities());
        existingHouse.setImageUrls(house.getImageUrls());

        House updatedHouse = houseRepository.save(existingHouse);
        return mapToHouseDTO(updatedHouse);
    }

    @Override
    public void deleteHouse(Long id) {
        if (!houseRepository.existsById(id)) {
            throw new IllegalArgumentException("House with id " + id + " not found");
        }
        houseRepository.deleteById(id);
    }

    @Override
    public List<HouseDTO> getAllHouses() {
        List<House> houses = houseRepository.findAll();
        return houses.stream()
                .map(this::mapToHouseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HouseDTO> getHouseById(Long id) {
        return houseRepository.findById(id)
                .map(this::mapToHouseDTO);
    }

    // Helper mapping method
    private HouseDTO mapToHouseDTO(House house) {
        User owner = house.getOwner();
        OwnerDTO ownerDTO = new OwnerDTO(
                owner.getId(),
                owner.getUsername(),
                owner.getEmail(),
                owner.getRole()
        );

        return new HouseDTO(
                house.getId(),
                house.getTitle(),
                house.getDescription(),
                house.getPrice(),
                house.getLocation(),
                house.getBedrooms(),
                house.getAmenities(),
                house.getImageUrls(),
                ownerDTO,
                house.isNegotiable(),

                // New fields below:
                house.getStatus(),
                house.getFurnished(),
                house.getRentDurationMonths(),
                house.getContactNumber(),
                house.getDepositAmount(),
                house.getCreatedOn(),
                house.getUpdatedOn()
        );
    }



}
