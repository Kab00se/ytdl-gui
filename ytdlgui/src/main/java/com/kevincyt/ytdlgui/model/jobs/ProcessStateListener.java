package com.kevincyt.ytdlgui.model.jobs;

import com.kevincyt.process.IProcessEndListener;
import com.kevincyt.ytdlgui.model.jobs.state.IYtdlJobState;

public class ProcessStateListener implements IProcessEndListener {

	private final IYtdlJobState jobState;
	
	public ProcessStateListener(IYtdlJobState state) {
		this.jobState = state;
	}
	
	@Override
	public void onProcessEnd() {
		jobState.finish();
	}

}