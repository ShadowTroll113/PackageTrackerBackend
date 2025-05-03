package com.Project.PackageTracker.Truck;

import com.Project.PackageTracker.Route.Route;
import com.Project.PackageTracker.Route.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TruckService {

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private RouteRepository routeRepository;

    /**
     * Crea un nuevo camión a partir del DTO.
     * Solo campos: name y routeId.
     */
    public Truck createTruck(Truck dto) {
        Truck truck = new Truck();
        truck.setName(dto.getName());

        if (dto.getRoute() != null) {
            Route route = routeRepository.findById(dto.getRoute().getId())
                    .orElseThrow(() -> new RuntimeException("Ruta no encontrada: " + dto.getRoute()));
            truck.setRoute(route);
        }

        return truckRepository.save(truck);
    }

    /**
     * Actualiza un camión existente. Devuelve Optional.empty() si no existe.
     * Solo campos: name y routeId.
     */
    public Optional<Truck> updateTruck(Long id, Truck dto) {
        return truckRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());

                    if (dto.getRoute() != null) {
                        Route route = routeRepository.findById(dto.getRoute().getId())
                                .orElseThrow(() -> new RuntimeException("Ruta no encontrada: " + dto.getRoute()));
                        existing.setRoute(route);
                    } else {
                        existing.setRoute(null);
                    }

                    return truckRepository.save(existing);
                });
    }

    /**
     * Elimina un camión. Devuelve true si existía y se eliminó, false si no se encontró.
     */
    public boolean deleteTruck(Long id) {
        if (truckRepository.existsById(id)) {
            truckRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Recupera todos los camiones.
     */
    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    /**
     * Recupera un camión por su ID.
     */
    public Optional<Truck> getTruckById(Long id) {
        return truckRepository.findById(id);
    }


}
