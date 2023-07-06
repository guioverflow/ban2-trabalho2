package com.atacadista.supplier;

import com.atacadista.establishment.Establishment;
import com.atacadista.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("Fornecedor")
public class Supplier {
    @Id
    @GeneratedValue
    private Long idSupplier;

    @Property("nome")
    private String name;

    @Property("telefone")
    private String phone;

    @Property("email")
    private String email;

    @Property("cnpj")
    private String CNPJ;

    public Supplier(SupplierRequestDTO data) {
        this.name = data.name();
        this.phone = data.phone();
        this.email = data.email();
        this.CNPJ = data.CNPJ();
    }

    public List<Object> toList() {
        List<Object> employeeList = new ArrayList<>();
        employeeList.add(this.getName());
        employeeList.add(this.getPhone());
        employeeList.add(this.getEmail());
        employeeList.add(this.getCNPJ());

        return employeeList;
    }
}
