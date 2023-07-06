package com.atacadista.sale;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SaleRepository extends Neo4jRepository<Sale, Long> {
}
