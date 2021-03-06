package com.kevincyt.ytdlgui.model.jobs.state;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.application.Platform;

import com.kevincyt.concurrency.ThreadPoolManager;
import com.kevincyt.io.ParallelBufferedReader;
import com.kevincyt.process.ProcessEndNotifier;
import com.kevincyt.ytdlgui.customfx.PopupStub;
import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;
import com.kevincyt.ytdlgui.model.jobs.ProcessStateListener;

public class YtdlWaitingState extends AbstractYtdlJobState {
	// VARS

	// CONS
	public YtdlWaitingState(AbstractYtdlJob job) {
		super(job);
	}

	// STATE
	@Override
	public void start() {
		try {
			Process process = new ProcessBuilder().command(getJob().getArgumentsList()).start();
			ParallelBufferedReader reader = new ParallelBufferedReader(new BufferedReader(
					new InputStreamReader(process.getErrorStream())), new BufferedReader(
							new InputStreamReader(process.getInputStream())));
			IYtdlJobState newState = new YtdlRunningState(getJob(), reader);
			getJob().setState(newState);
			// Thread waits for end of the process. Triggers state switch.
			ProcessEndNotifier pen = new ProcessEndNotifier(process);
			pen.addProcessEndListener(new ProcessStateListener(newState));
			ThreadPoolManager.getInstance().getExecutor().execute(pen);
		} catch (IOException e) {
			// TODO: If youtube-dl is not in path, error comes here (IOException)
			e.printStackTrace();
		}
	}

	@Override
	public void cancel() {
		getJob().setState(new YtdlCancelledState(getJob()));
	}

	@Override
	public void finish() throws IllegalStateException {
		throw new IllegalStateException("Job cannot finish without being run");
	}
	
	@Override
	public boolean isWaiting() {
		return true;
	}

}
