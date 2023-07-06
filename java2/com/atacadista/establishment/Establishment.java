package com.atacadista.establishment;

import com.atacadista.relations.Stock;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("Estabelecimento")
public class Establishment {
    @Id
    @GeneratedValue
    private Long idEstablishment;

    @Property("telefone")
    private String phone;

    @Property("cnpj")
    private String CNPJ;

    @Relationship(type = "ESTOCA", direction = Relationship.Direction.OUTGOING)
    private List<Stock> stockProducts;

    public void stocks(Stock stock) {
        this.stockProducts.add(stock);
    }

    public Establishment(String phone, String CNPJ) {
        this.phone = phone;
        this.CNPJ = CNPJ;
    }

    public List<Object> toList() {
        List<Object> establishmentList = new ArrayList<>();
        establishmentList.add(this.getIdEstablishment());
        establishmentList.add(this.getPhone());
        establishmentList.add(this.getCNPJ());

        return establishmentList;
    }
}
