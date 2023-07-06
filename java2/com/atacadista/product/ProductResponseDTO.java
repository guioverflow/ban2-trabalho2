package com.atacadista.product;


public record ProductResponseDTO(Long idProduct, String name, String description) {
    public ProductResponseDTO(Product productBean) {
        this(
                productBean.getIdProduct(),
                productBean.getName(),
                productBean.getDescription()
        );
    }
}
