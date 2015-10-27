package com.kevincyt.ytdlgui.model.jobs.state;

import com.kevincyt.io.IBufferedReaderListener;
import com.kevincyt.io.ParallelBufferedReader;
import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;

public class YtdlRunningState extends AbstractYtdlJobState {
	// VARS
	private final ParallelBufferedReader reader;

	public YtdlRunningState(AbstractYtdlJob job, ParallelBufferedReader reader) {
		super(job);
		this.reader = reader;
		reader.addListener(new YtdlProcessReaderListener(this));
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
		getJob().setState(new YtdlFinishedState(getJob()));
	}

	public ParallelBufferedReader getReader(){
		return reader;
	}

	@Override
	public boolean isRunning() {
		return true;
	}
	
	private class YtdlProcessReaderListener implements IBufferedReaderListener{
		private final YtdlRunningState context;
		
		private YtdlProcessReaderListener(YtdlRunningState context) {
			this.context = context;
		}
		
		@Override
		public void notifyLine(String line) {
			// TODO UPDATE CONTEXT PROGRESS BASED ON LINE
		}
		
	}
}
