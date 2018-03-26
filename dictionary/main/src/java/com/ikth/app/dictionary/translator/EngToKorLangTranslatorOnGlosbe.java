package com.ikth.app.dictionary.translator;

import java.text.MessageFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ikth.app.dictionary.glosbe.GlosbeHttpClient;

public class EngToKorLangTranslatorOnGlosbe implements ILangTranslator
{
	@Override
	public String translate(String word) throws Exception
	{
		String json= 
				GlosbeHttpClient.translate("translate", "eng", "kor", word, "json", true);

		String RESULT_FORMAT= "<b>의미</b> : {0}" + "<br>" + "<b>정의</b> : {1}";
		
		JSONObject jsonObj= new JSONObject(json);
		String result= jsonObj.getString("result");
		
		if("ok".equals(result))
		{
			JSONArray tuc= jsonObj.getJSONArray("tuc");
			
			if(tuc == null || tuc.length() == 0)
			{
				return "<b>There are no translation information.</b>";
			}

			JSONObject contents= tuc.getJSONObject(0);
			
			String translated= null;
			String meaning= "";
			
			if(contents.has("phrase"))
			{
				translated= contents.getJSONObject("phrase").getString("text");
			}
			else
			{
				return "<b>There are no translation information.</b>";
			}
			
			if(contents.has("meanings"))
			{
				JSONArray meanings= contents.getJSONArray("meanings");
				for(int i=0; i<meanings.length();)
				{
					meaning= meanings.getJSONObject(0).getString("text");
					break;
				}
			}
			
			return MessageFormat.format(RESULT_FORMAT, translated, meaning);
		}
		
		return null;
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

	public static void main(String[] args) throws Exception {
		EngToKorLangTranslatorOnGlosbe m= new EngToKorLangTranslatorOnGlosbe();
		System.out.println(m.translate("apple"));
	}
}
