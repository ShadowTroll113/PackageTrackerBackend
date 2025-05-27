package com.Project.PackageTracker.Truck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {
    @Query("SELECT t FROM Truck t JOIN FETCH t.route r WHERE r.status = :status")
    List<Truck> findByRouteStatus(@Param("status") String status);
}
