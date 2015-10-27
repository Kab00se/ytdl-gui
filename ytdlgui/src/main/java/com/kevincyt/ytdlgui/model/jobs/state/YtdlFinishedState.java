package com.kevincyt.ytdlgui.model.jobs.state;

import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;

public class YtdlFinishedState extends AbstractYtdlJobState {
	public YtdlFinishedState(AbstractYtdlJob job) {
		super(job);
	}

	@Override
	public void start() throws IllegalStateException {
		throw new IllegalStateException("This job has already finished");
	}

	@Override
	public void cancel() throws IllegalStateException {
		throw new IllegalStateException("This job has already finished");
	}

	@Override
	public void finish() throws IllegalStateException {
		// Already finished.
	}

}
