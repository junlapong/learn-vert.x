package io.vertx.starter;

import java.util.Optional;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.starter.demo.Customer;
import io.vertx.starter.demo.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

	private CustomerRepository repository = new CustomerRepository();

	@Override
	public void start() {

		log.info("Start Vert.x");
		Router router = Router.router(vertx);

		router.route().handler(BodyHandler.create());

		router.get("/").handler(routingContext -> {
			routingContext.response().putHeader("content-type", "text/html").end("Hello Vert.x");
		});

		router.get("/customers").handler(this::handleListCustomer);
		router.get("/customers/:id").handler(this::handleGetCustomer);
		router.post("/customers").handler(this::handleAddCustomer);
		router.put("/customers/:id").handler(this::handleEditCustomer);
		router.delete("/customers/:id").handler(this::handleDeleteCustomer);

		vertx.createHttpServer().requestHandler(router).listen(8080);
	}

	private void handleListCustomer(RoutingContext routingContext) {
		log.debug("listCustomer");

		JsonArray arr = new JsonArray();
		repository.findAll().forEach(c -> {
			arr.add(JsonObject.mapFrom(c));
		});

		routingContext.response().putHeader("content-type", "application/json").end(arr.encodePrettily());
	}

	private void handleAddCustomer(RoutingContext routingContext) {
		log.debug("addCustomer");
		JsonObject body = routingContext.getBodyAsJson();
		HttpServerResponse response = routingContext.response();

		if (body == null) {
			response.setStatusCode(400).end();
		} else {
			repository.save(body.mapTo(Customer.class));
			response.end();
		}
	}

	private void handleGetCustomer(RoutingContext routingContext) {
		log.debug("getCustomer");
		String id = routingContext.request().getParam("id");
		HttpServerResponse response = routingContext.response();

		if (id == null) {
			response.setStatusCode(400).end();
		} else {
			Optional<Customer> customer = repository.findById(Integer.valueOf(id));
			if (!customer.isPresent()) {
				response.setStatusCode(404).end();
			} else {
				response.putHeader("content-type", "application/json")
						.end(JsonObject.mapFrom(customer.get()).encodePrettily());
			}
		}
	}

	private void handleEditCustomer(RoutingContext routingContext) {
		log.debug("editCustomer");
		String id = routingContext.request().getParam("id");
		JsonObject body = routingContext.getBodyAsJson();
		HttpServerResponse response = routingContext.response();

		if (id == null) {
			response.setStatusCode(400).end();
		} else {
			Optional<Customer> customer = repository.findById(Integer.valueOf(id));
			if (!customer.isPresent()) {
				response.setStatusCode(404).end();
			} else {
				Customer c = body.mapTo(Customer.class);
				c.setId(customer.get().getId());

				repository.save(c);
				response.end();
			}
		}
	}

	private void handleDeleteCustomer(RoutingContext routingContext) {
		log.debug("deleteCustomer");
		String id = routingContext.request().getParam("id");
		HttpServerResponse response = routingContext.response();

		if (id == null) {
			response.setStatusCode(400).end();
		} else {
			Optional<Customer> customer = repository.findById(Integer.valueOf(id));
			if (!customer.isPresent()) {
				response.setStatusCode(404).end();
			} else {
				repository.delete(Integer.valueOf(id));
				response.end();
			}
		}
	}

}
