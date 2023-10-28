package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public static Order getOrderById(String orderId) {
        return OrderRepository.getOrderById(orderId);
    }

    public static DeliveryPartner getPartnerById(String partnerId) {
        return OrderRepository.getPartnerById(partnerId);
    }

    public static Integer getOrderByPartnerId(String partnerId) {
        return OrderRepository.getOrderByPartnerId(partnerId);
    }

    public static List<String> getOrdersByPartnerId(String partnerId) {
        return  OrderRepository.getOrdersByPartnerId(partnerId);
    }

    public static List<String> getAllOrders() {
        return OrderRepository.getAllOrders();
    }

    public static Integer getCountOfUnassignedOrders() {
        return OrderRepository.getCountOfUnassignedOrders();
    }

    public static Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return OrderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public static String getLastDeliveryTimeByPartnerId(String partnerId) {
        return OrderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public static void deletePartnerById(String partnerId) {
        OrderRepository.deletePartnerById(partnerId);
    }

    public static void deleteOrderById(String orderId) {
        OrderRepository.deleteOrderById(orderId);
    }

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartner(String orderId, String partnerId) {
        orderRepository.addOrderPartner(orderId,partnerId);
    }
}
