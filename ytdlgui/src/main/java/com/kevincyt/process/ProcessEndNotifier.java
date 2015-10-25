package com.kevincyt.process;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

public class ProcessEndNotifier implements Runnable{
	private final List<IProcessEndListener> listeners;
	private final Process process;
	
	public ProcessEndNotifier(Process process){
		this.process = process;
		this.listeners = new ArrayList<IProcessEndListener>(1);
	}
	
	@Override
	public void run() {
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			LogManager.getLogger().warn("Process interrupted. Triggering process end.");
		} finally{
			notifyListeners();
		}
	}

	// Observers
	public void addProcessEndListener(IProcessEndListener listener){
		this.listeners.add(listener);
	}
	
	public void removeProcessEndListener(IProcessEndListener listener){
		this.listeners.remove(listener);
	}
	
	public void notifyListeners(){
		for(IProcessEndListener listener : listeners){
			listener.onProcessEnd();
		}
	}

}
