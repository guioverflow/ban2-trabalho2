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
public class Stock {
    @RelationshipId
    @GeneratedValue
    private Long idStock;

    @Property("quantidade")
    private Integer quantity;

    @TargetNode
    private Product product;
}
