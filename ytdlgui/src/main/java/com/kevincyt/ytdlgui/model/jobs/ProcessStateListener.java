package com.kevincyt.ytdlgui.model.jobs;

import com.kevincyt.process.IProcessEndListener;

public class ProcessStateListener implements IProcessEndListener {

	private final AbstractYtdlJob job;
	
	public ProcessStateListener(AbstractYtdlJob job) {
		this.job = job;
	}
	
	@Override
	public void onProcessEnd() {
		job.setState(YtdlJobState.FINISHED);
	}

}