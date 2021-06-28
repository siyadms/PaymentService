package com.pexa.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pexa.model.PaymentList;
import com.pexa.model.Transaction;
import com.pexa.model.TransactionTree;

@Service("paymentServiceOptmized")
class PaymentServiceOptmizedImpl implements PaymentService{

	@Override
	public List<Transaction> schedulePayments(PaymentList pl) {
		
		// Assumption : no of txns are in the range of  0 < n < 100000
		
		//  get list element 
		// binary search tree alogrith
		// Node : 
		//  - key = from ~ to;
		//  - value = Tansaction
		//  left = 

		TransactionTree tree = new TransactionTree();
		
		for (Transaction t: pl.getTransactions()) {
			System.out.println("Adding to tree -"+t);
			tree.add(t);
		}
		
		
		return tree.getNetTransactions();
	}
	
	

}
