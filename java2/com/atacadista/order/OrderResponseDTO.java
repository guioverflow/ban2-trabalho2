package com.atacadista.order;

import java.time.LocalDate;

public record OrderResponseDTO(Long idOrder, LocalDate orderDate) {
    public OrderResponseDTO(Order orderBean) {
        this(
                orderBean.getIdOrder(),
                orderBean.getOrderDate()
        );
    }
}
