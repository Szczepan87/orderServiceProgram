package com.szczepanski.lukasz;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderTest {

    private String tooLongName() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i <= 255; i++) {
            s.append("a");
        }
        return s.toString();
    }

    @Test
    public void OrderConstructorTest() {
        Order order = new Order("1", 1, "Bułka", 1, BigDecimal.valueOf(0.32));
        assertEquals("1", order.getClientID());
        assertEquals(1, order.getRequestId());
        assertEquals("Bułka", order.getName());
        assertEquals(1, order.getQuantity());
        assertEquals(BigDecimal.valueOf(0.32), order.getPrice());
    }

    @Test
    public void wrongInputInConstructorClientIdTest() {
        Order order = new Order("1234567", 1, "Bułka", 1, BigDecimal.valueOf(0.32));
        assertEquals(order, new Order("------", 1, "Bułka", 1, BigDecimal.valueOf(0.32)));
    }

    @Test
    public void wrongInputInConstructorRequestIdTest() {
        Order order = new Order("123456", -12, "Bułka", 1, BigDecimal.valueOf(0.32));
        assertEquals(order, new Order("123456", -1, "Bułka", 1, BigDecimal.valueOf(0.32)));
    }

    @Test
    public void wrongInputInConstructorNameTest() {
        Order order = new Order("123456", 1, tooLongName(), 1, BigDecimal.valueOf(0.32));
        assertEquals(order, new Order("123456", 1, "Nieprawidłowy format!", 1, BigDecimal.valueOf(0.32)));
    }

    @Test
    public void wrongInputInConstructorQuantityTest() {
        Order order = new Order("123456", 1, "Bułka", -1, BigDecimal.valueOf(0.32));
        assertEquals(order, new Order("123456", 1, "Bułka", 0, BigDecimal.valueOf(0.32)));
    }

    @Test
    public void wrongInputInConstructorPriceTest() {
        Order order = new Order("123456", 1, "Bułka", 1, BigDecimal.valueOf(-0.32));
        Order compare = new Order("123456", 1, "Bułka", 1, BigDecimal.ZERO);
        assertEquals(order,compare);
    }
}