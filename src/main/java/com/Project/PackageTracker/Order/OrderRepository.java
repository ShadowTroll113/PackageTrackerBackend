package com.Project.PackageTracker.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByRouteId(Long storeId);
    List<Order> findByStoreId(Long storeId);
    List<Order> findByWarehouseId(Long warehouseId);
    List<Order> findByRouteIdAndStatus(Long storeId, String status);

}
