package com.atacadista.order;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends Neo4jRepository<Order, Long> {
}
