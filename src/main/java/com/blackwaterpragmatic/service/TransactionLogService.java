package com.blackwaterpragmatic.service;

import com.blackwaterpragmatic.bean.Header;
import com.blackwaterpragmatic.bean.Transaction;
import com.blackwaterpragmatic.builder.HeaderBuilder;
import com.blackwaterpragmatic.builder.TransactionBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionLogService {

	private final HeaderBuilder headerBuilder;
	private final TransactionBuilder transactionBuilder;

	@Autowired
	public TransactionLogService(
			final HeaderBuilder headerBuilder,
			final TransactionBuilder transactionBuilder) {
		this.headerBuilder = headerBuilder;
		this.transactionBuilder = transactionBuilder;
	}

	public List<Transaction> parseTransactionLog(final String transactionFilename) throws FileNotFoundException, IOException {
		final List<Transaction> transactions = new ArrayList<>();
		final ClassPathResource logFileResource = new ClassPathResource(transactionFilename);
		try (InputStream file = logFileResource.getInputStream()) {
			final Header header = headerBuilder.buildHeader(file);
			validateHeader(header);

			for (int i = 0; i < header.getNumberOfRecords(); i++) {
				transactions.add(transactionBuilder.buildTransaction(file));
			}
		}
		return transactions;
	}

	private void validateHeader(final Header header) {
		if (!"MPS7".equals(header.getLogType())) {
			throw new UnsupportedOperationException(String.format("'%s' is not a supported transation log format.", header.getLogType()));
		}
	}

}
