package com.Project.PackageTracker.Product.Inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-inventory")
public class ProductInventoryCommandController {

    @Autowired
    private ProductInventoryService productInventoryService;

    // Crea un registro de inventario para un producto
    @PostMapping
    public ResponseEntity<ProductInventory> createProductInventory(@RequestBody ProductInventoryDto dto) {
        ProductInventory created = productInventoryService.createProductInventory(dto);
        return ResponseEntity.ok(created);
    }

    // Endpoint para disminuir el stock de un producto en un almacén
    @PatchMapping("/decrease-stock")
    public ResponseEntity<ProductInventory> decreaseStock(@RequestBody DecreaseStockRequest request) {
        try {
            ProductInventory updated = productInventoryService.decreaseStock(
                    request.getProductId(), request.getBranchId(), request.getQuantity());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Clase auxiliar para recibir la petición de disminuir stock
    public static class DecreaseStockRequest {
        private Long productId;
        private Long branchId;
        private int quantity;

        public Long getProductId() {
            return productId;
        }
        public void setProductId(Long productId) {
            this.productId = productId;
        }
        public Long getBranchId() {
            return branchId;
        }
        public void setBranchId(Long branchId) {
            this.branchId = branchId;
        }
        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
