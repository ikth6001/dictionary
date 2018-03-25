package com.ikth.app.dictionary;

import org.eclipse.core.runtime.Status;

public class PluginLogger 
{
	public static void logError(String msg, Throwable t)
	{
		error(msg, t);
	}
	
	public static void logError(String msg)
	{
		error(msg, null);
	}

	private static void error(String msg, Throwable t)
	{
		if(t == null)
		{
			Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, msg));
		}
		else
		{
			Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, msg, t));
		}
	}
}
