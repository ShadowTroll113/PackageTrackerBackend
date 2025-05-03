package com.Project.PackageTracker.Truck;

import com.Project.PackageTracker.Route.Route;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "trucks")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

   // @JsonIgnore
   @JsonBackReference
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "route_id", unique = true)
    private Route route;


    public Truck() {
    }
    @JsonProperty("routeId")
    public Long getRouteId() {
        return route != null ? route.getId() : null;
    }
    // Constructor con campos
    public Truck(String name, Route route) {
        this.name = name;
        setRoute(route); // usa el setter para sincronizar bidireccionalmente
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
        // sincroniza la cara inversa: que la ruta apunte a este camión
        if (route != null && route.getAssignedTruck() != this) {
            route.setAssignedTruck(this);
        }
    }

    public void setId(Long id) {
        this.id = id;
    }
}
