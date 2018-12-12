package com.example.cassandrapaging;

import com.datastax.driver.core.*;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/")
public class CustomerController {

    private CustomerRepository customerRepository;
    private SessionFactory session;

    public CustomerController(CustomerRepository customerRepository,
                              SessionFactory session) {
        this.customerRepository = customerRepository;
        this.session = session;
    }

    @GetMapping(path = "/repo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> pagingUsingRepository(
            @RequestParam final int limit) {

        final CassandraPageRequest cassandraPageRequest = CassandraPageRequest.of(PageRequest.of(0, limit), null);

        Slice<CustomerEntity> slice = customerRepository.findAll(cassandraPageRequest);


        if (slice.hasNext()) {
            return ResponseEntity.ok("This slice has more pages");
        }

        return ResponseEntity.ok("There are no more pages");
    }

    @GetMapping(path = "/resultset", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> pagingUsingResultSet(@RequestParam final int limit,
                                                       @RequestParam final boolean useIsExhausted) {
        Statement st = new SimpleStatement("select * from cust_t;");
        st.setFetchSize(limit);

        ResultSet rs = session.getSession().execute(st);
        PagingState nextPage = rs.getExecutionInfo().getPagingState();

        int remaining = rs.getAvailableWithoutFetching();

        final List<CustomerEntity> customerEntities = new ArrayList<>();

        for (Row row : rs) {
            customerEntities.add(new CustomerEntity(row.getUUID("cust_id"),
                    row.getString("first_name"),
                    row.getString("last_name")));

            if (--remaining == 0) {
                break;
            }
        }

        if (useIsExhausted) {
            if (nextPage != null && !rs.isExhausted()) {
                return ResponseEntity.ok("This slice has more pages");
            }
        } else {
            if (nextPage != null) {
                return ResponseEntity.ok("This slice has more pages");
            }
        }

        return ResponseEntity.ok("There are no more pages");
    }
}
