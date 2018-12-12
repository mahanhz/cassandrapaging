package com.example.cassandrapaging;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends CassandraRepository<CustomerEntity, UUID> {
}
