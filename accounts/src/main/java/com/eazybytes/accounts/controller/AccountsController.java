package com.eazybytes.accounts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.accounts.AccountServiceConfig;
import com.eazybytes.accounts.feignclients.CardsFeignClient;
import com.eazybytes.accounts.feignclients.LoansFeignClient;
import com.eazybytes.accounts.model.*;
import com.eazybytes.accounts.repository.AccountsRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import ch.qos.logback.core.net.ObjectWriter;


@RestController
public class AccountsController {

	
	@Autowired
	private CardsFeignClient cardsFeignClient;
	
	@Autowired
	private LoansFeignClient loansFeignClient;
	
	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private AccountServiceConfig accountServiceConfig;
	
	@GetMapping("/sayHello")
	public String sayhello()
	{
		return "Hello World!";
	}

	@GetMapping("/accounts/properties")
	public Properties getPropertyDetails() {
		Properties prop = new Properties(accountServiceConfig.getMsg(), accountServiceConfig.getBuildVersion(), accountServiceConfig.getMailDetails(), accountServiceConfig.getActiveBranches());
		return prop;
	}

	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {

		accountsRepository.findAll()
		.forEach(c -> System.out.println(c));

		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());

		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}

	}
	
	@PostMapping("/getCustomerDetails")
	//@CircuitBreaker(name="customerDetailsCircuitBreakerInAccountsService", fallbackMethod="myCustomerDetailsFallback")
	@Retry(name="retrygetCustomerDetails", fallbackMethod="retryFallbackForCustomerDetails")
	public CustomerDetails getCustomerDetails(@RequestBody Customer customer) {

	CustomerDetails customerDetails = new CustomerDetails();

	//get customer acc details
	Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());

	//make http req to /mycards --> localhost:9000/myCards
	List<Cards> cards = cardsFeignClient.getCardsDetails(customer);

	//make http req to /myLoans --> localhost:8090/myLoans
	List<Loans> loans = loansFeignClient.getLoansDetails(customer);

	customerDetails.setAccount(accounts);
	customerDetails.setCards(cards);
	customerDetails.setLoans(loans);

	return customerDetails;

	}
	
	public CustomerDetails retryFallbackForCustomerDetails(Customer customer, RuntimeException e)
	{
		CustomerDetails customerDetails = new CustomerDetails();

		//get customer acc details
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());

		//make http req to /mycards --> localhost:9000/myCards
		//List<Cards> cards = cardsFeignClient.getCardsDetails(customer);

		//make http req to /myLoans --> localhost:8090/myLoans
		List<Loans> loans = loansFeignClient.getLoansDetails(customer);

		customerDetails.setAccount(accounts);
		//customerDetails.setCards(cards);
		customerDetails.setLoans(loans);

		return customerDetails;
	}
	
	public CustomerDetails myCustomerDetailsFallback(Customer customer, Throwable t)
	{
		CustomerDetails customerDetails = new CustomerDetails();

		//get customer acc details
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());

		//make http req to /mycards --> localhost:9000/myCards
		//List<Cards> cards = cardsFeignClient.getCardsDetails(customer);

		//make http req to /myLoans --> localhost:8090/myLoans
		List<Loans> loans = loansFeignClient.getLoansDetails(customer);

		customerDetails.setAccount(accounts);
		//customerDetails.setCards(cards);
		customerDetails.setLoans(loans);

		return customerDetails;
	}

}
