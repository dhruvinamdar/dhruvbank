package com.eazybytes.accounts.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.model.Loans;

@FeignClient("loans")
public interface LoansFeignClient {
	
	@PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestBody Customer customer);

}