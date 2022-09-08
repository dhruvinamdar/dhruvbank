package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankApiGatewayApplication {
	
	@Autowired
	private TokenRelayGatewayFilterFactory filterFactory;

	public static void main(String[] args) {
		SpringApplication.run(BankApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder)
	{
		return builder.routes().
				route(p -> p.path("/dhruvbank/accounts/**")
				.filters(f -> f.rewritePath("/dhruvbank/accounts/(?<segment>.*)","/${segment")
						.removeRequestHeader("Cookie"))
				.uri("lb:/accounts"))
				.build();
	}
}	
