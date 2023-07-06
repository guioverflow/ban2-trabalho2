package com.atacadista.order;

import com.atacadista.employee.Employee;
import com.atacadista.establishment.Establishment;
import com.atacadista.product.Product;
import com.atacadista.relations.OrderContains;
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
@Node("Pedido")
public class Order {
    @Id
    @GeneratedValue
    private Long idOrder;

    @Property("data_pedido")
    private LocalDate orderDate;

    @Relationship(type = "PEDIDO_PARA", direction = Relationship.Direction.OUTGOING)
    private Supplier supplier;

    @Relationship(type = "PEDIDO_POR", direction = Relationship.Direction.OUTGOING)
    private Employee employee;

    @Relationship(type = "FEITO_PARA", direction = Relationship.Direction.OUTGOING)
    private Establishment establishment;

    @Relationship(type = "CONTEM", direction = Relationship.Direction.OUTGOING)
    private List<OrderContains> orderContains;

    public Order(OrderRequestDTO data) {
        this.orderDate = data.orderDate();
    }

    public void askedFor(Supplier supplier) {
        this.supplier = supplier;
    }

    public void askedBy(Employee employee) {
        this.employee = employee;
    }

    public void askedTo(Establishment establishment) {
        this.establishment = establishment;
    }

    public void contains(OrderContains orderContains) {
        if (this.orderContains != null)
            this.orderContains.add(orderContains);
        else this.orderContains = List.of(orderContains);
    }

    public List<Object> toList() {
        List<Object> saleList = new ArrayList<>();
        saleList.add(this.getOrderDate());

        return saleList;
    }
}
