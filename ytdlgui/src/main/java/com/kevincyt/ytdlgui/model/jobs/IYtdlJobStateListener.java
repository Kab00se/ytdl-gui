package com.kevincyt.ytdlgui.model.jobs;


public interface IYtdlJobStateListener {

	public void onJobStateChanged(AbstractYtdlJob job, YtdlJobState newState);
	
}