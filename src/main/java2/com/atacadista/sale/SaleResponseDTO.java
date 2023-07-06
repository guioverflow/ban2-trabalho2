package com.atacadista.sale;

import java.time.LocalDate;

public record SaleResponseDTO(Long idSale, LocalDate saleDate) {
    public SaleResponseDTO(Sale saleBean) {
        this(
                saleBean.getIdSale(),
                saleBean.getSaleDate()
        );
    }
}
