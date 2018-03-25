package com.ikth.app.dictionary.glosbe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;

public class GlosbeHttpClient 
{
	/**
	 * 
	 * @param function glosbe request function
	 * @param from phrase locale
	 * @param dest locale to translate
	 * @param phrase word
	 * @param format json, xml
	 * @param pretty make output string format
	 * @return
	 */
	public static String translate(String function, String from, String dest, String phrase, String format, boolean pretty) throws Exception
	{
		final String REQUEST_FORMAT= "https://glosbe.com/gapi/{0}?from={1}&dest={2}&phrase={3}&format={4}&pretty={5}";
		
		URL url= new URL(MessageFormat.format(REQUEST_FORMAT, function, from, dest, phrase, format, String.valueOf(pretty)));
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();
		
		int resCd= conn.getResponseCode();
		
		if(resCd == HttpURLConnection.HTTP_OK)
		{
			try(BufferedReader r= new BufferedReader(
					new InputStreamReader(conn.getInputStream())))
			{
				StringBuilder response= new StringBuilder();
				String line;
				
				while( (line= r.readLine()) != null )
				{
					response.append(line);
				}
				
				return response.toString();
			}
		}
		throw new Exception("Http error code : [" + resCd + "]");
	}
	
	public static void main(String[] args) 
	{
		try {
			System.out.println(GlosbeHttpClient.translate("translate", "eng", "kor", "package", "json", true));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
