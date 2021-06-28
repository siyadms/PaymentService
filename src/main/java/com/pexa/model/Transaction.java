package com.pexa.model;

import java.util.Objects;


public class Transaction {
	public Transaction(String id, String from, String to, long amount) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.amount = amount;
	}
	String id;
	String from;
	String to;
	long amount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, from, id, to);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return amount == other.amount && Objects.equals(from, other.from) && Objects.equals(id, other.id)
				&& Objects.equals(to, other.to);
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", from=" + from + ", to=" + to + ", amount=" + amount + "]";
	}
	
	public static String getkey(Transaction value){
		return value.getFrom()+value.getTo();
	}

}
