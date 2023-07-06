package com.atacadista.sale;

import com.atacadista.employee.Employee;
import com.atacadista.establishment.Establishment;
import com.atacadista.product.Product;
import com.atacadista.relations.OrderContains;
import com.atacadista.relations.SaleContains;
import com.atacadista.supplier.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("Venda")
public class Sale {
    @Id
    @GeneratedValue
    private Long idSale;

    @Property("data_venda")
    private LocalDate saleDate;

    @Relationship(type = "VENDEU_EM", direction = Relationship.Direction.OUTGOING)
    private Establishment establishment;

    @Relationship(type = "FEITA_POR", direction = Relationship.Direction.OUTGOING)
    private Employee employee;

    @Relationship(type = "CONTEM", direction = Relationship.Direction.OUTGOING)
    private List<SaleContains> saleContains;

    public Sale(SaleRequestDTO data) {
        this.saleDate = data.saleDate();
    }

    public void madeIn(Establishment establishment) {
        this.establishment = establishment;
    }
    public void madeBy(Employee employee) {
        this.employee = employee;
    }

    public void contains(SaleContains saleContains) {
        if (this.saleContains != null)
            this.saleContains.add(saleContains);
        else this.saleContains = List.of(saleContains);
    }

    public List<Object> toList() {
        List<Object> saleList = new ArrayList<>();
        saleList.add(this.getSaleDate());

        return saleList;
    }
}
