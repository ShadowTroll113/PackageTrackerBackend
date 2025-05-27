package com.Project.PackageTracker.Route;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByAssignedTruck_Id(Long truckId);
    Optional<Route> findByWarehouseId(Long truckId);
}
