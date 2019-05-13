package com.ikth.app.dictionary.cache;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class DictionaryCacheManager {
	
	private static DictionaryCacheManager INSTANCE= null;
	
	private ICache<String, String> cache= null;
	
	private final int STATUS_NOT_LOADED= 0;
	private final int STATUS_LOADING= 1;
	private final int STATUS_LOAD_COMPLETE= 2;
	
	private int status= STATUS_NOT_LOADED;
	
	public ICache<String, String> getDictionaryCache() throws Exception {
		
		if(status != STATUS_LOAD_COMPLETE) {
			
			throw new Exception("Cache is not loaded, yet.");
		}
		
		return this.cache;
	}
	
	public synchronized static DictionaryCacheManager instance() {
		
		if(INSTANCE == null) {
			
			INSTANCE= new DictionaryCacheManager();
		}
		
		return INSTANCE;
	}
	
	private DictionaryCacheManager() {
		
		load();
	}
	
	private void load() {
		
		if(status == STATUS_NOT_LOADED) {
			
			status= STATUS_LOADING;
			Job.create("Load", (monitor) -> load(monitor)).schedule();
		}
	}
	
	private IStatus load(IProgressMonitor monitor) {
		monitor.beginTask("Load...", 2);
		monitor.worked(1);
		/**
		 * TODO load database in background job.
		 */
		this.cache= new LRUCache<>(100);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		status= STATUS_LOAD_COMPLETE;
		monitor.worked(1);
		monitor.done();
		return Status.OK_STATUS;
	}
	
}
