package com.Project.PackageTracker.Route;

import com.Project.PackageTracker.Order.Order;
import com.Project.PackageTracker.Order.OrderRepository;
import com.Project.PackageTracker.Truck.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository, OrderRepository orderRepository) {
        this.routeRepository = routeRepository;
        this.orderRepository = orderRepository;
    }

    // Crea una nueva ruta
    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    // Actualiza completamente una ruta existente
    public Route updateRoute(Long id, Route routeDetails) {
        Route existingRoute = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));

        existingRoute.setName(routeDetails.getName());
        existingRoute.setDetails(routeDetails.getDetails());
        existingRoute.setStatus(routeDetails.getStatus());
        existingRoute.setStartTime(routeDetails.getStartTime());
        existingRoute.setEstimatedEndTime(routeDetails.getEstimatedEndTime());
        existingRoute.setActualEndTime(routeDetails.getActualEndTime());

        if (routeDetails.getAssignedTruck() != null) {
            existingRoute.setAssignedTruck(routeDetails.getAssignedTruck());
        }

        // Se actualiza la lista de órdenes utilizando los IDs (orderIds) si se envían en el request.
        if (routeDetails.getOrderIds() != null && !routeDetails.getOrderIds().isEmpty()) {
            existingRoute.setOrderIds(routeDetails.getOrderIds());
        }

        return routeRepository.save(existingRoute);
    }

    // Actualiza parcialmente una ruta
    public Route partialUpdateRoute(Long id, Route routeUpdates) {
        Route existingRoute = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));

        if (routeUpdates.getName() != null) {
            existingRoute.setName(routeUpdates.getName());
        }
        if (routeUpdates.getDetails() != null) {
            existingRoute.setDetails(routeUpdates.getDetails());
        }
        if (routeUpdates.getStatus() != null) {
            existingRoute.setStatus(routeUpdates.getStatus());
        }
        if (routeUpdates.getStartTime() != null) {
            existingRoute.setStartTime(routeUpdates.getStartTime());
        }
        if (routeUpdates.getEstimatedEndTime() != null) {
            existingRoute.setEstimatedEndTime(routeUpdates.getEstimatedEndTime());
        }
        if (routeUpdates.getActualEndTime() != null) {
            existingRoute.setActualEndTime(routeUpdates.getActualEndTime());
        }
        if (routeUpdates.getAssignedTruck() != null) {
            existingRoute.setAssignedTruck(routeUpdates.getAssignedTruck());
        }

        return routeRepository.save(existingRoute);
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

        // Se asigna la ruta al pedido, usando el id de la ruta
        order.setRouteId(route.getId());
        orderRepository.save(order);

        // Se actualiza la lista de orderIds de la ruta
        List<Long> orderIds = route.getOrderIds();
        if (orderIds == null) {
            orderIds = new ArrayList<>();
        }
        if (!orderIds.contains(order.getId())) {
            orderIds.add(order.getId());
        }
        route.setOrderIds(orderIds);

        return routeRepository.save(route);
    }

    // Asigna un camión a una ruta
    public Route assignTruck(Long routeId, Long truckId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + routeId));

        // Dado que no se cuenta con un TruckRepository, se crea una instancia de Truck con el id
        Truck truck = new Truck();
        truck.setId(truckId);
        route.setAssignedTruck(truck);

        return routeRepository.save(route);
    }

    // Métodos para consultas

    // Busca una ruta por el id del camión asignado
    public Route findByTruckId(Long truckId) {
        return routeRepository.findByAssignedTruckId(truckId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada para el camión con id: " + truckId));
    }

    // Retorna todas las rutas
    public List<Route> findAllRoutes() {
        return routeRepository.findAll();
    }

    // Busca una ruta por su id
    public Route findRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));
    }

    // Busca una ruta que contenga la orden dada
    public Route findByOrderId(Long orderId) {
        return routeRepository.findByOrderIdsContaining(orderId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada para la orden con id: " + orderId));
    }
}
