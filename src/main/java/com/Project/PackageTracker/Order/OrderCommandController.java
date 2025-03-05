package com.Project.PackageTracker.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderCommandController {

    @Autowired
    private OrderService orderService;

    // Crear un nuevo pedido
    @PostMapping
    public Order createOrder(@RequestBody OrderDto newOrderDto) {
        return orderService.createOrder(newOrderDto);
    }

    // Actualizar un pedido existente
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        return orderService.updateOrder(id, orderDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}