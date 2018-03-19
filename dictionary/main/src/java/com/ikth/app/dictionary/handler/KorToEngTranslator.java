package com.ikth.app.dictionary.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

public class KorToEngTranslator extends AbstractHandler
{

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException 
	{
		ITextEditor txtEditor= getActiveTextEditor();
		
		if(txtEditor == null)
		{
			return null;
		}
		
		@SuppressWarnings("unused")
		ISelection selection= txtEditor.getSelectionProvider().getSelection();
		
		/**
		 * TODO
		 * 1. 현재 텍스트 에디터의 블록된 단어 찾기
		 * 2. 해당 단어가 영어인지 확인
		 * 3. 영어인 경우 네이버 오픈 API를 통해 뜻 검색
		 * 4. 표현(자바 에디터의 Javadoc을 보여주는 방식이 좋을 듯)
		 */
		
		return null;
	}
	
	private ITextEditor getActiveTextEditor()
	{
		IEditorPart editorPart= PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		
		if(editorPart instanceof ITextEditor)
		{
			return (ITextEditor) editorPart;
		}
		
		return null;
	}
}
