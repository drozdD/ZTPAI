package com.example.demo;

import com.example.demo.service.OrderService;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private final OrderService orderService = new OrderService();

    @Test
    void shouldReturnCorrectSumForValidList() {
        List<Double> prices = Arrays.asList(10.0, 20.0, 30.0);
        double expected = 60.0;

        double actual = orderService.calculateTotal(prices);

        assertEquals(expected, actual, "Suma powinna wynosić 60.0");
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        List<Double> prices = Collections.emptyList();

        double actual = orderService.calculateTotal(prices);

        assertEquals(0.0, actual, "Suma dla pustej listy powinna wynosić 0.0");
    }

    @Test
    void shouldThrowExceptionWhenListIsNull() {
        List<Double> prices = null;

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.calculateTotal(prices);
        }, "Powinien zostać wyrzucony wyjątek IllegalArgumentException");
    }
}
