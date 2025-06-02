package org.act.houserentingsystem.repository;


import org.act.houserentingsystem.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    // Optional: Add custom query methods here if needed
}