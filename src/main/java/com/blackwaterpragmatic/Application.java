package com.blackwaterpragmatic;

import com.blackwaterpragmatic.bean.Transaction;
import com.blackwaterpragmatic.constant.TransactionType;
import com.blackwaterpragmatic.service.TransactionLogService;
import com.blackwaterpragmatic.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application implements ApplicationRunner {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionLogService transactionLogService;

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(final ApplicationArguments applicationArguments) throws Exception {
		final List<Transaction> transactions = transactionLogService.parseTransactionLog("txnlog.dat");
		transactionService.insert(transactions);

		System.out.println("What is the total amount in dollars of debits?");
		System.out.println(String.format("\t%.2f", transactionService.calculateTotalAmount(TransactionType.DEBIT)));
		System.out.println("What is the total amount in dollars of credits?");
		System.out.println(String.format("\t%.2f", transactionService.calculateTotalAmount(TransactionType.CREDIT)));
		System.out.println("How many autopays were started?");
		System.out.println("\t" + transactionService.getTransactionCount(TransactionType.START_AUTOPAY));
		System.out.println("How many autopays were ended?");
		System.out.println("\t" + transactionService.getTransactionCount(TransactionType.END_AUTOPAY));
		System.out.println("What is balance of user ID 2456938384156277127?");
		final long userId = 2456938384156277127L;
		System.out.println(String.format("\t%.2f", transactionService.calculateUserBalance(userId)));
	}

}
