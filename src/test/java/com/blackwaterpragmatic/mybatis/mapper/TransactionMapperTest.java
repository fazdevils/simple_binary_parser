package com.blackwaterpragmatic.mybatis.mapper;

import static org.junit.Assert.assertEquals;

import com.blackwaterpragmatic.bean.Transaction;
import com.blackwaterpragmatic.constant.TransactionType;
import com.blackwaterpragmatic.spring.DataConfiguration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration(classes = {DataConfiguration.class})
@Rollback
@Transactional
@Component
public class TransactionMapperTest {
	private static List<Transaction> transactions;

	@Autowired
	private TransactionMapper transactionMapper;

	@BeforeClass
	public static void beforeClass() throws FileNotFoundException, IOException {
		transactions = new ArrayList<Transaction>() {
			{
				add(new Transaction() {
					{
						setUserId(1L);
						setTimestamp(1);
						setTransactionType(TransactionType.CREDIT);
						setAmount(BigDecimal.valueOf(1.0));
					}
				});
				add(new Transaction() {
					{
						setUserId(2L);
						setTimestamp(2);
						setTransactionType(TransactionType.CREDIT);
						setAmount(BigDecimal.valueOf(2.0));
					}
				});
				add(new Transaction() {
					{
						setUserId(1L);
						setTimestamp(3);
						setTransactionType(TransactionType.DEBIT);
						setAmount(BigDecimal.valueOf(3.0));
					}
				});
				add(new Transaction() {
					{
						setUserId(4L);
						setTimestamp(4);
						setTransactionType(TransactionType.START_AUTOPAY);
					}
				});
				add(new Transaction() {
					{
						setUserId(1L);
						setTimestamp(5);
						setTransactionType(TransactionType.CREDIT);
						setAmount(BigDecimal.valueOf(15.0));
					}
				});
				add(new Transaction() {
					{
						setUserId(6L);
						setTimestamp(6);
						setTransactionType(TransactionType.END_AUTOPAY);
					}
				});
				add(new Transaction() {
					{
						setUserId(7L);
						setTimestamp(7);
						setTransactionType(TransactionType.START_AUTOPAY);
					}
				});
			}
		};
	};

	@Before
	public void before() {
		for (final Transaction transaction : transactions) {
			transactionMapper.insert(transaction);
		}
	}

	@Test
	public void should_calculate_total() {
		final BigDecimal total = transactionMapper.calculateTotalAmount(TransactionType.CREDIT);
		assertEquals(18.0, total.doubleValue(), 0.001);
	}

	@Test
	public void should_calculate_user_total() {
		final long userId = 1L;
		final BigDecimal credit = transactionMapper.calculateTotalUserAmount(userId, TransactionType.CREDIT);
		assertEquals(16.0, credit.doubleValue(), 0.001);

		final BigDecimal debit = transactionMapper.calculateTotalUserAmount(userId, TransactionType.DEBIT);
		assertEquals(3.0, debit.doubleValue(), 0.001);

		assertEquals(13.0, credit.subtract(debit).doubleValue(), 0.001);
	}

	@Test
	public void should_count_transactions() {
		final Integer creditCount = transactionMapper.countTransactions(TransactionType.CREDIT);
		assertEquals(3, creditCount.intValue());

		final Integer debitCount = transactionMapper.countTransactions(TransactionType.DEBIT);
		assertEquals(1, debitCount.intValue());

		final Integer startAutoPayCount = transactionMapper.countTransactions(TransactionType.START_AUTOPAY);
		assertEquals(2, startAutoPayCount.intValue());

		final Integer endAutoPayCount = transactionMapper.countTransactions(TransactionType.END_AUTOPAY);
		assertEquals(1, endAutoPayCount.intValue());
	}

}
