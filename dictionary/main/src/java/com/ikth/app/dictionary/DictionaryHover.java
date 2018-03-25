package com.ikth.app.dictionary;

import org.eclipse.jdt.ui.text.java.hover.IJavaEditorTextHover;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.ui.IEditorPart;

public class DictionaryHover implements IJavaEditorTextHover
{
	@Override
	public String getHoverInfo(ITextViewer txtViewer, IRegion region) 
	{
		final String word= getTargetWord(region.getOffset(), txtViewer.getDocument().get());
		
		if(isEnglishWord(word))
		{
			return word;
		}
		
		System.out.println("It is not an english word");
		return null;
	}

	@Override
	public IRegion getHoverRegion(ITextViewer txtViewer, int offset) 
	{
		return new Region(offset, 0);
	}

	@Override
	public void setEditor(IEditorPart arg0) 
	{
		/**
		 * ignore
		 */
	}
	
	/**
	 * 마우스가 올라간 위치의 단어를 찾는다.
	 * @param offset 마우스가 올라간 위치
	 * @param contents 전체 문자열
	 * @return
	 */
	private String getTargetWord(int offset, String contents)
	{
		char chr= contents.charAt(offset);
		
		if(Character.isWhitespace(chr))
		{
			return null;
		}
		
		int start= 0;
		int end= contents.length();
		
		for(int i=offset; i>=0; i--)
		{
			if(Character.isWhitespace(contents.charAt(i)))
			{
				start= i + 1;
				break;
			}
		}
		
		for(int i=offset; i<contents.length(); i++)
		{
			if(Character.isWhitespace(contents.charAt(i)))
			{
				end= i;
				break;
			}
		}
		
		return contents.substring(start, end);
	}
	
	private boolean isEnglishWord(String word)
	{
		final String REGEX_ENGLISH= "[a-zA-Z]+";
		
		if(word == null)
		{
			return false;
		}
		
		return word.matches(REGEX_ENGLISH);
	}
}