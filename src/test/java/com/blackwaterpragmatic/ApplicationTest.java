package com.blackwaterpragmatic;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.blackwaterpragmatic.bean.Transaction;
import com.blackwaterpragmatic.constant.TransactionType;
import com.blackwaterpragmatic.service.TransactionLogService;
import com.blackwaterpragmatic.service.TransactionService;
import com.blackwaterpragmatic.test.MockHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.DefaultApplicationArguments;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

	@Mock
	private TransactionService transactionService;

	@Mock
	private TransactionLogService transactionLogService;

	@InjectMocks
	private Application application;


	@Test
	public void should_run_missing_duration_argument() throws Exception {

		application.run(new DefaultApplicationArguments(new String[0]));

		verify(transactionLogService).parseTransactionLog("txnlog.dat");
		verify(transactionService).insert(anyListOf(Transaction.class));
		verify(transactionService).calculateTotalAmount(TransactionType.DEBIT);
		verify(transactionService).calculateTotalAmount(TransactionType.CREDIT);
		verify(transactionService).getTransactionCount(TransactionType.START_AUTOPAY);
		verify(transactionService).getTransactionCount(TransactionType.END_AUTOPAY);
		verify(transactionService).calculateUserBalance(2456938384156277127L);
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}

}
