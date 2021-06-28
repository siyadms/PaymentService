package com.pexa.service;


import com.pexa.model.PaymentList;
import com.pexa.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service("paymentService")
class PaymentServiceImpl implements PaymentService{

	@Override
	public List<Transaction> schedulePayments(PaymentList pl) {
		
		Map<String, Long> bankTxnMap = getCumulativeTxnMap(pl.getTransactions());
		
		List<Transaction> netTransactions = new ArrayList<>();
		List<String> usedKeys = new ArrayList<>();
		for (String key: bankTxnMap.keySet()) {

			if (!usedKeys.contains(key)) {
				String[] from_toArry = key.split("~");
				String to_from_key = from_toArry[1]+"~"+from_toArry[0];
				Long amount1 = bankTxnMap.get(key);
				Long amount2 = bankTxnMap.get(to_from_key);
				if (amount2 == null) {
					netTransactions.add(new Transaction(key+amount1,from_toArry[0],from_toArry[1],amount1));
					
				}
				else if (amount1 - amount2 >0) {
					netTransactions.add(new Transaction(key+amount1,from_toArry[0],from_toArry[1],(amount1 - amount2)));
					usedKeys.add(to_from_key);
				}
				else {
					netTransactions.add(new Transaction(key+amount1,from_toArry[1],from_toArry[0],(amount2 - amount1)));
					usedKeys.add(to_from_key);
				}
			}
		}
		
		return netTransactions;
	}
	
	private Map<String, Long> getCumulativeTxnMap(List<Transaction> transactions){
		Map<String, Long> bankTxnMap = new HashMap<>(); 
		for ( Transaction t :transactions) {
			String key = t.getFrom()+"~"+t.getTo();
			Long amount = bankTxnMap.get(key);
			if (amount == null) {
				bankTxnMap.put(key,t.getAmount());
			}
			else {
				bankTxnMap.put(key,amount+t.getAmount());
			}
		}
		return bankTxnMap;
		
	}
	

}
