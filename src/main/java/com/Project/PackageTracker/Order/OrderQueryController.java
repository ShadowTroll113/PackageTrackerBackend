package com.Project.PackageTracker.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderQueryController {

    @Autowired
    private OrderService orderService;

    // Obtener todos los paquetes
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Obtener un paquete por ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrdersById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
