package com.atacadista.product;

import com.atacadista.establishment.Establishment;
import com.atacadista.relations.Stock;
import com.atacadista.relations.StockReverse;
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
@Node("Produto")
public class Product {
    @Id
    @GeneratedValue
    private Long idProduct;

    @Property("nome")
    private String name;

    @Property("descricao")
    private String description;

    public Product(ProductRequestDTO data) {
        this.name = data.name();
        this.description = data.description();
    }

    public List<Object> toList() {
        List<Object> productList = new ArrayList<>();
        productList.add(this.getName());
        productList.add(this.getDescription());

        return productList;
    }
}
