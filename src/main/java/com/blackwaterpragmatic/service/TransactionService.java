package com.blackwaterpragmatic.service;

import com.blackwaterpragmatic.bean.Transaction;
import com.blackwaterpragmatic.constant.TransactionType;
import com.blackwaterpragmatic.mybatis.mapper.TransactionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

	private final TransactionMapper transactionMapper;

	@Autowired
	public TransactionService(
			final TransactionMapper transactionMapper) {
		this.transactionMapper = transactionMapper;
	}

	public void insert(final Transaction transaction) {
		transactionMapper.insert(transaction);
	}

	public void insert(final List<Transaction> transactions) {
		for (final Transaction transaction : transactions) {
			insert(transaction);
		}
	}

	public BigDecimal calculateTotalAmount(final TransactionType transactionType) {
		return transactionMapper.calculateTotalAmount(transactionType);
	}

	public Integer getTransactionCount(final TransactionType transactionType) {
		return transactionMapper.countTransactions(transactionType);
	}

	public BigDecimal calculateUserBalance(final Long userId) {
		final BigDecimal credit = transactionMapper.calculateTotalUserAmount(userId, TransactionType.CREDIT);
		final BigDecimal debit = transactionMapper.calculateTotalUserAmount(userId, TransactionType.DEBIT);
		return credit.subtract(debit);
	}
}
