package com.pexa.service;

import java.util.List;

import com.pexa.model.PaymentList;
import com.pexa.model.Transaction;

public interface PaymentService {
	List<Transaction> schedulePayments(PaymentList pl);

}
