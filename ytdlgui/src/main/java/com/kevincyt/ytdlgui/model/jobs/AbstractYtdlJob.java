package com.kevincyt.ytdlgui.model.jobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kevincyt.io.ParallellBufferedReader;
import com.kevincyt.process.ProcessEndNotifier;

public abstract class AbstractYtdlJob {
	// VARS
	private YtdlJobState jobState;
	private final List<String> arguments;
	private Process process;
	private ParallellBufferedReader reader;

	private final List<IYtdlJobStateListener> listeners;

	// CONS
	/**
	 * Creates a YtdlProcess that will be run with the given arguments. The parameters will not be checked for
	 * validity. In the case of erroneous parameters, an exception will be thrown upon starting this process.
	 */
	public AbstractYtdlJob(String ytdlPathString, List<String> arguments) {
		this.arguments = arguments;
		this.listeners = new ArrayList<IYtdlJobStateListener>();
		this.setState(YtdlJobState.WAITING);
	}

	/**
	 * @see #AbstractYtdlProcess(List)
	 */
	public AbstractYtdlJob(String ytdlPathString, String... arguments) {
		this(ytdlPathString, Arrays.asList(arguments));
	}

	// METHODS
	/**
	 * Creates and starts the process.
	 */
	public void start() {
		try {
			this.setState(YtdlJobState.RUNNING);
			process = new ProcessBuilder().command(arguments).start();
			reader = new ParallellBufferedReader(new BufferedReader(new InputStreamReader(
					process.getInputStream())), new BufferedReader(new InputStreamReader(
					process.getErrorStream())));
			// Thread waits for end of the process. Triggers state switch to FINISHED.
			new ProcessEndNotifier(process).addProcessEndListener(new ProcessStateListener(this));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * If this job is running, it will be stopped (the backed process gets destroyed). Sets this jobstate to
	 * cancelled afterwards. If this job has already finised this method has no effect.
	 */
	public void cancel() {
		if(getState() == YtdlJobState.FINISHED) {
			return;
		}
		if(getProcess() != null) {
			getProcess().destroy();
		}
		setState(YtdlJobState.CANCELLED);
	}

	// OBSERVER
	public void addJobStateListener(IYtdlJobStateListener listener) {
		this.listeners.add(listener);
	}

	public void removeJobStateListener(IYtdlJobStateListener listener) {
		this.listeners.remove(listener);
	}

	public void notifyJobStateChange() {
		for (IYtdlJobStateListener listener : listeners) {
			listener.onJobStateChanged(this, getState());
		}
	}

	// GETS / SETS
	public String getProcessArguments() {
		String result = "";
		for (String arg : arguments) {
			result += " " + arg;
		}
		return result;
	}

	protected Process getProcess() {
		return process;
	}

	public YtdlJobState getState() {
		return jobState;
	}

	public void setState(YtdlJobState state) {
		if(getState() == state) {
			return;
		} // else
		this.jobState = state;
		notifyJobStateChange();
	}

	public ParallellBufferedReader getReader() {
		if(getState().equals(YtdlJobState.RUNNING)) {
			return reader;
		} else {
			return null;
		}
	}

}
