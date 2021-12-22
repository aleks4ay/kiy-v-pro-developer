package org.aleks4ay.developer.service;

import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.model.Page;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class KbEngineTest {

    @Test
    public void getOrdersWithDescriptionsKbTest() {
        List<Order> ordersWithDescriptionsKb = new KbEngine().getOrdersWithDescriptionsKb(new Page(1), "по № заказа");
        System.out.println("first Description: " + ordersWithDescriptionsKb.get(0).getDescriptions().get(0));
        System.out.println("first Time: " + ordersWithDescriptionsKb.get(0).getDescriptions().get(0).getTimes().get(0));
        assertTrue(ordersWithDescriptionsKb.get(0).getDescriptions().get(0).getTimes().size() > 0);
    }

    @Test
    public void getOrdersWithDescriptionsFindTest() {
        List<Order> ordersWithDescriptionsKb = new KbEngine().getOrdersWithDescriptionsFind("2021", "0000009");
        System.out.println("first Description: " + ordersWithDescriptionsKb.get(0).getDescriptions().get(0));
        System.out.println("first Time: " + ordersWithDescriptionsKb.get(0).getDescriptions().get(0).getTimes().get(0));
        assertTrue(ordersWithDescriptionsKb.get(0).getDescriptions().get(0).getTimes().size() > 0);
    }
}