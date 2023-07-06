package com.atacadista.establishment;

import com.atacadista.product.Product;
import com.atacadista.relations.Stock;

import java.util.List;

public record EstablishmentResponseDTO(Long idEstablishment, String phone, String CNPJ, List<Stock> stockList) {
    public EstablishmentResponseDTO(Establishment establishmentBean) {
        this(
                establishmentBean.getIdEstablishment(),
                establishmentBean.getPhone(),
                establishmentBean.getCNPJ(),
                establishmentBean.getStockProducts()
        );
    }
}
