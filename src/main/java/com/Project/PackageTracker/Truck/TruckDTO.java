package com.Project.PackageTracker.Truck;

public class TruckDTO {
    private String name;
    private Long routeId;
    private Long warehouseId;

    // Constructor por defecto requerido por Jackson
    public TruckDTO() {
    }

    // (Opcional) Constructor con todos los campos
    public TruckDTO(String name, Long routeId, Long warehouseId) {
        this.name = name;
        this.routeId = routeId;
        this.warehouseId = warehouseId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getRouteId() {
        return routeId;
    }
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }
    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}
