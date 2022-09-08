package com.eazybytes.accounts.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eazybytes.accounts.model.Cards;
import com.eazybytes.accounts.model.Customer;

@FeignClient("cards")
public interface CardsFeignClient {
	
	@PostMapping("/myCards")
	public List<Cards> getCardsDetails(@RequestBody Customer customer);

}
