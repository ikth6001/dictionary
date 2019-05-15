package com.ikth.app.dictionary.cache;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> implements ICache<K, V> {
	
	/**
	 * LinkedHashMap을 활용한 손쉬운? LRUCache
	 */
	private int capacity;
	private final int DEFAULT_INITIAL_CAPACITY = 5;
	private final float DEFAULT_LOAD_FACTOR = 0.75f;
	private Map<K, V> cache= Collections.synchronizedMap(new LRUMap());

	public LRUCache(int capacity) {
		
		if(capacity < DEFAULT_INITIAL_CAPACITY) {
			
			throw new IllegalArgumentException();
		}
		this.capacity= capacity;
	}
	
	@Override
	public V get(K key) {
		
		return cache.get(key);
	}
	
	@Override
	public void add(K key, V dat) {
		
		cache.put(key, dat);
	}
	
	@Override
	public void clear() {
		
		cache.clear();
	}
	
	
	
	class LRUMap extends LinkedHashMap<K, V> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 7377892693529038717L;
		
		public LRUMap() {
			
			super(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, true);
		}
		
		@Override
		protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
			
			return size() > capacity;
		}
	}
	
	
	/**
	 * 내가 구현 해본 일반적인? 쉽게 생각할 수 있는? LRUCache
	 */
//	private int capacity;
//	
//	private LinkedList<K> keys= null;
//	private Map<K, V> cache= null;
//	
//	LRUCache(int capacity) {
//		
//		this.capacity= capacity;
//		this.keys= new LinkedList<>();
//		this.cache= new HashMap<>();
//	}
//
//	public synchronized V get(K key) {
//		
//		if(keys.contains(key)) {
//			
//			keys.remove(key);
//			keys.addFirst(key);
//			return cache.get(key);
//		}
//		
//		return null;
//	}
//	
//	public synchronized void add(K key, V dat) {
//		
//		if(keys.contains(key)) {
//			
//			keys.remove(key);
//			keys.addFirst(key);
//			cache.put(key, dat);
//		} else if(capacity > keys.size()) {
//			
//			keys.addFirst(key);
//			cache.put(key, dat);
//		} else {
//			
//			cache.remove(keys.removeLast());
//			keys.addFirst(key);
//			cache.put(key, dat);
//		}
//	}
//	
//	synchronized void clear() {
//		
//		keys.clear();
//		cache.clear();
//	}
}
