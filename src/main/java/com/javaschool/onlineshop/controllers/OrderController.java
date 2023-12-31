package com.javaschool.onlineshop.controllers;

import com.javaschool.onlineshop.dto.*;
import com.javaschool.onlineshop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // Endpoint to get all orders
    @GetMapping
    public ResponseEntity<List<OrderRequestDTO>> getAllOrders() {
        List<OrderRequestDTO> result = orderService.getAllOrders();
        return ResponseEntity.ok(result);
    }

    // Endpoint to create a new order
    @PostMapping
    public ResponseEntity<OrderRequestDTO> createOrder(@RequestBody OrderRequestDTO orderDTO) {
        OrderRequestDTO result = orderService.saveOrder(orderDTO);
        return ResponseEntity.ok(result);
    }

    // Endpoint to get an order by UUID
    @GetMapping("/{uuid}")
    public ResponseEntity<OrderRequestDTO> getOrderbyUuid(@PathVariable UUID uuid) {
        OrderRequestDTO result = orderService.getOrderUuid(uuid);
        return ResponseEntity.ok(result);
    }

    // Endpoint to get all orders for a specific user by user UUID
    @GetMapping("/user/{userUuid}")
    public ResponseEntity<List<OrderRequestDTO>> getOrderbyShopUser(@PathVariable UUID userUuid) {
        List<OrderRequestDTO> result = orderService.getAllOrdersForUser(userUuid);
        return ResponseEntity.ok(result);
    }

    // Endpoint to update the status of an order by UUID and new status
    @PutMapping("/{uuid}/{status}")
    public ResponseEntity<String> updateOrder(@PathVariable UUID uuid, @PathVariable String status) {
        orderService.updateOrder(uuid, status);
        return ResponseEntity.ok("Order changed successfully");
    }

    // Endpoint to get revenue statistics based on date range
    @PostMapping("/revenueStatistic")
    public ResponseEntity<List<RevenueStatisticDTO>> getRevenuesStatistic(@RequestBody DateSelectorStatisticDTO dateDTO) {
        List<RevenueStatisticDTO> result = orderService.getRevenueStatistic(dateDTO);
        return ResponseEntity.ok(result);
    }
}
