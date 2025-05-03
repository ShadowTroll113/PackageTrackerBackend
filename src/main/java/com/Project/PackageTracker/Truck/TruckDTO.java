package com.Project.PackageTracker.Truck;

public class TruckDTO {

    private String name;
    private Long routeId;

    // Constructor vacío (requerido por Jackson)
    public TruckDTO() {
    }

    // Constructor con todos los campos
    public TruckDTO(String name, Long routeId) {
        this.name = name;
        this.routeId = routeId;
    }

    // Getters y setters
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

}
