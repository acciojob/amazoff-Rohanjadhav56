package com.driver;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class OrderRepository {
    static HashMap<String,Order> orderMap = new HashMap<>();
    static HashMap<String,DeliveryPartner> deliveryPartnerHashMap = new HashMap<>();
   static  HashMap<String,List<String>> deliveryPartnerListHashMap = new HashMap<String, List<String>>();
   static HashMap<String,String> orderPartnerHashMap = new HashMap<>();

    public void addOrder(Order order) {

        orderMap.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {

        deliveryPartnerHashMap.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void addOrderPartner(String orderId, String partnerId) {
        if(orderMap.containsKey(orderId)&& deliveryPartnerHashMap.containsKey(partnerId))
        {
            orderPartnerHashMap.put(orderId,partnerId);
        }
        List<String> orders = deliveryPartnerListHashMap.getOrDefault(deliveryPartnerListHashMap.get(partnerId),new ArrayList<>());

        orders.add(orderId);
        deliveryPartnerListHashMap.put(partnerId,orders);
        DeliveryPartner deliveryPartner = deliveryPartnerHashMap.get(partnerId);
        deliveryPartner.setNumberOfOrders(orders.size());

    }
    public static Order getOrderById(String orderId) {
        return orderMap.get(orderId);
    }
    public static DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerHashMap.get(partnerId);
    }

    public static Integer getOrderByPartnerId(String partnerId) {
        return deliveryPartnerListHashMap.getOrDefault(deliveryPartnerListHashMap.get(partnerId),new ArrayList<>()).size();
    }
    public static List<String> getOrdersByPartnerId(String partnerId) {
        return deliveryPartnerListHashMap.getOrDefault(deliveryPartnerListHashMap.get(partnerId),new ArrayList<>());
    }

    public static List<String> getAllOrders() {
       List<String> orders = new ArrayList<>();
       for(String order : orderMap.keySet())
       {
           orders.add(order);
       }
       return orders;
    }

    public static Integer getCountOfUnassignedOrders() {
        return orderMap.size()-orderPartnerHashMap.size();
    }

    public static Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String []t = time.split(":");
        int hour = Integer.parseInt(t[0]);
        int min = Integer.parseInt(t[1]);
        int totalTime = hour*60 + min;
        int cnt = 0;
        List<String> orders = deliveryPartnerListHashMap.get(partnerId);

        for (String order: orders) {
            if(orderMap.containsKey(order))
            {
               if(orderMap.get(order).getDeliveryTime() > totalTime)
               {
                   cnt++;
               }
            }

        }
        return cnt;
    }

    public static String getLastDeliveryTimeByPartnerId(String partnerId) {
        int lastDelivery = 0;
        List<String> orders = deliveryPartnerListHashMap.get(partnerId);
        for (String order:orders) {
            if(orderMap.containsKey(order))
            {
                if(orderMap.get(order).getDeliveryTime() > lastDelivery)
                {
                    lastDelivery = orderMap.get(order).getDeliveryTime();
                }
            }

        }
        String HH = String.valueOf(lastDelivery/60);
        if(HH.length()<2) HH = "0"+HH ;
        String MM = String.valueOf(lastDelivery%60);
        if(MM.length()<2) MM  = "0"+MM;
        return HH+":"+MM;

    }

    public static void deletePartnerById(String partnerId) {

       deliveryPartnerHashMap.remove(partnerId);

       List<String> orders = deliveryPartnerListHashMap.get(partnerId);
       deliveryPartnerListHashMap.remove(partnerId);
       for(String order : orders)
       {
           orderPartnerHashMap.remove(order);
       }
    }

    public static void deleteOrderById(String orderId) {
        orderMap.remove(orderId);
        String partnerId = orderPartnerHashMap.get(orderId);
        orderPartnerHashMap.remove(orderId);

        deliveryPartnerListHashMap.get(partnerId).remove(orderId);
        deliveryPartnerHashMap.get(partnerId).setNumberOfOrders(deliveryPartnerListHashMap.get(partnerId).size());

        }












}
