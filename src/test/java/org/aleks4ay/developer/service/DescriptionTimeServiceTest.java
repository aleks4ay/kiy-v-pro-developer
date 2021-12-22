package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.ConnectionBase;
import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.DescriptionTimeDao;
import org.aleks4ay.developer.model.DescriptionTime;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DescriptionTimeServiceTest {
    private final ConnectionBase connectionPool = ConnectionPool.getInstance();

    @Test
    public void findAllTest() {
        List<DescriptionTime> times = new DescriptionTimeService(new DescriptionTimeDao(connectionPool)).findAll();
        System.out.println(times.size());
        assertTrue(times.size() > 1000);
    }

    @Test
    public void findAllByOrderIdTest() {
        List<DescriptionTime> times = new DescriptionTimeService(new DescriptionTimeDao(connectionPool)).findAllByOrderId("  FYH9");
        System.out.println(times);
        assertTrue(times.size() > 1);
    }
}