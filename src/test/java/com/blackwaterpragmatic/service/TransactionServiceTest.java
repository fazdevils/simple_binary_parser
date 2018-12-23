package com.blackwaterpragmatic.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.blackwaterpragmatic.bean.Transaction;
import com.blackwaterpragmatic.constant.TransactionType;
import com.blackwaterpragmatic.mybatis.mapper.TransactionMapper;
import com.blackwaterpragmatic.test.MockHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

	@Mock
	private TransactionMapper transactionMapper;

	@InjectMocks
	private TransactionService transactionService;

	@Test
	public void should_insert() {
		final Transaction transaction = new Transaction();

		transactionService.insert(transaction);

		verify(transactionMapper).insert(transaction);
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}

	@Test
	public void should_insert_array() {
		final List<Transaction> transactions = new ArrayList<Transaction>() {
			{
				add(new Transaction());
				add(new Transaction());
			}
		};

		transactionService.insert(transactions);

		verify(transactionMapper, times(2)).insert(any(Transaction.class));
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}

	@Test
	public void should_calculate_total_amount() {
		transactionService.calculateTotalAmount(TransactionType.CREDIT);

		verify(transactionMapper).calculateTotalAmount(TransactionType.CREDIT);
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}

	@Test
	public void should_get_transaction_count() {
		transactionService.getTransactionCount(TransactionType.CREDIT);

		verify(transactionMapper).countTransactions(TransactionType.CREDIT);
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}


	@Test
	public void should_calculate_user_balance() {
		final long userId = 1L;

		when(transactionMapper.calculateTotalUserAmount(userId, TransactionType.CREDIT)).thenReturn(BigDecimal.valueOf(10.0));
		when(transactionMapper.calculateTotalUserAmount(userId, TransactionType.DEBIT)).thenReturn(BigDecimal.valueOf(3.0));

		final BigDecimal balance = transactionService.calculateUserBalance(userId);

		verify(transactionMapper).calculateTotalUserAmount(userId, TransactionType.CREDIT);
		verify(transactionMapper).calculateTotalUserAmount(userId, TransactionType.DEBIT);
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

		assertEquals(BigDecimal.valueOf(7.0), balance);
	}
}
