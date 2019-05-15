package com.ikth.app.dictionary.cache;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LRUCacheTest {
	
	private final int CAPACITY= 5;
	private ICache<Integer, Integer> cache;
	
	@Before
	public void prepare() {
		
		cache= new LRUCache<>(CAPACITY);
	}
	
	@After
	public void clear() {
		
		cache.clear();
		cache= null;
	}
	
	@Test
	public void hitTest() {
		
		cache.clear();
		
		cache.add(1, 1);
		cache.add(2, 2);
		cache.add(3, 3);
		cache.add(4, 4);
		cache.add(5, 5);
		cache.add(6, 6);
		
		Assert.assertEquals("Miss가 나야 하는데 Hit", null, cache.get(1));
		Assert.assertEquals("Hit이 나야 하는데 Miss", new Integer(2), cache.get(2));
	}

	@Test
	public void justAddTest() {
		
		cache.clear();
		
		cache.add(1, 1);
		cache.add(2, 2);
		cache.add(3, 3);
		cache.add(4, 4);
		cache.add(5, 5);
		cache.add(6, 6);
		
		Assert.assertEquals("키 값이 다름.", Arrays.asList(2, 3, 4, 5, 6), getAllKeys());
		Assert.assertEquals("데이터 값이 다름.", Arrays.asList(2, 3, 4, 5, 6), getAllData());
		
		cache.add(7, 7);
		cache.add(4, 4);
		cache.add(1, 1);
		
		Assert.assertEquals("키 값이 다름.", Arrays.asList(5, 6, 7, 4, 1), getAllKeys());
		Assert.assertEquals("데이터 값이 다름.", Arrays.asList(5, 6, 7, 4, 1), getAllData());
	}

	@SuppressWarnings("unchecked")
	private List<Integer> getAllKeys() {
		
		try {
			
			Class<?> clazz= LRUCache.class;
			Field field= clazz.getDeclaredField("cache");
			field.setAccessible(true);
			Map<Integer, Integer> cache= (Map<Integer, Integer>) field.get(this.cache);
			return cache.keySet().stream().collect(Collectors.toList());
		} catch (Exception e) {
			return Collections.emptyList();
		}
//		try {
//			
//			Class<?> clazz= LRUCache.class;
//			Field keys= clazz.getDeclaredField("keys");
//			keys.setAccessible(true);
//			return (List<Integer>) keys.get(this.cache);
//		} catch (Exception e) {
//			return Collections.emptyList();
//		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Integer> getAllData() {
		
		try {
			
			Class<?> clazz= LRUCache.class;
			Field field= clazz.getDeclaredField("cache");
			field.setAccessible(true);
			Map<Integer, Integer> cache= (Map<Integer, Integer>) field.get(this.cache);
			return cache.values().stream().collect(Collectors.toList());
		} catch (Exception e) {
			return Collections.emptyList();
		}
//		try {
//			
//			Class<?> clazz= LRUCache.class;
//			Field keys= clazz.getDeclaredField("cache");
//			keys.setAccessible(true);
//			Map<Integer, Integer> cache= (Map<Integer, Integer>) keys.get(this.cache);
//			
//			List<Integer> result= new ArrayList<>(cache.size());
//			getAllKeys().stream().forEach((key) -> result.add(cache.get(key))); 
//			return result;
//		} catch (Exception e) {
//			return Collections.emptyList();
//		}
	}
}
