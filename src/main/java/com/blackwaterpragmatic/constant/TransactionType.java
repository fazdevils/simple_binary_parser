package com.blackwaterpragmatic.constant;

public enum TransactionType {
	DEBIT(0x00),
	CREDIT(0x01),
	START_AUTOPAY(0x02),
	END_AUTOPAY(0x03);

	private final int id;

	private TransactionType(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static TransactionType getValue(final int id) {
		for (final TransactionType transactionType : values()) {
			if (transactionType.id == id) {
				return transactionType;
			}
		}
		return null;
	}

}
