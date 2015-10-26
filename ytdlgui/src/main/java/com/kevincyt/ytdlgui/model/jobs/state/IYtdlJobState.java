package com.kevincyt.ytdlgui.model.jobs.state;

public interface IYtdlJobState {

	/**
	 * Attempts to start the job.
	 *
	 * @throws IllegalStateException
	 *             The job cannot be started.
	 */
	public void start() throws IllegalStateException;

	/**
	 * Cancels the job, regardless of the current state. Note that this kills the process and reader (if
	 * available).
	 */
	public void cancel();

	/**
	 * Finalizes the job, releasing the process and reader objects if available.
	 * 
	 * @throws IllegalStateException
	 *             The current job cannot finish yet.
	 */
	public void finish() throws IllegalStateException;

	public boolean isRunning();
	public boolean isWaiting();

}
