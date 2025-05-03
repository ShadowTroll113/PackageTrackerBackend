package com.Project.PackageTracker.Route;

import com.Project.PackageTracker.Order.Order;
import com.Project.PackageTracker.Order.OrderRepository;
import com.Project.PackageTracker.Truck.Truck;
import com.Project.PackageTracker.Truck.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final OrderRepository orderRepository;
    private final TruckRepository truckRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository,
                        OrderRepository orderRepository,
                        TruckRepository truckRepository) {
        this.routeRepository = routeRepository;
        this.orderRepository = orderRepository;
        this.truckRepository = truckRepository;
    }

    // Crea una nueva ruta
    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    // Actualiza completamente una ruta existente
    public Route updateRoute(Long id, Route routeDetails) {
        Route existing = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));

        existing.setName(routeDetails.getName());
        existing.setDetails(routeDetails.getDetails());
        existing.setStatus(routeDetails.getStatus());
        existing.setStartTime(routeDetails.getStartTime());
        existing.setEstimatedEndTime(routeDetails.getEstimatedEndTime());
        existing.setActualEndTime(routeDetails.getActualEndTime());

        if (routeDetails.getAssignedTruck() != null) {
            existing.setAssignedTruck(routeDetails.getAssignedTruck());
        }

        if (routeDetails.getOrderIds() != null) {
            existing.setOrderIds(new ArrayList<>(routeDetails.getOrderIds()));
        }

        return routeRepository.save(existing);
    }

    // Actualiza parcialmente una ruta existente
    public Route partialUpdateRoute(Long id, Route updates) {
        Route existing = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));

        if (updates.getName() != null)                existing.setName(updates.getName());
        if (updates.getDetails() != null)             existing.setDetails(updates.getDetails());
        if (updates.getStatus() != null)              existing.setStatus(updates.getStatus());
        if (updates.getStartTime() != null)           existing.setStartTime(updates.getStartTime());
        if (updates.getEstimatedEndTime() != null)    existing.setEstimatedEndTime(updates.getEstimatedEndTime());
        if (updates.getActualEndTime() != null)       {existing.setActualEndTime(updates.getActualEndTime());}
        existing.setAssignedTruck(updates.getAssignedTruck());
        if (updates.getOrderIds() != null)            existing.setOrderIds(new ArrayList<>(updates.getOrderIds()));

        return routeRepository.save(existing);
    }

    // Elimina una ruta por su id
    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new RuntimeException("Ruta no encontrada con id: " + id);
        }
        routeRepository.deleteById(id);
    }

    // Asigna una orden a una ruta
    public Route assignOrder(Long routeId, Long orderId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + routeId));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con id: " + orderId));

        // Vincular la orden a la ruta
        order.setRouteId(routeId);
        orderRepository.save(order);

        // Añadir la orden a la lista de la ruta
        List<Long> ids = route.getOrderIds();
        if (ids == null) {
            ids = new ArrayList<>();
        }
        if (!ids.contains(orderId)) {
            ids.add(orderId);
            route.setOrderIds(ids);
        }

        return routeRepository.save(route);
    }

    // Asigna un camión existente a una ruta
    public Route assignTruck(Long routeId, Long truckId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + routeId));
        Truck truck = truckRepository.findById(truckId)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado con id: " + truckId));

        route.setAssignedTruck(truck);
        return routeRepository.save(route);
    }

    // Métodos de consulta:

    public List<Route> findAllRoutes() {
        return routeRepository.findAll();
    }

    public Route findRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));
    }

    public Route findByTruckId(Long truckId) {
        return routeRepository.findByAssignedTruckId(truckId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada para camión id: " + truckId));
    }

    public Route findByOrderId(Long orderId) {
        return routeRepository.findByOrderIdsContaining(orderId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada para orden id: " + orderId));
    }
}
