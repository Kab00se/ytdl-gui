package com.kevincyt.ytdlgui.model;

import java.util.List;

import com.kevincyt.ytdlgui.model.jobs.AbstractYtdlJob;
import com.kevincyt.ytdlgui.model.jobs.IYtdlJobStateListener;
import com.kevincyt.ytdlgui.model.jobs.YtdlJobState;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;

// TODO: EXTRA: update has to override / block normal download jobs.

public class YtdlJobQueue {
	// VARS
	private final Property<Number> maxConcurrentJobsProperty;
	private final List<AbstractYtdlJob> jobList;
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
		if(job.getState() != YtdlJobState.FINISHED || job.getState() != YtdlJobState.CANCELLED) {
			job.cancel();
		}
		this.jobList.remove(job);
		activateJobs();
	}

	/**
	 * Attempts to activate job(s) if there are slots for them.
	 */
	public void activateJobs() {
		int inProgress = 0;
		for (AbstractYtdlJob job : jobList) {
			if(job.getState() == YtdlJobState.RUNNING) {
				inProgress++;
			}
		}
		int openSlots = getMaxConcurrentJobs() - inProgress;
		int index = 0;
		while(openSlots > 0 && index < jobList.size()){
			if(jobList.get(index).getState() == YtdlJobState.WAITING){
				jobList.get(index).start();
				openSlots--;
			}
			index++;
		}
	}

	// GETS & SETS
	public Property<Number> getMaxConcurrentJobsProperty() {
		return this.maxConcurrentJobsProperty;
	}

	public int getMaxConcurrentJobs() {
		return this.maxConcurrentJobsProperty.getValue().intValue();
	}

	protected class QueueJobStateListener implements IYtdlJobStateListener{
		private final YtdlJobQueue queue;
		
		public QueueJobStateListener(YtdlJobQueue queue) {
			this.queue = queue;
		}

		@Override
		public void onJobStateChanged(AbstractYtdlJob job, YtdlJobState newState) {
			// TODO: CHECK: Do I even need to do anything on job change?
		}

	}
}
