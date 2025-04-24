package com.Project.PackageTracker.Route;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteQueryController {

    private final RouteService routeService;

    public RouteQueryController(RouteService routeService) {
        this.routeService = routeService;
    }

    // Obtener ruta por ID de camión
    @GetMapping("/by-truck/{truckId}")
    public Route getRouteByTruckId(@PathVariable Long truckId) {
        return routeService.findByTruckId(truckId);
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

    // Obtener ruta por ID de orden
    @GetMapping("/by-order/{orderId}")
    public Object getRouteByOrderId(@PathVariable Long orderId) {
        return routeService.findByOrderId(orderId);
    }
}