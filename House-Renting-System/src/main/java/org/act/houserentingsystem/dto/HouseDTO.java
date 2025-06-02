package org.act.houserentingsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

// House DTO wrapping the owner DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String location;
    private int bedrooms;
    private List<String> amenities;
    private List<String> imageUrls;
    private OwnerDTO owner;
    private boolean negotiable;

    // Use matching types:
    private String status;
    private Boolean furnished;
    private Integer rentDurationMonths;
    private String contactNumber;
    private BigDecimal depositAmount;
    private Instant createdOn;
    private Instant updatedOn;

}
