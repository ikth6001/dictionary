package com.ikth.app.dictionary.naver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;

import com.ikth.app.dictionary.PluginLogger;

public class NaverOpenAPIClient 
{
	/**
	 * 이 키 값이 오픈되는데... 크게 문제될 일은 없겠지..
	 */
	private final String PROP_CLIENT_ID= "X-Naver-Client-Id";
	private final String PROP_CLIENT_SECRET= "X-Naver-Client-Secret";
	private final String CLIENT_ID= "E8nExwsUoa5KswI1yt44";
	private final String CLIENT_SECRET= "86T5STOQww";
	
	private final String BASE_REQUEST_URI= "https://openapi.naver.com/v1/search/encyc.json?query={0}&display=10&start=1";
	private final String REQUEST_METHOD= "GET";
	
	public String send(String word) throws Exception
	{
		URL url= new URL(MessageFormat.format(BASE_REQUEST_URI, word));
		HttpURLConnection conn= (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(REQUEST_METHOD);
		conn.setRequestProperty(PROP_CLIENT_ID, CLIENT_ID);
		conn.setRequestProperty(PROP_CLIENT_SECRET, CLIENT_SECRET);
		
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
		
		PluginLogger.logError(
				MessageFormat.format(
						"There are translate error. code [{0}], msg[{1}]", resCd, conn.getResponseMessage()));
		
		return "";
	}

	public static void main(String[] args) throws Exception {
		NaverOpenAPIClient client= new NaverOpenAPIClient();
		System.out.println(client.send("apple"));
	}
}
