package io.vertx.starter.demo;

import lombok.Data;

@Data
public class Customer {
	private Integer id;
	private String firstName;
	private String lastName;
	private Integer age;
	private String email;
}
