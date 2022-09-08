package com.eazybytes.loans.context;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class RequestContext {

	public static final String CORRELATION_ID = "eazybank-correlation-id";

	private String correlationId = new String();

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	

}
