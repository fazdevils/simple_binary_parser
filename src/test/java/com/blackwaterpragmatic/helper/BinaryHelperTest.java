package com.blackwaterpragmatic.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

public class BinaryHelperTest {

	private final BinaryHelper binaryHelper = new BinaryHelper();

	@Test
	public void should_convert_to_integer() {
		assertEquals(1, binaryHelper.toInteger(ByteBuffer.allocate(4).putInt(1).array()).intValue());
	}

	@Test
	public void should_convert_to_long() {
		assertEquals(1L, binaryHelper.toLong(ByteBuffer.allocate(8).putLong(1L).array()).longValue());
	}

	@Test
	public void should_convert_to_big_decimal() {
		assertEquals(BigDecimal.valueOf(1.0), binaryHelper.toBigDecimal(ByteBuffer.allocate(8).putDouble(1.0).array()));
	}

	@Test
	public void should_convert_to_string() {
		final String string = "string";
		assertEquals(string, binaryHelper.toString(string.getBytes()));
	}
}
