package org.act.houserentingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Owner DTO without password
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
}
