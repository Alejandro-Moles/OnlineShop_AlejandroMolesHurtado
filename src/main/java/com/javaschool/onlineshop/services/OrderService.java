package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.OrderRequestDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.mapper.OrderMapper;
import com.javaschool.onlineshop.models.Order;
import com.javaschool.onlineshop.models.Payment;
import com.javaschool.onlineshop.models.Status;
import com.javaschool.onlineshop.models.Delivery;
import com.javaschool.onlineshop.models.ShopUser;
import com.javaschool.onlineshop.models.UserAddress;
import com.javaschool.onlineshop.repositories.DeliveryRepository;
import com.javaschool.onlineshop.repositories.OrderRepository;
import com.javaschool.onlineshop.repositories.PaymentRepository;
import com.javaschool.onlineshop.repositories.StatusRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import com.javaschool.onlineshop.repositories.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaymentRepository paymentRepository;
    private final StatusRepository statusRepository;
    private final DeliveryRepository deliveryRepository;
    private final ShopUserRepository shopUserRepository;
    private final UserAddressRepository userAddressRepository;

    public OrderRequestDTO saveOrder(OrderRequestDTO orderDTO){
        Order order = createOrderEntity(orderDTO, new Order());
        orderRepository.save(order);
        return createOrderDTO(order);
    }

    @Transactional(readOnly = true)
    private Payment findPayment(String type){
        return paymentRepository.findByType(type).orElseThrow(() -> new NoExistData("This payment method don't exist"));
    }

    @Transactional(readOnly = true)
    private Status findStatus(String type){
        return statusRepository.findByType(type).orElseThrow(() -> new NoExistData("This status don't exist"));
    }

    @Transactional(readOnly = true)
    private Delivery findDelivery(String type){
        return deliveryRepository.findByType(type).orElseThrow(() -> new NoExistData("This delivery don't exist"));
    }

    @Transactional(readOnly = true)
    private ShopUser findShopUser(String mail){
        return shopUserRepository.findByMail(mail).orElseThrow(() -> new NoExistData("This shop user don't exist"));
    }

    @Transactional(readOnly = true)
    private UserAddress findUserAddress(String apartament, String home, String street){
        return userAddressRepository.findByApartamentAndHomeAndStreet(apartament, home, street).orElseThrow(() -> new NoExistData("This user address don't exist"));
    }

    private OrderRequestDTO createOrderDTO(Order order){
        return orderMapper.createOrderDTO(order);
    }

    private Order createOrderEntity(OrderRequestDTO orderDTO, Order order){
        order.setPayment(findPayment(orderDTO.getPayment()));
        order.setStatus(findStatus(orderDTO.getStatus()));
        order.setDelivery(findDelivery(orderDTO.getDelivery()));
        order.setShopUser(findShopUser(orderDTO.getMail()));
        order.setPay_status(orderDTO.getPayStatus());
        order.setIsDeleted(orderDTO.getIsDeleted());
        order.setUserAddress(findUserAddress(orderDTO.getApartament(), orderDTO.getHome(), orderDTO.getStreet()));
        order.setDate(orderDTO.getOrderDate());
        return order;
    }
}
