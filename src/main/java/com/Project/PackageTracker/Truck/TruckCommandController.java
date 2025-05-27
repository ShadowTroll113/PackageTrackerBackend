package com.Project.PackageTracker.Truck;

import com.Project.PackageTracker.Truck.Truck;
import com.Project.PackageTracker.Truck.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trucks")
public class TruckCommandController {

    @Autowired
    private TruckService truckService;

    // Crear un nuevo camión
    @PostMapping
    public ResponseEntity<Truck> createTruck(@RequestBody TruckDTO truckDto) {
        try {
            Truck truck = truckService.createTruck(truckDto);
            return ResponseEntity.ok(truck);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Actualizar un camión existente
    @PutMapping("/{id}")
    public ResponseEntity<Truck> updateTruck(
            @PathVariable Long id,
            @RequestBody Truck truck
    ) {
        return truckService.updateTruck(id, truck)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un camión
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        boolean deleted = truckService.deleteTruck(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
