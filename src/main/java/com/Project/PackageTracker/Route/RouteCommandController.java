package com.Project.PackageTracker.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
public class RouteCommandController {

    private final RouteService RouteService;

    @Autowired
    public RouteCommandController(RouteService RouteService) {
        this.RouteService = RouteService;
    }

    @PostMapping
    public ResponseEntity<String> createRoute(@RequestBody Route route) {
        return RouteService.createRoute(route);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @RequestBody Route route) {
        Route updatedRoute = RouteService.updateRoute(id, route);
        return ResponseEntity.ok(updatedRoute);     
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        RouteService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}