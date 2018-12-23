package com.blackwaterpragmatic.bean;

import com.blackwaterpragmatic.constant.TransactionType;

import java.math.BigDecimal;

public class Transaction {
	private TransactionType transactionType;
	private Integer timestamp;
	private Long userId;
	private BigDecimal amount;

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(final TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Integer getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final Integer timestamp) {
		this.timestamp = timestamp;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}


}
