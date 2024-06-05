package com.example.order.service;

import com.example.order.domain.AccountDto;
import com.example.order.domain.Order;
import com.example.order.domain.OrderCreationResponse;
import com.example.order.domain.OrderDetails;
import com.example.order.domain.OrderDetailsDto;
import com.example.order.domain.OrderCreationRequest;
import com.example.order.repository.OrderDetailsRepository;
import com.example.order.repository.OrderRepository;
import com.example.order.repository.ProductRepository;
import com.example.order.service.integration.BillingIntegrationService;
import com.example.order.service.integration.NotificationIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCreatorService {
    private static final String TAKE_OFF_MONEY = "TAKE_OFF";
    private final BillingIntegrationService billingIntegrationService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final NotificationIntegrationService notificationIntegrationService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public OrderCreationResponse createOrder(OrderCreationRequest orderCreationRequest) {
        validateProducts(orderCreationRequest);
        BigDecimal totalPrice = getTotalPrice(orderCreationRequest);
        Order order = saveOrder(orderCreationRequest);
        saveOrderDetails(orderCreationRequest, order.getId());
        takeOffMoney(totalPrice, orderCreationRequest.getUserId(), order);
        sendNotification(order, orderCreationRequest.getUserId());
        order = orderRepository.save(order);
        return OrderCreationResponse.builder()
                .orderId(order.getId())
                .userId(orderCreationRequest.getUserId())
                .paidFor(order.isPaidFor())
                .totalPrice(totalPrice)
                .build();
    }

    @SneakyThrows
    private void validateProducts(OrderCreationRequest orderCreationRequest) {
        if (orderCreationRequest.getProducts() == null || orderCreationRequest.getProducts().isEmpty()) {
            throw new BadRequestException("Can't be the order without products");
        }
        Set<UUID> productIds = orderCreationRequest.getProducts().stream()
                .map(OrderDetailsDto::getProductId).collect(Collectors.toSet());
        if (productIds.size() != orderCreationRequest.getProducts().size()) {
            throw new BadRequestException("Products are duplicated");
        }
    }

    private Order saveOrder(OrderCreationRequest orderCreationRequest) {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(orderCreationRequest.getUserId());
        order.setCreatedAt(LocalDateTime.now());
        order.setPaidFor(false);
        return orderRepository.save(order);
    }

    private void saveOrderDetails(OrderCreationRequest orderCreationRequest, UUID orderId) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        for (var productDto : orderCreationRequest.getProducts()) {
            OrderDetails details = new OrderDetails();
            details.setOrderId(orderId);
            details.setProductId(productDto.getProductId());
            details.setQuantity(productDto.getQuantity());
            orderDetails.add(details);
        }
        orderDetailsRepository.saveAll(orderDetails);
    }

    @SneakyThrows
    private BigDecimal getTotalPrice(OrderCreationRequest orderCreationRequest) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (var productDto : orderCreationRequest.getProducts()) {
            BigDecimal price = productRepository.findById(productDto.getProductId())
                    .orElseThrow(() -> new BadRequestException("The product with id '" + productDto.getProductId() + "' is not found"))
                    .getPrice();
            totalPrice = totalPrice.add(productDto.getQuantity().multiply(price));
        }
        return totalPrice;
    }

    private void takeOffMoney(BigDecimal totalPrice, UUID userId, Order order) {
        try {
            AccountDto account = billingIntegrationService.getAccountByUser(userId);
            billingIntegrationService.setBalance(account.getId(), TAKE_OFF_MONEY, totalPrice);
            order.setPaidFor(true);
        } catch (Exception ex) {
            order.setPaidFor(false);
            log.warn("Can't take off money", ex);
        }
    }

    private void sendNotification(Order order, UUID userId) {
        try {
            notificationIntegrationService.sendNotification(order.isPaidFor() ? "SUCCESS" : "FAIL", userId, order.getId());
        } catch (Exception ex) {
            log.warn("The notification for order '{}' can't be sent", order.getId(), ex);
        }
    }

}

