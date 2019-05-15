package com.ikth.app.dictionary.preference;

import com.ikth.app.dictionary.Activator;
import com.ikth.app.dictionary.glosbe.EngToKorLangTranslatorOnGlosbe;

public class DictionaryPreferenceManager 
{
	private final static String PROP_SELECTED_TRANSLATOR= "selectedTranslator";
	
	public void setSelectedTranslatorId(String id)
	{
		Activator.getDefault().getPreferenceStore().setValue(PROP_SELECTED_TRANSLATOR, id);
	}
	
	public String getSelectedTranslatorId()
	{
		String id= Activator.getDefault().getPreferenceStore().getString(PROP_SELECTED_TRANSLATOR);
		return id==null || id=="" ? EngToKorLangTranslatorOnGlosbe.ID : id;
	}
	
	private DictionaryPreferenceManager() { }
	
	private static DictionaryPreferenceManager INSTANCE= null;
	
	public static DictionaryPreferenceManager instance()
	{
		if(INSTANCE == null) INSTANCE= new DictionaryPreferenceManager();
		return INSTANCE;
	}

}
