package com.kevincyt.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
	// Vars
	private final ExecutorService executor;
	
	// Singleton
	private ThreadPoolManager(){
		executor = Executors.newCachedThreadPool();
	}
	
	public static ThreadPoolManager getInstance(){
		return InstanceHolder.INSTANCE;
	}
	
	private static class InstanceHolder{
		private static final ThreadPoolManager INSTANCE = new ThreadPoolManager();
	}

	// Methods
	public ExecutorService getExecutor(){
		return executor;
	}
	
}
