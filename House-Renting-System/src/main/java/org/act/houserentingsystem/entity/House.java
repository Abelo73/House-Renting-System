package org.act.houserentingsystem.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "houses")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal price;

    private String location;

    private int bedrooms;

    private boolean isNegotiable;

    @ElementCollection
    @CollectionTable(name = "house_amenities", joinColumns = @JoinColumn(name = "house_id"))
    @Column(name = "amenity")
    private List<String> amenities;

    @ElementCollection
    @CollectionTable(name = "house_images", joinColumns = @JoinColumn(name = "house_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    // Relationship with Owner (User)
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;


    // Additional fields for renting

    // Use matching types:
    private String status;
    private Boolean furnished;
    private Integer rentDurationMonths;
    private String contactNumber;
    private BigDecimal depositAmount;
    private Instant createdOn;
    private Instant updatedOn;

    @PrePersist
    protected void onCreate() {
        this.createdOn = java.time.Instant.now();
        this.updatedOn = this.createdOn;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedOn = java.time.Instant.now();
    }
}