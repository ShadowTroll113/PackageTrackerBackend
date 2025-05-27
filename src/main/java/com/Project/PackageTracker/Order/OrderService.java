// OrderService.java
package com.Project.PackageTracker.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByBranch(Long branchId) {
        if(orderRepository.findByStoreId(branchId).isEmpty())
        return orderRepository.findByWarehouseId(branchId);
        else return orderRepository.findByStoreId(branchId);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(OrderDto orderDto) {
        Order order = new Order();
        mapDtoToEntity(orderDto, order);
        return orderRepository.save(order);
    }

    public Optional<Order> updateOrder(Long id, OrderDto orderDto) {
        return orderRepository.findById(id).map(existingOrder -> {
            mapDtoToEntity(orderDto, existingOrder);
            return orderRepository.save(existingOrder);
        });
    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void mapDtoToEntity(OrderDto dto, Order entity) {
        entity.setStoreId(dto.getStoreId());
        entity.setWarehouseId(dto.getWarehouseId());
        entity.setRouteId(dto.getRouteId());
        // Convertir la lista de OrderProductDto a OrderProduct
        if (dto.getOrderProducts() != null) {
            entity.setOrderProducts(
                    dto.getOrderProducts().stream()
                            .map(opDto -> new OrderProduct(opDto.getProductId(), opDto.getQuantity()))
                            .collect(Collectors.toList())
            );
        }
        entity.setStatus(dto.getStatus());
        entity.setOrderDetails(dto.getOrderDetails());
    }
}