package com.blackwaterpragmatic.builder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.blackwaterpragmatic.bean.Header;
import com.blackwaterpragmatic.helper.BinaryHelper;
import com.blackwaterpragmatic.test.MockHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;

@RunWith(MockitoJUnitRunner.class)
public class HeaderBuilderTest {

	@Mock
	private BinaryHelper binaryHelper;

	@Mock
	private InputStream file;

	@InjectMocks
	private HeaderBuilder headerBuilder;

	@Test
	public void should_build_header() throws IOException {
		when(binaryHelper.toString(any(byte[].class))).thenReturn("logType");
		when(file.read()).thenReturn(1);
		when(binaryHelper.toInteger(any(byte[].class))).thenReturn(2);

		final Header header = headerBuilder.buildHeader(file);

		verify(file, times(2)).read(any(byte[].class));
		verify(file).read();
		verify(binaryHelper).toString(any(byte[].class));
		verify(binaryHelper).toInteger(any(byte[].class));
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

		assertEquals("logType", header.getLogType());
		assertEquals(1, header.getVersion().intValue());
		assertEquals(2, header.getNumberOfRecords().intValue());
	}

}
