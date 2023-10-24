package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.OrderProductsRequestDTO;
import com.javaschool.onlineshop.models.OrderProducts;
import org.springframework.stereotype.Service;

@Service
public class OrderProductsMapper {
    public OrderProductsRequestDTO createOrderProductsDTO(OrderProducts orderProducts){
        OrderProductsRequestDTO orderProductDTO = new OrderProductsRequestDTO();
        orderProductDTO.setUuid(orderProducts.getOrderProductsUuid());
        orderProductDTO.setOrderUUID(orderProducts.getOrder().getOrderUuid());
        orderProductDTO.setProductTitle(orderProducts.getProduct().getTitle());
        orderProductDTO.setQuantity(orderProducts.getQuantity());
        orderProductDTO.setIsDeleted(orderProducts.getIsDeleted());

        return orderProductDTO;
    }
}
