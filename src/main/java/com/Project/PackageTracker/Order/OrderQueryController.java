// OrderQueryController.java - small improvement
package com.Project.PackageTracker.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderQueryController {

    @Autowired
    private OrderService orderService;

    // 1) Cuando exista el query-param branchId
    @GetMapping(params = "branchId")
    public ResponseEntity<List<Order>> getOrdersByBranch(
            @RequestParam("branchId") Long branchId) {
        List<Order> orders = orderService.getOrdersByBranch(branchId);
        return ResponseEntity.ok(orders);
    }

    // 2) Cuando no venga branchId, devuelve todos
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

