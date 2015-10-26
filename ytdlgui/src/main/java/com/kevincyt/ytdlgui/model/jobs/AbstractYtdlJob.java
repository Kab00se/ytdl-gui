package com.kevincyt.ytdlgui.model.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.kevincyt.ytdlgui.model.jobs.state.IYtdlJobState;
import com.kevincyt.ytdlgui.model.jobs.state.YtdlWaitingState;

public abstract class AbstractYtdlJob {
	// VARS
	private IYtdlJobState jobState; // Does most of the functions
	private final List<String> arguments;
	// VARS - Observer List
	private final List<IYtdlJobStateListener> listeners;
	// VARS - Properties
	private final IntegerProperty id;
	private final DoubleProperty progress;
	private final StringProperty argumentString;
	
	// CONS
	/**
	 * Creates a YtdlProcess that will be run with the given arguments. The parameters will not be checked for
	 * validity. In the case of erroneous parameters, an exception will be thrown upon starting this process.
	 */
	public AbstractYtdlJob(int id, String ytdlPathString, List<String> arguments) {
		if(ytdlPathString == null || ytdlPathString.isEmpty()){
			arguments.add(0, "youtube-dl");
		} else{
			arguments.add(0, ytdlPathString);
		}
		
		this.arguments = arguments;
		this.listeners = new ArrayList<IYtdlJobStateListener>();
		this.setState(new YtdlWaitingState(this));
		
		this.id = new SimpleIntegerProperty(id);
		this.progress = new SimpleDoubleProperty(0.0);
		this.argumentString = new SimpleStringProperty(getArgumentsString());
	}

	/**
	 * @see #AbstractYtdlProcess(List)
	 */
	public AbstractYtdlJob(int id, String ytdlPathString, String... arguments) {
		this(id, ytdlPathString, Arrays.asList(arguments));
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

	@Deprecated
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
	
	// Properties
	public IntegerProperty idProperty(){
		return id;
	}
	
	public int getId(){
		return idProperty().get();
	}
	
	public DoubleProperty progressProperty(){
		return progress;
	}
	
	public double getProgress(){
		return progressProperty().get();
	}
	
	public void setProgress(double value){
		progressProperty().set(value);
	}
	
	public StringProperty argumentStringProperty(){
		return argumentString;
	}
	
	public String getArgumentString(){
		return argumentStringProperty().get();
	}
	
	public void setArgumentString(String value){
		argumentStringProperty().set(value);
	}
}
