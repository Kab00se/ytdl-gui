package com.kevincyt.ytdlgui.model.jobs.state;

import java.io.IOException;

import com.kevincyt.io.ParallellBufferedReader;
import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;

public class YtdlRunningState extends AbstractYtdlJobState {
	// VARS
	private final ParallellBufferedReader reader;

	public YtdlRunningState(AbstractYtdlJob job, ParallellBufferedReader reader) {
		super(job);
		this.reader = reader;
		// TODO: TEMP
		try {
			String line;
			while ((line = getReader().readLine()) != null) {
				System.out.println("Line: " + line);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public ParallellBufferedReader getReader() throws IllegalStateException {
		return reader;
	}

	@Override
	public boolean isRunning() {
		return true;
	}
}
