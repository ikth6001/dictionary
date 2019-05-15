package com.ikth.app.dictionary.preference;

import java.util.Collection;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.ikth.app.dictionary.translator.ILangTranslator;
import com.ikth.app.dictionary.translator.TranslatorManager;

public class DictionarySelectPreferencePage extends PreferencePage implements IWorkbenchPreferencePage
{
	private String selectedId;
	private ComboViewer dictionaries;

	@Override
	public void init(IWorkbench workbench) 
	{
		selectedId= DictionaryPreferenceManager.instance().getSelectedTranslatorId();
	}

	@Override
	protected Control createContents(Composite composite)
	{
		Composite area= new Composite(composite, SWT.NONE);
		{
			area.setLayout(new GridLayout(2, false));
			area.setLayoutData(new GridData(GridData.FILL_BOTH));
		}
		
		Label lbl= new Label(area, SWT.NONE);
		lbl.setText("Dictionary ");
		
		dictionaries= new ComboViewer(area, SWT.READ_ONLY);
		dictionaries.getCombo().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		dictionaries.setContentProvider(new ArrayContentProvider());
		dictionaries.setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element) 
			{
				if(element instanceof ILangTranslator)
				{
					ILangTranslator translator= (ILangTranslator) element;
					return translator.getId();
				}
				
				return "";
			}
		});
		Collection<ILangTranslator> translators= TranslatorManager.instance().getLanguageAllTranslator();
		dictionaries.setInput(translators.toArray(new ILangTranslator[translators.size()]));
		
		dictionaries.setSelection(new StructuredSelection(
				TranslatorManager.instance().getLanguageTranslator(selectedId)));
		
		return composite;
	}
	
	@Override
	public boolean performOk() 
	{
		Object selected= dictionaries.getStructuredSelection().getFirstElement();
		DictionaryPreferenceManager.instance().setSelectedTranslatorId( ((ILangTranslator) selected).getId() );
		
		return super.performOk();
	}
}