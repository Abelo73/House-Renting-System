package org.act.houserentingsystem.entity;


import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class HouseSpecification {

    public static Specification<House> filterBy(Double minPrice, Double maxPrice, String location, List<String> amenities) {
        return (Root<House> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate p = cb.conjunction();

            if (minPrice != null) {
                p = cb.and(p, cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                p = cb.and(p, cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if (location != null && !location.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
            }
            if (amenities != null && !amenities.isEmpty()) {
                Join<House, String> amenityJoin = root.join("amenities");
                Predicate amenitiesPredicate = cb.disjunction();
                for (String amenity : amenities) {
                    amenitiesPredicate = cb.or(amenitiesPredicate, cb.like(cb.lower(amenityJoin), "%" + amenity.toLowerCase() + "%"));
                }
                p = cb.and(p, amenitiesPredicate);
            }

            return p;
        };
    }
}