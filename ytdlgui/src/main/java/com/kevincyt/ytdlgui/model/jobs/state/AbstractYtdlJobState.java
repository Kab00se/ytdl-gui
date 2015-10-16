package com.kevincyt.ytdlgui.model.jobs.state;

import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;


public abstract class AbstractYtdlJobState implements IYtdlJobState {
	// VARS
	private final AbstractYtdlJob job;		// State context
	
	// CONS
	public AbstractYtdlJobState(AbstractYtdlJob job){
		this.job = job;
	}
	
	@Override
	public boolean isRunning() {
		return false;
	}
	
	@Override
	public boolean isWaiting() {
		return false;
	}
	
	// Gets
	protected AbstractYtdlJob getJob(){
		return job;
	}
}
