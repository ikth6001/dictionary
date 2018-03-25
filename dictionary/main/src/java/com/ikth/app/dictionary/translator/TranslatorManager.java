package com.ikth.app.dictionary.translator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class TranslatorManager 
{
	private final String ID= "com.ikth.app.dictionary.translator";
	private final String ELEMENT_NAME= "translator";
	private final String ATTR_NAME= "class";

	private ILangTranslator translator= new EngToKorLangTranslatorOnGlosbe();
	
	public ILangTranslator getLanguageTranslator()
	{
		return this.translator;
	}
	
	public void loadTrnaslators()
	{
		IConfigurationElement[] elements= Platform.getExtensionRegistry().getConfigurationElementsFor(ID);
		
		for(IConfigurationElement element : elements)
		{
			if(ELEMENT_NAME.equals(element.getName()))
			{
				Object obj= element.getAttribute(ATTR_NAME);
				
				if(obj instanceof ILangTranslator)
				{
					translator= (ILangTranslator) obj;
					break;
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