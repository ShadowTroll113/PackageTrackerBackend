package com.Project.PackageTracker.Route;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/routes")
public class RouteCommandController {

    private final RouteService routeService;

    public RouteCommandController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public ResponseEntity<Route> createRoute(@RequestBody Route route) {
        Route createdRoute = routeService.createRoute(route);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdRoute.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdRoute);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(
            @PathVariable Long id,
            @RequestBody Route routeDetails) {
        Route updatedRoute = routeService.updateRoute(id, routeDetails);
        return ResponseEntity.ok(updatedRoute);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Route> partialUpdateRoute(
            @PathVariable Long id,
            @RequestBody Route routeUpdates) {
        Route updatedRoute = routeService.partialUpdateRoute(id, routeUpdates);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{routeId}/assign-order/{orderId}")
    public ResponseEntity<Route> assignOrderToRoute(
            @PathVariable Long routeId,
            @PathVariable Long orderId) {
        Route updatedRoute = routeService.assignOrder(routeId, orderId);
        return ResponseEntity.ok(updatedRoute);
    }

    @PostMapping("/{routeId}/assign-truck/{truckId}")
    public ResponseEntity<Route> assignTruckToRoute(
            @PathVariable Long routeId,
            @PathVariable Long truckId) {
        Route updatedRoute = routeService.assignTruck(routeId, truckId);
        return ResponseEntity.ok(updatedRoute);
    }
}