package com.kevincyt.io;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Reads two {@link BufferedReader}s in parallel. Prioritizes the first reader over the second. Ie. Will only
 * read from the second reader if the first reader returns null.
 */
public class ParallellBufferedReader {
	private final BufferedReader readerOne;
	private final BufferedReader readerTwo;
	private String lineOne;
	private String lineTwo;

	public ParallellBufferedReader(BufferedReader readerOne, BufferedReader readerTwo) {
		this.readerOne = readerOne;
		this.readerTwo = readerTwo;
		lineOne = lineTwo = null;
	}

	public String readLine() throws IOException {
		lineOne = readerOne.readLine();
		if(lineOne != null) {
			return lineOne;
		}
		lineTwo = readerTwo.readLine();
		if(lineTwo != null) {
			return lineTwo;
		}
		return null;
	}
}
