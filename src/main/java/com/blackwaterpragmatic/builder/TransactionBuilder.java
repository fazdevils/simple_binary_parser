package com.blackwaterpragmatic.builder;

import com.blackwaterpragmatic.bean.Transaction;
import com.blackwaterpragmatic.constant.TransactionType;
import com.blackwaterpragmatic.helper.BinaryHelper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@SuppressWarnings({"checkstyle:magicnumber"})
public class TransactionBuilder {

	private final BinaryHelper binaryHelper;

	public TransactionBuilder(final BinaryHelper binaryHelper) {
		this.binaryHelper = binaryHelper;
	}

	public Transaction buildTransaction(final InputStream file) throws IOException {
		// | 1 byte record type enum | 4 byte (uint32) Unix timestamp | 8 byte (uint64) user ID | 8 byte (float64) amount (optional)
		final int transactionType = file.read();

		final byte[] timestamp = new byte[4];
		file.read(timestamp);

		final byte[] userId = new byte[8];
		file.read(userId);

		final Transaction transaction = new Transaction();
		transaction.setTransactionType(TransactionType.getValue(transactionType));
		transaction.setTimestamp(binaryHelper.toInteger(timestamp));
		transaction.setUserId(binaryHelper.toLong(userId));

		switch (transaction.getTransactionType()) {
			case CREDIT:
			case DEBIT:
				final byte[] amount = new byte[8];
				file.read(amount);
				transaction.setAmount(binaryHelper.toBigDecimal(amount));
				break;
			default:
				break;
		}

		return transaction;
	}

}
