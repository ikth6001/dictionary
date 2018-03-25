package com.ikth.app.dictionary.translator;

public class EngToKorLangTranslatorOnGlosbe implements ILangTranslator
{

	@Override
	public String translate(String word) 
	{
		// TODO : OPEN API를 활용하여 번역단어 검색
		return "테스트";
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

}
