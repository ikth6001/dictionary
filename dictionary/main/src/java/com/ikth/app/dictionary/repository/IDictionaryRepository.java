package com.ikth.app.dictionary.repository;

import java.util.List;

public interface IDictionaryRepository {

	List<DictionaryEntity> findAll() throws Exception;
	
	void removeAll() throws Exception;
	
	void add(List<DictionaryEntity> dictionaries) throws Exception;
}
