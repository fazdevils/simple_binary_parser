package com.blackwaterpragmatic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.blackwaterpragmatic.bean.Header;
import com.blackwaterpragmatic.bean.Transaction;
import com.blackwaterpragmatic.builder.HeaderBuilder;
import com.blackwaterpragmatic.builder.TransactionBuilder;
import com.blackwaterpragmatic.test.MockHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TransactionLogServiceTest {

	@Mock
	private HeaderBuilder headerBuilder;

	@Mock
	private TransactionBuilder transactionBuilder;

	@InjectMocks
	private TransactionLogService transactionLogService;

	@Test
	public void should_parse_transaction_log() throws IOException {
		final Header header = new Header() {
			{
				setLogType("MPS7");
				setNumberOfRecords(2);
			}
		};

		when(headerBuilder.buildHeader(any(InputStream.class))).thenReturn(header);

		final List<Transaction> transactions = transactionLogService.parseTransactionLog("txnlog.dat");

		verify(headerBuilder).buildHeader(any(InputStream.class));
		verify(transactionBuilder, times(2)).buildTransaction(any(InputStream.class));
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

		assertEquals(2, transactions.size());
	}

	@Test
	public void should_not_parse_unsupported_transaction_log() throws IOException {
		final Header header = new Header() {
			{
				setLogType("Unsupported Type");
			}
		};

		when(headerBuilder.buildHeader(any(InputStream.class))).thenReturn(header);

		try {
			transactionLogService.parseTransactionLog("txnlog.dat");
			fail();
		} catch (final UnsupportedOperationException e) {
			assertEquals("'Unsupported Type' is not a supported transation log format.", e.getMessage());
		}

		verify(headerBuilder).buildHeader(any(InputStream.class));
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));
	}
}
