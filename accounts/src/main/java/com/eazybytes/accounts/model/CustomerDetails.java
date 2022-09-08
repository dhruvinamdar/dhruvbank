package com.eazybytes.accounts.model;

import java.util.List;

public class CustomerDetails {

	private Accounts account;
	private List<Cards> cards;
	private List<Loans> loans;
	public Accounts getAccount() {
		return account;
	}
	public void setAccount(Accounts account) {
		this.account = account;
	}
	public List<Cards> getCards() {
		return cards;
	}
	public void setCards(List<Cards> cards) {
		this.cards = cards;
	}
	public List<Loans> getLoans() {
		return loans;
	}
	public void setLoans(List<Loans> loans) {
		this.loans = loans;
	}

}