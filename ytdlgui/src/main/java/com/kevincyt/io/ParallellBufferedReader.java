package com.kevincyt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads two {@link BufferedReader}s in parallel. Prioritizes the first reader over the second. Ie. Will only
 * read from the second reader if the first reader returns null.
 */
public class ParallellBufferedReader {
	private final List<IBufferedReaderListener> listeners;
	private final BufferedReader readerOne;
	private final BufferedReader readerTwo;
	private String lineOne;
	private String lineTwo;

	public ParallellBufferedReader(BufferedReader readerOne, BufferedReader readerTwo) {
		this.listeners = new ArrayList<IBufferedReaderListener>();
		ObservableBufferedReader obr = new ObservableBufferedReader(readerOne);
		obr.addListener(new IBufferedReaderListener() {
			
			@Override
			public void notifyLine(String line) {
				// TODO Auto-generated method stub
				
			}
		});
		this.readerOne = readerOne;
		this.readerTwo = readerTwo;
		lineOne = lineTwo = null;
	}
	
	// Observer
	public void addListener(IBufferedReaderListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IBufferedReaderListener listener) {
		listeners.remove(listener);
	}
	
	public void notifyListeners(String line){
		for(IBufferedReaderListener listener : listeners){
			listener.notifyLine(line);
		}
	}

	public String readLine() throws IOException {
		// TODO: ParallellBufferedReader is reading serially (one > second)
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
