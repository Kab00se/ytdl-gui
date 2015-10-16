package com.kevincyt.ytdlgui.model.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kevincyt.io.ParallellBufferedReader;
import com.kevincyt.ytdlgui.model.jobs.state.IYtdlJobState;
import com.kevincyt.ytdlgui.model.jobs.state.YtdlWaitingState;

public abstract class AbstractYtdlJob {
	// VARS
	private IYtdlJobState jobState; // Does most of the functions
	private final List<String> arguments;

	private final List<IYtdlJobStateListener> listeners;

	// CONS
	/**
	 * Creates a YtdlProcess that will be run with the given arguments. The parameters will not be checked for
	 * validity. In the case of erroneous parameters, an exception will be thrown upon starting this process.
	 */
	public AbstractYtdlJob(String ytdlPathString, List<String> arguments) {
		if(ytdlPathString == null || ytdlPathString.isEmpty()){
			arguments.add(0, "youtube-dl");
		} else{
			arguments.add(0, ytdlPathString);
		}
		
		this.arguments = arguments;
		this.listeners = new ArrayList<IYtdlJobStateListener>();
		this.setState(new YtdlWaitingState(this));
	}

	/**
	 * @see #AbstractYtdlProcess(List)
	 */
	public AbstractYtdlJob(String ytdlPathString, String... arguments) {
		this(ytdlPathString, Arrays.asList(arguments));
	}

	// METHODS - State delegated
	/**
	 * Creates and starts the process. If it has finished or has been cancelled this has no effect.
	 */
	public void start() {
		getState().start();
	}

	/**
	 * If this job is running, it will be stopped (the backed process gets destroyed). Sets this job's state
	 * to cancelled afterwards. If this job has already finished this method has no effect.
	 */
	public void cancel() {
		getState().cancel();
	}

	/**
	 * Finalizes this job.
	 */
	public void finish() {
		getState().finish();
	}

	/**
	 * @return A {@link ParallellBufferedReader} if the process has started/finished.
	 * @throws IllegalStateException
	 *             The job state has no reader available.
	 */
	public ParallellBufferedReader getReader() throws IllegalStateException {
		return getState().getReader();
	}

	// OBSERVER
	public void addJobStateListener(IYtdlJobStateListener listener) {
		this.listeners.add(listener);
	}

	public void removeJobStateListener(IYtdlJobStateListener listener) {
		this.listeners.remove(listener);
	}

	protected void notifyJobStateChange(IYtdlJobState oldState, IYtdlJobState newState) {
		for (IYtdlJobStateListener listener : listeners) {
			listener.onJobStateChanged(this, oldState, newState);
		}
	}

	// GETS / SETS
	public List<String> getArgumentsList() {
		return arguments;
	}

	public String getArgumentsString() {
		String result = "";
		for (String arg : arguments) {
			result += " " + arg;
		}
		return result.trim();
	}

	public IYtdlJobState getState() {
		return jobState;
	}

	public void setState(IYtdlJobState newState) {
		if(getState() == newState) {
			return;
		}
		IYtdlJobState oldState = getState();
		this.jobState = newState;
		notifyJobStateChange(oldState, newState);
	}

	public boolean isRunning(){
		return getState().isRunning();
	}
	
	public boolean isWaiting(){
		return getState().isWaiting();
	}
}
