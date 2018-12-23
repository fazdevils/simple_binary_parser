package com.blackwaterpragmatic.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.blackwaterpragmatic.bean.Transaction;
import com.blackwaterpragmatic.constant.TransactionType;
import com.blackwaterpragmatic.helper.BinaryHelper;
import com.blackwaterpragmatic.test.MockHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class TransactionBuilderTest {

	@Mock
	private BinaryHelper binaryHelper;

	@Mock
	private InputStream file;

	@InjectMocks
	private TransactionBuilder transactionBuilder;

	@Test
	public void should_build_credit_transaction() throws IOException {
		when(file.read()).thenReturn(1);
		when(binaryHelper.toInteger(any(byte[].class))).thenReturn(2);
		when(binaryHelper.toLong(any(byte[].class))).thenReturn(3L);
		when(binaryHelper.toBigDecimal(any(byte[].class))).thenReturn(BigDecimal.valueOf(4.0));

		final Transaction transaction = transactionBuilder.buildTransaction(file);

		verify(file).read();
		verify(file, times(3)).read(any(byte[].class));
		verify(binaryHelper).toInteger(any(byte[].class));
		verify(binaryHelper).toLong(any(byte[].class));
		verify(binaryHelper).toBigDecimal(any(byte[].class));
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

		assertEquals(TransactionType.CREDIT, transaction.getTransactionType());
		assertEquals(2, transaction.getTimestamp().intValue());
		assertEquals(3, transaction.getUserId().intValue());
		assertEquals(BigDecimal.valueOf(4.0), transaction.getAmount());
	}

	@Test
	public void should_build_start_autopay_transaction() throws IOException {
		when(file.read()).thenReturn(2);
		when(binaryHelper.toInteger(any(byte[].class))).thenReturn(2);
		when(binaryHelper.toLong(any(byte[].class))).thenReturn(3L);

		final Transaction transaction = transactionBuilder.buildTransaction(file);

		verify(file).read();
		verify(file, times(2)).read(any(byte[].class));
		verify(binaryHelper).toInteger(any(byte[].class));
		verify(binaryHelper).toLong(any(byte[].class));
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

		assertEquals(TransactionType.START_AUTOPAY, transaction.getTransactionType());
		assertEquals(2, transaction.getTimestamp().intValue());
		assertEquals(3, transaction.getUserId().intValue());
		assertNull(transaction.getAmount());
	}

}
