package com.Project.PackageTracker.Order;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id", nullable = true)
    private Long senderId; // Solo el ID del remitente

    @Column(name = "receiver_id", nullable = true)
    private Long receiverId; // Solo el ID del destinatario

    @Column(name = "route_id", nullable = false)
    private Long routeId; // Solo el ID de la ruta

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product_id")
    private List<Long> productIds; // Lista de IDs de productos

    @Column(nullable = false)
    private String status; // Ej. "In Transit", "Delivered", "Pending"

    @Column(name = "start_address", nullable = false)
    private String startAddress; // Dirección de inicio

    @Column(name = "end_address", nullable = false)
    private String endAddress; // Dirección de destino

    @Column(name = "order_details", columnDefinition = "TEXT")
    private String orderDetails; // Detalles adicionales del pedido

    @Column(name = "shipment_type", nullable = false)
    private String shipmentType; // Tipo de envío: "send" o "receive"
}