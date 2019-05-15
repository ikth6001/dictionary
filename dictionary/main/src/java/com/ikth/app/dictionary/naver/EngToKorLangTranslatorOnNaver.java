package com.ikth.app.dictionary.naver;

import java.text.MessageFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ikth.app.dictionary.translator.ILangTranslator;

public class EngToKorLangTranslatorOnNaver implements ILangTranslator
{
	private NaverOpenAPIClient client= new NaverOpenAPIClient();
	private final String RESULT_FORMAT= "<b>의미</b> : {0}" + "<br>" + "<b>정의</b> : {1}";
	
	@Override
	public String translate(String word) throws Exception 
	{
		String res= client.send(word);
		if(res == null || "".equals(res.trim())) return res;
		
		JSONObject jsonObj= new JSONObject(res);
		JSONArray items= jsonObj.getJSONArray("items");
		
		if(items == null || items.length() == 0)
		{
			return "<b>There are no translation information.</b>";
		}
		
		JSONObject contents= items.getJSONObject(0);
		
		String meaning= contents.getString("title");
		String desc= contents.getString("description");
		
		return MessageFormat.format(RESULT_FORMAT, meaning, desc);
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

	public static void main(String[] args) throws Exception {
		EngToKorLangTranslatorOnNaver m= new EngToKorLangTranslatorOnNaver();
		System.out.println(m.translate("apple"));
	}
}
