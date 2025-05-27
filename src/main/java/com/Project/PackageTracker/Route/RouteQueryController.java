package com.Project.PackageTracker.Route;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteQueryController {

    private final RouteService routeService;

    public RouteQueryController(RouteService routeService) {
        this.routeService = routeService;
    }


    // Obtener todas las rutas
    @GetMapping
    public List<Route> getAllRoutes() {
        return routeService.findAllRoutes();
    }

    // Obtener ruta por ID
    @GetMapping("/{id}")
    public Route getRouteById(@PathVariable Long id) {
        return routeService.findRouteById(id);
    }



    @GetMapping("/by-truck/{truckId}")
    public ResponseEntity<RouteResponseDTO> getRoute(@PathVariable Long truckId) {
        return routeService.findByTruckId(truckId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-warehouse/{warehouseId}")
    public List<Route> getRoutes(@PathVariable Long warehouseId) {
        return routeService.findByWarehouseId(warehouseId);
    }
}