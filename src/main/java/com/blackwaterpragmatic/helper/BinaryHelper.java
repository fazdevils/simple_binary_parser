package com.blackwaterpragmatic.helper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

@Component
public class BinaryHelper {

	public Integer toInteger(final byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}

	public Long toLong(final byte[] bytes) {
		return ByteBuffer.wrap(bytes).getLong();
	}

	public BigDecimal toBigDecimal(final byte[] bytes) {
		return BigDecimal.valueOf(ByteBuffer.wrap(bytes).getDouble());
	}

	public String toString(final byte[] bytes) {
		return new String(bytes);
	}

}
