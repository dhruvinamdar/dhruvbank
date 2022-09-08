/**
 * 
 */
package com.eazybytes.loans.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.loans.model.Properties;
import com.eazybytes.loans.config.LoansServiceConfig;
import com.eazybytes.loans.model.Customer;
import com.eazybytes.loans.model.Loans;
import com.eazybytes.loans.repository.LoansRepository;

/**
 * @author Eazy Bytes
 *
 */

@RestController
public class LoansController {

	@Autowired
	private LoansRepository loansRepository;
	@Autowired
	private LoansServiceConfig loansConfig;
	
	@GetMapping("/account/properties")
	public Properties getPropertyDetails() //throws JsonProcessingException
	{
		Properties properties = new Properties(loansConfig.getMsg(), loansConfig.getBuildVersion(), 
				loansConfig.getMailDetails(), loansConfig.getActiveBranches());
		return properties;
	}

	@PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestBody Customer customer) {
		List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		if (loans != null) {
			return loans;
		} else {
			return null;
		}

	}

}
