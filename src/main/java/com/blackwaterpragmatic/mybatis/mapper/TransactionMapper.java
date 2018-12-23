package com.blackwaterpragmatic.mybatis.mapper;

import com.blackwaterpragmatic.bean.Transaction;
import com.blackwaterpragmatic.constant.TransactionType;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface TransactionMapper {

	void insert(Transaction transaction);

	BigDecimal calculateTotalAmount(
			@Param("transactionType") TransactionType transactionType);

	BigDecimal calculateTotalUserAmount(
			@Param("userId") Long userId,
			@Param("transactionType") TransactionType transactionType);

	Integer countTransactions(
			@Param("transactionType") TransactionType transactionType);
}
