package org.act.houserentingsystem.controller;

import lombok.RequiredArgsConstructor;
import org.act.houserentingsystem.dto.ApiResponse;
import org.act.houserentingsystem.dto.HouseDTO;
import org.act.houserentingsystem.entity.House;
import org.act.houserentingsystem.service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @PostMapping
    public ResponseEntity<ApiResponse<HouseDTO>> addHouse(@RequestBody House house, @RequestParam Long ownerId) {
        HouseDTO houseDTO = houseService.addHouse(house, ownerId);
        ApiResponse<HouseDTO> response = ApiResponse.<HouseDTO>builder()
                .success(true)
                .message("House added successfully")
                .data(houseDTO)
                .build();
        return ResponseEntity.ok(response);
    }


//    @PostMapping("/{ownerId}")
//    public ResponseEntity<ApiResponse<HouseDTO>> addHouse(@RequestBody House house, @PathVariable Long ownerId) {
//        HouseDTO houseDTO = houseService.addHouse(house, ownerId);
//        ApiResponse<HouseDTO> response = ApiResponse.<HouseDTO>builder()
//                .success(true)
//                .message("House added successfully")
//                .data(houseDTO)
//                .build();
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HouseDTO>> updateHouse(@PathVariable Long id, @RequestBody House house) {
        HouseDTO houseDTO = houseService.updateHouse(id, house);
        ApiResponse<HouseDTO> response = ApiResponse.<HouseDTO>builder()
                .success(true)
                .message("House updated successfully")
                .data(houseDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HouseDTO>>> getAllHouses() {
        List<HouseDTO> houses = houseService.getAllHouses();
        ApiResponse<List<HouseDTO>> response = ApiResponse.<List<HouseDTO>>builder()
                .success(true)
                .message("All houses fetched successfully")
                .data(houses)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HouseDTO>> getHouseById(@PathVariable Long id) {
        HouseDTO houseDTO = houseService.getHouseById(id)
                .orElseThrow(() -> new IllegalArgumentException("House not found with id " + id));
        ApiResponse<HouseDTO> response = ApiResponse.<HouseDTO>builder()
                .success(true)
                .message("House fetched successfully")
                .data(houseDTO)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("House deleted successfully")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }
}
