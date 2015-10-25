package com.kevincyt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObservableBufferedReader extends Thread {
	// VARS
	private final BufferedReader reader;
	private final List<IBufferedReaderListener> listeners;

	public ObservableBufferedReader(BufferedReader reader) {
		this.reader = reader;
		this.listeners = new ArrayList<IBufferedReaderListener>();
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

	// Runnable / Thread
	@Override
	public void run() {
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				// TODO: TEMP
				try {
					Thread.sleep((long) Math.random()*1000000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				notifyListeners(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Finalize/cleanup?
		}
	}
}
