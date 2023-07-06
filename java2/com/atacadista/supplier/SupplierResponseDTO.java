package com.atacadista.supplier;

public record SupplierResponseDTO(Long idSupplier, String name, String phone, String email, String CNPJ) {
    public SupplierResponseDTO(Supplier supplierBean) {
        this(
                supplierBean.getIdSupplier(),
                supplierBean.getName(),
                supplierBean.getPhone(),
                supplierBean.getEmail(),
                supplierBean.getCNPJ()
        );
    }
}
