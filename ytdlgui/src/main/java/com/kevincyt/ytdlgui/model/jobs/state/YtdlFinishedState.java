package com.kevincyt.ytdlgui.model.jobs.state;

import com.kevincyt.io.ParallelBufferedReader;
import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;

public class YtdlFinishedState extends AbstractYtdlJobState {
	// VARS
	private final ParallelBufferedReader reader;
	
	public YtdlFinishedState(AbstractYtdlJob job, ParallelBufferedReader reader) {
		super(job);
		this.reader = reader;
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
