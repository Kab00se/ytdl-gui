package com.kevincyt.io;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Reads {@link BufferedReader}s in parallel by assigning each in its own thread and subsequently listening for lines as
 * they are read. Implements observer pattern through which these lines can be obtained.
 * </p>
 * 
 * <p>
 * Note that the threads will not be started until {@link #start()} has been called.
 * </p>
 */
public class ParallellBufferedReader {
	private final List<IBufferedReaderListener> listeners;
	private ObservableBufferedReader[] observableReaders;

	public ParallellBufferedReader(BufferedReader... readers) {
		observableReaders = new ObservableBufferedReader[readers.length];
		this.listeners = new ArrayList<IBufferedReaderListener>();
		IBufferedReaderListener brListener = new BufferedReaderListener(this);
		ObservableBufferedReader obr;
		for (int i = 0; i < readers.length; i++) {
			obr = new ObservableBufferedReader(readers[i]);
			observableReaders[i] = obr;
			obr.addListener(brListener);
		}
	}

	/**
	 * Starts the buffered reader threads.
	 */
	public void start() {
		for(ObservableBufferedReader obr : observableReaders){
			obr.start();
		}
	}

	// Observer
	public void addListener(IBufferedReaderListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IBufferedReaderListener listener) {
		listeners.remove(listener);
	}

	public void notifyListeners(String line) {
		for (IBufferedReaderListener listener : listeners) {
			listener.notifyLine(line);
		}
	}

	private class BufferedReaderListener implements IBufferedReaderListener {

		private final ParallellBufferedReader pbr;

		public BufferedReaderListener(ParallellBufferedReader pbr) {
			this.pbr = pbr;
		}

		@Override
		public void notifyLine(String line) {
			pbr.notifyListeners(line);
		}

	}
}
