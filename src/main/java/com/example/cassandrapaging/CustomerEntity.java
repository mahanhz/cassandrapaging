package com.example.cassandrapaging;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("cust_t")
public class CustomerEntity {
    @PrimaryKeyColumn(name = "cust_id", type = PrimaryKeyType.PARTITIONED)
    private UUID custId;

    @PrimaryKeyColumn(name = "first_name", type = PrimaryKeyType.CLUSTERED)
    private String firstName;

    @PrimaryKeyColumn(name = "last_name", type = PrimaryKeyType.CLUSTERED)
    private String lastName;

    public CustomerEntity(UUID custId, String firstName, String lastName) {
        this.custId = custId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getCustId() {
        return custId;
    }

    public void setCustId(UUID custId) {
        this.custId = custId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
