package com.Project.PackageTracker.Truck;

import com.Project.PackageTracker.Route.Route;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "trucks")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    /**
     * Campo manual para mantener la FK route_id sincronizada
     */
    @Column(name = "route_id")
    private Long routeId;

    @OneToOne(mappedBy = "assignedTruck", cascade = CascadeType.MERGE)
    @JsonBackReference
    private Route route;

    public Truck() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
        // Sincronizar ambos lados de la relación
        if (route != null && route.getAssignedTruck() != this) {
            route.setAssignedTruck(this);
        }
    }
}
