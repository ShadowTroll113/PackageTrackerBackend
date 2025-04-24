package com.Project.PackageTracker.Product.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {

    // Retorna la lista de inventarios para una sucursal (utilizando el id de la sucursal)
    @Query("SELECT pi FROM ProductInventory pi WHERE pi.warehouse.id = :warehouseId")
    List<ProductInventory> findByWarehouseId(@Param("warehouseId") Long warehouseId);

    // Retorna el inventario para un producto en una sucursal específica
    @Query("SELECT pi FROM ProductInventory pi WHERE pi.product.id = :productId AND pi.warehouse.id = :warehouseId")
    Optional<ProductInventory> findByProductIdAndWarehouseId(@Param("productId") Long productId, @Param("warehouseId") Long warehouseId);
}
