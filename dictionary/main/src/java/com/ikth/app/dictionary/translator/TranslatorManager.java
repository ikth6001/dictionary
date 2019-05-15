package com.ikth.app.dictionary.translator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.ikth.app.dictionary.PluginLogger;
import com.ikth.app.dictionary.glosbe.EngToKorLangTranslatorOnGlosbe;

public class TranslatorManager 
{
	private final String ID= "com.ikth.app.dictionary.translator";
	private final String ELEMENT_NAME= "translator";
	private final String ATTR_NAME= "class";

	private Map<String, ILangTranslator> translator= null;
	
	public Collection<ILangTranslator> getLanguageAllTranslator()
	{
		return this.translator.values();
	}
	
	public ILangTranslator getLanguageTranslator(String id)
	{
		ILangTranslator t= this.translator.get(id);
		return t==null ? translator.get(EngToKorLangTranslatorOnGlosbe.ID) : t;
	}
	
	public void loadTrnaslators()
	{
		this.translator= new HashMap<>();
		
		IConfigurationElement[] elements= Platform.getExtensionRegistry().getConfigurationElementsFor(ID);
		for(IConfigurationElement element : elements)
		{
			if(ELEMENT_NAME.equals(element.getName()))
			{
				Object obj;
				try {
					obj = element.createExecutableExtension(ATTR_NAME);
					
					if(obj instanceof ILangTranslator)
					{
						ILangTranslator t= (ILangTranslator) obj;
						this.translator.put(t.getId(), t);
					}
				} catch (CoreException e) {
					PluginLogger.logError(e.getMessage(), e);
				}
			}
		}
	}
	
	
	private TranslatorManager()
	{
		loadTrnaslators();
	}
	
	private static TranslatorManager INSTANCE= null;
	
	public static TranslatorManager instance()
	{
		if(INSTANCE == null)
		{
			INSTANCE= new TranslatorManager();
		}
		return INSTANCE;
	}
}