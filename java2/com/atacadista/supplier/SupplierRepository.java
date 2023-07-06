package com.atacadista.supplier;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SupplierRepository extends Neo4jRepository<Supplier, Long> {
}
