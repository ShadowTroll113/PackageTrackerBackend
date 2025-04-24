package com.Project.PackageTracker.Route;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByAssignedTruckId(Long truckId);
    Optional<Route> findByOrderIdsContaining(Long orderId);
}
