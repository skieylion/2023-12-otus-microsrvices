package com.example.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderDetails.OrderDetailsId.class)
public class OrderDetails {
    @Id
    @Column(name = "order_id")
    private UUID orderId;
    @Id
    @Column(name = "product_id")
    private UUID productId;
    @Column(name = "quantity")
    private BigDecimal quantity;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class OrderDetailsId implements Serializable {
        private UUID orderId;
        private UUID productId;
    }
}
