package com.Project.PackageTracker.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    // Crear un nuevo pedido
    public Order createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setSenderId(orderDto.getSenderId());
        order.setReceiverId(orderDto.getReceiverId());
        order.setRouteId(orderDto.getRouteId());
        order.setProductIds(orderDto.getProductIds());
        order.setStatus(orderDto.getStatus());
        order.setStartAddress(orderDto.getStartAddress());
        order.setEndAddress(orderDto.getEndAddress());
        order.setOrderDetails(orderDto.getOrderDetails());
        order.setShipmentType(orderDto.getShipmentType());

        return orderRepository.save(order);
    }

    // Actualizar un pedido existente
    public Optional<Order> updateOrder(Long id, OrderDto orderDto) {
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setSenderId(orderDto.getSenderId());
            existingOrder.setReceiverId(orderDto.getReceiverId());
            existingOrder.setRouteId(orderDto.getRouteId());
            existingOrder.setProductIds(orderDto.getProductIds());
            existingOrder.setStatus(orderDto.getStatus());
            existingOrder.setStartAddress(orderDto.getStartAddress());
            existingOrder.setEndAddress(orderDto.getEndAddress());
            existingOrder.setOrderDetails(orderDto.getOrderDetails());
            existingOrder.setShipmentType(orderDto.getShipmentType());

            return orderRepository.save(existingOrder);
        });
    }

    // Eliminar un pedido
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}



