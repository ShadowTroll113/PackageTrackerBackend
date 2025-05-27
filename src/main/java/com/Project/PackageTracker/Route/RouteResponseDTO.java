// src/main/java/com/Project/PackageTracker/Route/dto/RouteResponseDTO.java
package com.Project.PackageTracker.Route;

import java.util.List;

public class RouteResponseDTO {
    private WarehouseDTO warehouse;
    private List<StopDTO> stops;

    public RouteResponseDTO(WarehouseDTO warehouse, List<StopDTO> stops) {
        this.warehouse = warehouse;
        this.stops = stops;
    }

    public WarehouseDTO getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseDTO warehouse) {
        this.warehouse = warehouse;
    }

    public List<StopDTO> getStops() {
        return stops;
    }

    public void setStops(List<StopDTO> stops) {
        this.stops = stops;
    }
}
