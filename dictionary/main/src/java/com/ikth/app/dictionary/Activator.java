package com.ikth.app.dictionary;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.ikth.app.dictionary.cache.DictionaryCacheManager;
import com.ikth.app.dictionary.cache.ICache;
import com.ikth.app.dictionary.cache.LRUCache;
import com.ikth.app.dictionary.repository.DictionaryEntity;
import com.ikth.app.dictionary.repository.HibernateDictionaryRepository;
import com.ikth.app.dictionary.repository.IDictionaryRepository;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	
	private IDictionaryRepository repository= new HibernateDictionaryRepository();

	// The plug-in ID
	public static final String PLUGIN_ID = "com.ikth.app.dictionary"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		DictionaryCacheManager.instance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		
		ICache<String, String> dictCache= DictionaryCacheManager.instance().getDictionaryCache();
		Map<String, String> data= getCacheData(dictCache);
		List<DictionaryEntity> dictionaries= new ArrayList<>(data.size());
		data.entrySet().stream()
					 .forEach((entry) -> dictionaries.add(createEntity(entry.getKey(), entry.getValue())));
		
		repository.removeAll();
		repository.add(dictionaries);
	}
	
	private DictionaryEntity createEntity(String word, String meaning) {
		
		DictionaryEntity entity= new DictionaryEntity();
		entity.setWord(word);
		entity.setMeaning(meaning);
		return entity;
	}
	
	private Map<String, String> getCacheData(ICache<String, String> cache) {
		
		Class<?> clazz= LRUCache.class;
		try {
			Field field= clazz.getDeclaredField("cache");
			field.setAccessible(true);
			
			@SuppressWarnings("unchecked")
			Map<String, String> data= (Map<String, String>) field.get(cache);
			
			return data;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			/** ignore */
			e.printStackTrace();
			return Collections.emptyMap();
		}
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
