package com.kevincyt.ytdlgui.model;

import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;
import com.kevincyt.ytdlgui.model.jobs.IYtdlJobStateListener;
import com.kevincyt.ytdlgui.model.jobs.state.IYtdlJobState;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// TODO: EXTRA: update has to override / block normal download jobs.

public class YtdlJobQueue {
	// VARS
	private final Property<Number> maxConcurrentJobsProperty;
	private final ObservableList<AbstractYtdlJob> jobList;
	private final QueueJobStateListener jobStateListener;

	// CONS
	public YtdlJobQueue() {
		maxConcurrentJobsProperty = new SimpleIntegerProperty();
		jobList = FXCollections.observableArrayList();
		jobStateListener = new QueueJobStateListener(this);
		maxConcurrentJobsProperty.addListener((object, oldValue, newValue) -> {
			activateJobs();
		});
	}

	// METHODS
	public void addYtdlJob(AbstractYtdlJob job) {
		this.jobList.add(job);
		job.addJobStateListener(jobStateListener);
		activateJobs();
	}

	public void removeYtdlJob(AbstractYtdlJob job) {
		job.cancel();
		this.jobList.remove(job);
		activateJobs();
	}

	/**
	 * Attempts to activate job(s) if there are slots for them.
	 */
	public void activateJobs() {
		int jobsInProgress = 0;
		for(AbstractYtdlJob job : jobList){
			if(job.isRunning()){
				jobsInProgress++;
			}
		}
		int openSlots = getMaxConcurrentJobs() - jobsInProgress;
		int idx = 0;
		AbstractYtdlJob job;
		while(idx < jobList.size() && openSlots > 0){
			job = jobList.get(idx);
			if(job.isWaiting()){
				job.start();
				openSlots--;
			}
			idx++;
		}
	}

	// GETS & SETS
	public ObservableList<AbstractYtdlJob> getJobList() {
		return jobList;
	}
	
	public Property<Number> maxConcurrentJobsProperty() {
		return this.maxConcurrentJobsProperty;
	}

	public int getMaxConcurrentJobs() {
		return this.maxConcurrentJobsProperty.getValue().intValue();
	}

	// INNER CLASS
	protected class QueueJobStateListener implements IYtdlJobStateListener{
		private final YtdlJobQueue queue;
		
		public QueueJobStateListener(YtdlJobQueue queue) {
			this.queue = queue;
		}

		@Override
		public void onJobStateChanged(AbstractYtdlJob job, IYtdlJobState oldState, IYtdlJobState newState) {
			queue.activateJobs();
		}

	}

	
}
