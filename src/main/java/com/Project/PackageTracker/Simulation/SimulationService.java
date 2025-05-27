package com.Project.PackageTracker.Simulation;

import com.Project.PackageTracker.Route.RouteRepository;
import com.Project.PackageTracker.Truck.TruckRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Lazy;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SimulationService {

    private  final RouteRepository routeRepo;
    private final TruckRepository truckRepo;
    private final SimulationService self;  // proxy para @Async

    /** Una cola de coordenadas por cada camión */
    private final Map<Long, LinkedBlockingQueue<Coordinate>> queues = new ConcurrentHashMap<>();

    /** Intervalo global (ms) entre cada actualización de TODAS las colas */
    private final AtomicLong intervalMs = new AtomicLong(5000);

    /** Controla que el hilo de simulación se inicie sólo una vez */
    private final AtomicBoolean running = new AtomicBoolean(false);

    public SimulationService(RouteRepository routeRepo, TruckRepository truckRepo,
                             @Lazy SimulationService self) {
        this.routeRepo = routeRepo;
        this.truckRepo = truckRepo;
        this.self = self;
    }

    /**
     * Arranca la simulación global: un hilo que cada intervaloMs itera
     * todas las colas y actualiza punto a punto con su propia transacción.
     * Se detiene automáticamente si todas las colas quedan vacías,
     * o manualmente vía stopGlobalSimulation().
     */
    @Async
    public void startGlobalSimulation() {
        // sólo arranca si no hay otro hilo corriendo
        if (!running.compareAndSet(false, true)) {
            return;
        }

        try {
            while (running.get() && !Thread.currentThread().isInterrupted()) {
                // Auto-stop: si no hay camiones o todas las colas vacías, salimos
                if (queues.isEmpty() ||
                        queues.values().stream().allMatch(Queue::isEmpty)) {
                    running.set(false);
                    break;
                }

                // 1) Por cada camión con ruta en cola, extrae un punto y actualiza
                for (Map.Entry<Long, LinkedBlockingQueue<Coordinate>> e : queues.entrySet()) {
                    Long truckId = e.getKey();
                    Coordinate coord = e.getValue().poll();  // non-blocking
                    if (coord != null) {
                        // cada coordenada en su propia tx
                        self.updateTruckPosition(truckId, coord);
                    }
                }

                // 2) Espera el intervalo actual antes de la siguiente tanda
                Thread.sleep(intervalMs.get());
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        } finally {
            // asegurar flag reseteado
            running.set(false);
        }
    }

    /**
     * Detiene la simulación global de forma manual.
     * El hilo parará en la próxima iteración.
     */
    public void stopGlobalSimulation() {
        running.set(false);
    }

    /**
     * Registra un camión para simulación (crea su cola y arranca
     * la simulación global si no lo estaba).
     */
    public void start(Long truckId) {
        queues.computeIfAbsent(truckId, id -> new LinkedBlockingQueue<>());
        // usamos el proxy para que @Async funcione
        self.startGlobalSimulation();
    }

    /**
     * Añade una lista de coordenadas a la cola de un camión ya registrado.
     * También re-arranca la simulación si ya había terminado por auto-stop.
     */
    public void addRoute(Long truckId, List<Coordinate> route) {
        LinkedBlockingQueue<Coordinate> q = queues.computeIfAbsent(truckId, id -> new LinkedBlockingQueue<>());
        q.addAll(route);
        // si ya no había hilo corriendo, lo volvemos a arrancar
        if (!running.get()) {
            self.startGlobalSimulation();
        }
    }

    /**
     * Recupera la ruta (puntos pendientes) actualmente en cola para un camión.
     */
    public List<Coordinate> getRoute(Long truckId) {
        LinkedBlockingQueue<Coordinate> q = queues.get(truckId);
        if (q == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(q);
    }

    /**
     * Ajusta dinámicamente el intervalo (ms) que usa el hilo global.
     */
    public void setIntervalMs(long ms) {
        if (ms <= 0) {
            throw new IllegalArgumentException("Intervalo debe ser positivo");
        }
        intervalMs.set(ms);
    }

    /**
     * Actualiza posición de un camión en su propia transacción.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateTruckPosition(Long truckId, Coordinate coord) {
        truckRepo.findById(truckId).ifPresent(truck -> {
            truck.setLatitude(coord.lat());
            truck.setLongitude(coord.lng());
            truckRepo.save(truck);
        });
    }
}
