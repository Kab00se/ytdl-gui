package com.kevincyt.ytdlgui.model.jobs.state;

import java.io.IOException;

import com.kevincyt.io.ParallelBufferedReader;
import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;

public class YtdlRunningState extends AbstractYtdlJobState {
	// VARS
	private final ParallelBufferedReader reader;

	public YtdlRunningState(AbstractYtdlJob job, ParallelBufferedReader reader) {
		super(job);
		this.reader = reader;
	}

	@Override
	public void start() throws IllegalStateException {
		throw new IllegalStateException("This job has already started");
	}

	@Override
	public void cancel() throws IllegalStateException {
		getJob().setState(new YtdlCancelledState(getJob()));
	}

	@Override
	public void finish() throws IllegalStateException {
		getJob().setState(new YtdlFinishedState(getJob(), reader));
	}

	@Override
	public ParallelBufferedReader getReader() throws IllegalStateException {
		return reader;
	}

	@Override
	public boolean isRunning() {
		return true;
	}
}
