package com.ikth.app.dictionary.cache;

public interface ICache<K, V> {

	V get(K key);
	
	void add(K key, V dat);
	
	void clear();
}
