package com.Project.PackageTracker.Truck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trucks")
public class TruckQueryController {

    @Autowired
    private TruckService truckService;

    /**
     * Obtener todos los camiones
     */
    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks() {
        List<Truck> trucks = truckService.getAllTrucks();
        return ResponseEntity.ok(trucks);
    }

    /**
     * Obtener un camión por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Truck> getTruckById(@PathVariable Long id) {
        return truckService.getTruckById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public List<Map<String, Object>> getActiveTrucks() {
            List<Truck> trucks = truckService.getTruckByRouteStatus("en camino");
        String timestamp = Instant.now().toString();

        return trucks.stream()
                .map(t -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", t.getId());
                    m.put("latitude", t.getLatitude());
                    m.put("longitude", t.getLongitude());
                    m.put("timestamp", timestamp);
                    return m;
                })
                .collect(Collectors.toList());
    }



}
