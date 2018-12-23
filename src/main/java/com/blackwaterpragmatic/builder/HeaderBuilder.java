package com.blackwaterpragmatic.builder;

import com.blackwaterpragmatic.bean.Header;
import com.blackwaterpragmatic.helper.BinaryHelper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@SuppressWarnings({"checkstyle:magicnumber"})
public class HeaderBuilder {

	private final BinaryHelper binaryHelper;

	public HeaderBuilder(final BinaryHelper binaryHelper) {
		this.binaryHelper = binaryHelper;
	}

	public Header buildHeader(final InputStream file) throws IOException {
		// | 4 byte magic string "MPS7" | 1 byte version | 4 byte (uint32) # of records
		final byte[] logType = new byte[4];
		file.read(logType);

		final int version = file.read();

		final byte[] numberOfRecords = new byte[4];
		file.read(numberOfRecords);

		final Header header = new Header();
		header.setLogType(binaryHelper.toString(logType));
		header.setVersion(version);
		header.setNumberOfRecords(binaryHelper.toInteger(numberOfRecords));
		return header;
	}

}
