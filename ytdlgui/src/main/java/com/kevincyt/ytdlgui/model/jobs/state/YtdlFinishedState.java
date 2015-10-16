package com.kevincyt.ytdlgui.model.jobs.state;

import com.kevincyt.io.ParallellBufferedReader;
import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;

public class YtdlFinishedState extends AbstractYtdlJobState {
	// VARS
	private final ParallellBufferedReader reader;
	
	public YtdlFinishedState(AbstractYtdlJob job, ParallellBufferedReader reader) {
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

	@Override
	public ParallellBufferedReader getReader() throws IllegalStateException {
		return reader;
	}

}
