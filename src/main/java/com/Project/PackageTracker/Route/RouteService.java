package com.Project.PackageTracker.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public ResponseEntity<String> createRoute(Route route) {
        // Verifica si la ruta con el mismo nombre ya existe
        if (routeRepository.findByRouteName(route.getRouteName()).isEmpty()) {
            // Si la ruta no existe, se guarda como nueva y se devuelve un 201 Created
            routeRepository.save(route);
            return ResponseEntity.status(HttpStatus.CREATED).body("Route created successfully.");  // Creación exitosa, 201 Created
        } else {
            // Si la ruta ya existe, se devuelve 200 OK pero con un mensaje indicando que no es necesario crearla
            return ResponseEntity.ok("Route already exists, no creation needed.");  // Ya existe, 200 OK
        }
    }
    public Route updateRoute(Long id, Route routeDetails) {
        Optional<Route> existingRouteOptional = routeRepository.findByRouteName(routeDetails.getRouteName());

        if (existingRouteOptional.isPresent()) {
            Route existingRoute = existingRouteOptional.get();
            existingRoute.setStartAddress(routeDetails.getStartAddress());
            existingRoute.setEndAddress(routeDetails.getEndAddress());
            existingRoute.setRouteName(routeDetails.getRouteName());
            return routeRepository.save(existingRoute);
        } else {
            throw new IllegalArgumentException("Route with id " + id + " not found.");
        }
    }

    public void deleteRoute(Long id) {
        if (routeRepository.existsById(id)) {
            routeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Route with id " + id + " not found.");
        }
    }

    public Optional<Route> getRouteById(Long id) {
        return routeRepository.findById(id).map(route -> route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

}
