package io.vertx.starter.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerRepository {

	private Integer ID = 1;
	private List<Customer> customers;

	public CustomerRepository() {
		customers = new ArrayList<>();
	}

	public Customer save(Customer customer) {
		log.debug("save: {}", customer);
		
		if (customer.getId() != null && customer.getId() != 0) {
			Optional<Customer> e = findById(customer.getId());
			if (e.isPresent()) {
				int idx = customers.indexOf(e.get());
				customers.set(idx, customer);
			}
		}
		else {
			customer.setId(ID++);
			customers.add(customer);
		}

		return customer;
	}

	public List<Customer> findAll() {
		log.debug("findAll");
		return customers;
	}

	public Optional<Customer> findById(Integer id) {
		log.debug("findById: {}", id);
		return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
	}

	public void delete(Integer id) {
		log.debug("delete: {}", id);
		Optional<Customer> customer = findById(id);
		customer.ifPresent(c -> customers.remove(c));
	}

}
