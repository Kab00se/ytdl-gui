package com.kevincyt.ytdlgui.model.jobs;

import com.kevincyt.ytdlgui.model.jobs.state.IYtdlJobState;


public interface IYtdlJobStateListener {

	public void onJobStateChanged(AbstractYtdlJob job, IYtdlJobState oldState, IYtdlJobState newState);
	
}