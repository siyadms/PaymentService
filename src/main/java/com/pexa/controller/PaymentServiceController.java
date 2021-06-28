package com.pexa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pexa.model.PaymentList;
import com.pexa.model.Transaction;
import com.pexa.service.PaymentService;


@RestController
public class PaymentServiceController {
	private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(PaymentServiceController.class.getName());
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping(value="/payments/schedule",consumes = {MediaType.APPLICATION_JSON_VALUE},produces ={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Transaction>> schedulePayment(@RequestBody PaymentList pl) {
		
		log.info("Received request for api /payments/schedule "+pl);
		return new ResponseEntity<List<Transaction>>(paymentService.schedulePayments(pl),new HttpHeaders(),HttpStatus.CREATED);
	}

}
