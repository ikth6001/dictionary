package com.ikth.app.dictionary.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

@Deprecated
public class EngToKorTranslator extends AbstractHandler
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
