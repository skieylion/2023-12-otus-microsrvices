package com.example.order.repository;

import com.example.order.domain.OrderDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderDetailsRepository extends CrudRepository<OrderDetails, UUID> {
}
