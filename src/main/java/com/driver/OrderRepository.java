package com.driver;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
@Component
public class OrderRepository {
    static HashMap<String,Order> orderMap = new HashMap<>();
    static HashMap<String,DeliveryPartner> deliveryPartnerHashMap = new HashMap<>();
   static  HashMap<String,List<String>> deliveryPartnerListHashMap = new HashMap<String, List<String>>();

    public static Integer getCountOfUnassignedOrders() {
        HashSet<String> assignedOrder = new HashSet<>();
        for (String partner:deliveryPartnerListHashMap.keySet()) {
            for (String order:deliveryPartnerListHashMap.get(partner)) {
                assignedOrder.add(order);

            }

        }
        int cnt = 0;
        for (String orderId:orderMap.keySet()) {
            if(!assignedOrder.contains(orderId))
            {
                cnt++;
            }

        }
        return cnt;
    }

    public static Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String []t = time.split(":");
        int hour = Integer.valueOf(t[0]);
        int min = Integer.valueOf(t[1]);
        int totalTime = hour*60 + min;
        int cnt = 0;

        for (String order:deliveryPartnerListHashMap.get(partnerId)) {
            if(orderMap.get(order).getDeliveryTime()>totalTime)
            {
                cnt++;
            }

        }
        return cnt;
    }

    public static String getLastDeliveryTimeByPartnerId(String partnerId) {
        int lastDelivery = 0;
        for (String order:deliveryPartnerListHashMap.get(partnerId)) {
            if(orderMap.get(order).getDeliveryTime()>lastDelivery)
            {
                lastDelivery =orderMap.get(order).getDeliveryTime() ;
            }

        }
        return (lastDelivery/60)+":"+(lastDelivery%60);

    }

    public static void deletePartnerById(String partnerId) {

        for (String order:deliveryPartnerListHashMap.get(partnerId)) {
           if(orderMap.containsKey(order))
           {
               continue;
           }
           orderMap.put(order,new Order(order,"00:00"));
           deliveryPartnerListHashMap.remove(partnerId);

        }
    }

    public static void deleteOrderById(String orderId) {
        orderMap.remove(orderId);
        for (String partner:deliveryPartnerListHashMap.keySet()) {
            for (String order:deliveryPartnerListHashMap.get(partner)) {
                if (order.equals(orderId))
                {
                    List<String> orders = deliveryPartnerListHashMap.get(partner);
                    orders.remove(order);
                    deliveryPartnerListHashMap.put(partner,orders);
                }

            }

        }
    }


    public void addOrder(Order order) {
        if(order==null) return;
        orderMap.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        if(deliveryPartnerHashMap.containsKey(partnerId) || partnerId==null)
        {
            return;
        }
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        deliveryPartnerHashMap.put(partnerId,partner);
    }

    public void addOrderPartner(String orderId, String partnerId) {
        List<String> orders = deliveryPartnerListHashMap.getOrDefault(deliveryPartnerListHashMap.get(partnerId),new ArrayList<>());
        for(String id : orders)
        {
            if(id.equals(orderId)) return;
        }
        orders.add(orderId);
        deliveryPartnerListHashMap.put(partnerId,orders);

    }
    public static Order getOrderById(String orderId) {
        return orderMap.getOrDefault(orderId,null);
    }
    public static DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerHashMap.getOrDefault(partnerId,null);
    }
    public static Integer getOrderByPartnerId(String partnerId) {
        if(deliveryPartnerListHashMap.containsKey(partnerId))
        {
            return deliveryPartnerListHashMap.get(partnerId).size();
        }
        return 0;
    }
    public static List<String> getOrdersByPartnerId(String partnerId) {
        return deliveryPartnerListHashMap.getOrDefault(deliveryPartnerListHashMap.get(partnerId),new ArrayList<>());
    }
    public static List<String> getAllOrders() {
        if(orderMap.size()>0) {
            return (List<String>) orderMap.keySet();
        }
        return new ArrayList<String>();
    }

}
