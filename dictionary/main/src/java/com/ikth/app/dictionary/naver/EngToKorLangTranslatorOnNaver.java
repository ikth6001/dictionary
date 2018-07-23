package com.ikth.app.dictionary.naver;

import com.ikth.app.dictionary.translator.ILangTranslator;

public class EngToKorLangTranslatorOnNaver implements ILangTranslator
{

	@Override
	public String translate(String word) throws Exception 
	{
		// TODO Auto-generated method stub
		return "구현중";
	}

	@Override
	public boolean isTargetWord(String word) 
	{
		final String REGEX_ENGLISH= "[a-zA-Z]+";
		
		if(word == null)
		{
			return false;
		}
		
		return word.matches(REGEX_ENGLISH);
	}

	@Override
	public String getId() 
	{
		return "Naver open API dictionary";
	}

}
