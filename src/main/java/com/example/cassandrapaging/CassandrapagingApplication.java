package com.example.cassandrapaging;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CassandrapagingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CassandrapagingApplication.class, args);
	}

	@Autowired
	private CustomerRepository repository;

	@PostConstruct
	public void loadData() {
		final UUID custId = UUID.randomUUID();

		List<CustomerEntity> entityList = new ArrayList<>();

		entityList.add(entity(custId, "John", "Doe"));
		entityList.add(entity(custId, "Jane", "Craft"));
		entityList.add(entity(custId, "Michael", "Jayson"));
		entityList.add(entity(custId, "Oliver", "Hunt"));
		entityList.add(entity(custId, "Lara", "Drift"));

		repository.saveAll(entityList);
	}

	public CustomerEntity entity(final UUID custId, final String firstName, final String lastName) {
		return new CustomerEntity(custId, firstName, lastName);
	}
}

