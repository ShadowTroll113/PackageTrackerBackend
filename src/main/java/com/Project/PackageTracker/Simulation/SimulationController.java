package com.Project.PackageTracker.Simulation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sim")
public class SimulationController {

    private final SimulationService simSvc;

    public SimulationController(SimulationService simSvc) {
        this.simSvc = simSvc;
    }

    /** 1) Inicia la simulación global (si no estaba en marcha). */
    @PostMapping("/start/{truckId}")
    public ResponseEntity<Void> start(@PathVariable Long truckId) {
        simSvc.start(truckId);
        return ResponseEntity.ok().build();
    }

    /** 2) Inyecta una nueva ruta para el camión dado. */
    @PostMapping("/{truckId}/route")
    public ResponseEntity<Void> addRoute(
            @PathVariable Long truckId,
            @RequestBody List<Coordinate> route
    ) {
        simSvc.addRoute(truckId, route);
        return ResponseEntity.ok().build();
    }

    /** 3) Cambia el intervalo global de simulación (ms). */
    @PostMapping("/interval")
    public ResponseEntity<Void> setInterval(@RequestParam long ms) {
        simSvc.setIntervalMs(ms);
        return ResponseEntity.ok().build();
    }

    /** 4) Recupera la ruta completa actualmente almacenada en el simulador. */
    @GetMapping("/{truckId}/route")
    public ResponseEntity<List<Coordinate>> getRoute(@PathVariable Long truckId) {
        List<Coordinate> route = simSvc.getRoute(truckId);
        if (route == null || route.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(route);
    }
}
