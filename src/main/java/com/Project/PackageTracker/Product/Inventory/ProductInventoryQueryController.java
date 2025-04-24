package com.Project.PackageTracker.Product.Inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-inventory")
public class ProductInventoryQueryController {

    @Autowired
    private ProductInventoryService productInventoryService;

    // Obtener un registro de inventario por su id
    @GetMapping("/{id}")
    public ResponseEntity<ProductInventory> getProductInventoryById(@PathVariable Long id) {
        Optional<ProductInventory> inventoryOpt = productInventoryService.getProductInventoryById(id);
        return inventoryOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener todos los inventarios de productos para una sucursal (warehouse)
    @GetMapping("/branch/{branchId}/products")
    public ResponseEntity<List<ProductInventory>> getProductInventoryByBranch(@PathVariable Long branchId) {
        List<ProductInventory> list = productInventoryService.getProductInventoryByBranch(branchId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }
}
