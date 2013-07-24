package org.ambientdynamix.contextplugins.currenttime;

import java.util.UUID;

import org.ambientdynamix.api.contextplugin.*;
import org.ambientdynamix.api.contextplugin.security.PrivacyRiskLevel;
import org.ambientdynamix.api.contextplugin.security.SecuredContextInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class CurrentTimePluginRuntime extends AutoReactiveContextPluginRuntime
{
	private final String TAG = "TIMEPLUGIN";
	private static CurrentTimePluginRuntime context;
	Timer timer;

	@Override
	public void start() 
	{
		/*
		 * Nothing to do, since this is a pull plug-in... we're now waiting for context scan requests.
		 */
		context=this;
		Log.i(TAG, "Started!");
	}

	@Override
	public void stop() 
	{
		/*
		 * At this point, the plug-in should cancel any ongoing context scans, if there are any.
		 */
		Log.i(TAG, "Stopped!");
		Timer.stop();
	}

	@Override
	public void destroy() 
	{
		/*
		 * At this point, the plug-in should release any resources.
		 */
		stop();
		Log.i(TAG, "Destroyed!");
	}

	@Override
	public void updateSettings(ContextPluginSettings settings) 
	{
		// Not supported
	}

	@Override
	public void handleContextRequest(UUID requestId, String contextInfoType) 
	{
		SecuredContextInfo aci= new SecuredContextInfo(new CurrentTimeContextInfo(), PrivacyRiskLevel.LOW);
		sendContextEvent(requestId, aci, 1000);
		context=this;
	}

	@Override
	public void handleConfiguredContextRequest(UUID requestId, String contextInfoType, Bundle scanConfig) 
	{
		handleContextRequest(requestId, contextInfoType);
		context=this;
	}

	@Override
	public void init(PowerScheme arg0, ContextPluginSettings arg1) throws Exception 
	{
		Log.d(TAG, "init");
		timer=new Timer();
		context=this;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPowerScheme(PowerScheme arg0) throws Exception 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doManualContextScan() 
	{
		// TODO Auto-generated method stub
		
	}
	
	public static void sendTime()
	{
		SecuredContextInfo aci= new SecuredContextInfo(new CurrentTimeContextInfo(), PrivacyRiskLevel.LOW);
		if(context!=null)
		{
			context.sendBroadcastContextEvent(aci, 1000);
		}
	}

}