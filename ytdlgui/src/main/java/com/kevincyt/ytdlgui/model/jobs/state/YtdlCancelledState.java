package com.kevincyt.ytdlgui.model.jobs.state;

import com.kevincyt.io.ParallelBufferedReader;
import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;

public class YtdlCancelledState extends AbstractYtdlJobState {

	public YtdlCancelledState(AbstractYtdlJob job) {
		super(job);
	}

	@Override
	public void start() throws IllegalStateException {
		throw new IllegalStateException("Process is cancelled");
	}

	@Override
	public void cancel() throws IllegalStateException {
		// Process has already been cancelled
	}

	@Override
	public void finish() throws IllegalStateException {
		throw new IllegalStateException("Process is cancelled");
	}

	@Override
	public ParallelBufferedReader getReader() throws IllegalStateException {
		throw new IllegalStateException("Process is cancelled");
	}

}
