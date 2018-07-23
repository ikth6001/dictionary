package com.ikth.app.dictionary.translator;

public interface ILangTranslator 
{
	/**
	 * 단어를 번역한다.
	 * @param word 번역할 단어
	 * @return 번역된 단어
	 */
	String translate(String word) throws Exception;
	
	/**
	 * 문자열이 번역 대상 단어인지 확인한다.
	 * @param word 대상인지 확인할 문자열
	 * @return 번역 대상 여부
	 */
	boolean isTargetWord(String word);
	
	/**
	 * 복수개의 번역 모듈 구분자
	 * @return
	 */
	String getId();
}