package com.kevincyt.io;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

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
public class ParallelBufferedReader {
	private final List<IBufferedReaderListener> listeners;
	private ObservableBufferedReader[] observableReaders;

	public ParallelBufferedReader(BufferedReader... readers) {
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
	 * Starts the buffered readers by passing them to the given {@link ExecutorService}. This does NOT guarantee that
	 * the buffered readers are run (if the executor rejects them) or run completely in parallel (Not enough threads to
	 * run them all at once).
	 */
	public void start(ExecutorService executor) {
		for (ObservableBufferedReader obr : observableReaders) {
			executor.execute(obr);
		}
	}

	/**
	 * Starts the readers by spawning a thread for each.
	 */
	public void start() {
		for (ObservableBufferedReader obr : observableReaders) {
			new Thread(obr).start();
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

		private final ParallelBufferedReader pbr;

		public BufferedReaderListener(ParallelBufferedReader pbr) {
			this.pbr = pbr;
		}

		@Override
		public void notifyLine(String line) {
			pbr.notifyListeners(line);
		}

	}
}
