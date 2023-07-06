package com.atacadista.establishment;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstablishmentRepository extends Neo4jRepository<Establishment, Long> {
}
