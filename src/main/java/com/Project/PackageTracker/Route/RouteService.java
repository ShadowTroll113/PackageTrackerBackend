package com.Project.PackageTracker.Route;

import com.Project.PackageTracker.Branch.Branch;
import com.Project.PackageTracker.Branch.BranchRepository;
import com.Project.PackageTracker.Order.Order;
import com.Project.PackageTracker.Order.OrderRepository;
import com.Project.PackageTracker.Truck.Truck;
import com.Project.PackageTracker.Truck.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final OrderRepository orderRepository;
    private final TruckRepository truckRepository;
    private final BranchRepository branchRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository,
                        OrderRepository orderRepository,
                        TruckRepository truckRepository,
                        BranchRepository branchRepository) {
        this.routeRepository = routeRepository;
        this.orderRepository = orderRepository;
        this.truckRepository = truckRepository;
        this.branchRepository = branchRepository;
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


        return routeRepository.save(existing);
    }

    // Actualiza parcialmente una ruta existente
    public Route partialUpdateRoute(Long id, Route updates) {
        // 1) Cargo la ruta existente (entidad gestionada)
        Route existing = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));

        // 2) Guarda referencia al camión antiguo
        Truck oldTruck = existing.getAssignedTruck();

        // 3) Aplica los parches sobre campos simples...
        if (updates.getName() != null)             existing.setName(updates.getName());
        if (updates.getDetails() != null)          existing.setDetails(updates.getDetails());
        if (updates.getStatus() != null)           existing.setStatus(updates.getStatus());
        if (updates.getStartTime() != null)        existing.setStartTime(updates.getStartTime());
        if (updates.getEstimatedEndTime() != null) existing.setEstimatedEndTime(updates.getEstimatedEndTime());
        if (updates.getActualEndTime() != null)    existing.setActualEndTime(updates.getActualEndTime());

        // 4) Carga el nuevo camión COMO ENTIDAD GESTIONADA (o null)
        Truck newTruck = null;
        if (updates.getAssignedTruck() != null) {
            Long newTruckId = updates.getAssignedTruck().getId();
            newTruck = truckRepository.findById(newTruckId)
                    .orElseThrow(() -> new RuntimeException("Camión no encontrado con id: " + newTruckId));
        }
        existing.setAssignedTruck(newTruck);

        // 5) Salva la ruta (asigna assigned_truck_id correctamente)
        Route saved = routeRepository.save(existing);

        // 6) Actualiza routeId en los trucks:
        if (oldTruck != null && (newTruck == null || !oldTruck.getId().equals(newTruck.getId()))) {
            oldTruck.setRouteId(null);
            truckRepository.save(oldTruck);
        }
        if (newTruck != null) {
            newTruck.setRouteId(saved.getId());
            truckRepository.save(newTruck);
        }

        return saved;
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


        return routeRepository.save(route);
    }

    public Route assignTruck(Long routeId, Long truckId) {
        // 1) Cargo ambas entidades
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + routeId));
        Truck truck = truckRepository.findById(truckId)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado con id: " + truckId));

        // 2) Sincronizo en memoria los objetos
        route.setAssignedTruck(truck);
        // no hace falta llamar aquí a truck.setRoute(route) porque el setter de Route ya lo hace

        // 3) Persiste la ruta (actualiza assigned_truck_id)
        Route saved = routeRepository.save(route);

        // 4) Persiste el routeId en trucks
        truck.setRouteId(saved.getId());
        truckRepository.save(truck);

        // 5) Devuelvo la ruta
        return saved;
    }

    public List<Route> findAllRoutes() {
        return routeRepository.findAll();
    }

    public Route findRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));
    }

    public Optional<RouteResponseDTO> findByTruckId(Long truckId) {
        // 1) Buscas la ruta
        Optional<Route> optRoute = routeRepository.findByAssignedTruck_Id(truckId);
        if (!optRoute.isPresent()) {
            return Optional.empty();
        }
        Route route = optRoute.get();

        // 2) Obtienes pedidos “en camino”
        List<Order> orders = orderRepository.findByRouteId(route.getId());
        if (orders.isEmpty()) {
            return Optional.empty();
        }

        // 3) Obtienes almacén
        Optional<Branch> optWh = branchRepository.findById(orders.get(0).getWarehouseId());
        if (!optWh.isPresent()) {
            return Optional.empty();
        }
        Branch wh = optWh.get();
        WarehouseDTO warehouseDTO = new WarehouseDTO(wh.getLatitude(), wh.getLongitude());

        // 4) Construyes la lista de paradas, saltándote las que no existan
        List<StopDTO> stops = orders.stream()
                .map(order -> branchRepository.findById(order.getStoreId())
                        .map(br -> new StopDTO(br.getId(), br.getLatitude(), br.getLongitude()))
                        .orElse(null)
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return Optional.of(new RouteResponseDTO(warehouseDTO, stops));
    }



    public List<Route> findByWarehouseId(Long warehouseId) {
        return (List<Route>) routeRepository.findByWarehouseId(warehouseId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada para warehouse Id: " + warehouseId));
    }
}
