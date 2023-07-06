package com.atacadista.relations;

import com.atacadista.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RelationshipProperties
public class OrderContains {
    @RelationshipId
    @GeneratedValue
    private Long idOrderProduct;

    @Property("quantidade")
    private Integer quantity;

    @Property("valor_unitario")
    private Float price;

    @TargetNode
    private Product product;
}
