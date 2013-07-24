package org.ambientdynamix.contextplugins.currenttime;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class Timer extends IntentService {

	private final String TAG = "TIMEPLUGIN";
	Intent intent;
	SharedPreferences prefs;
	private static boolean stop=false;

	
	public Timer() 
	{
		super("Timer");
	}

	@Override
	protected void onHandleIntent(Intent arg0) 
	{
		Log.i(TAG, "handle");
		CurrentTimePluginRuntime.sendTime();
		scheduleNext();
	}
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		Log.i(TAG, "onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		super.onStartCommand(intent, flags, startId);
		Log.i(TAG, "Received start id " + startId + ": " + intent);
		return Service.START_NOT_STICKY;
	}
	
	@Override
    public void onDestroy() 
    {
        super.onDestroy();
        Log.i(TAG, "destroy");
        //AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    	//if(intent!=null)
    	//{
	    //	PendingIntent p = PendingIntent.getService(this, 9632, intent, PendingIntent.FLAG_NO_CREATE);
	    //	am.cancel(p);
    	//}
    }
	
	private void scheduleNext()
	{
		//Log.d(TAG, "sNext");
		if(!stop)
		{
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.SECOND, 1);
	        intent = new Intent(this, Timer.class);
	        //Log.d(TAG, "snext2");
	        PendingIntent pendingIntent = PendingIntent.getService(this, 9632, intent, 0);
	        //Log.d(TAG, "snext3");
	        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
	        //Log.d(TAG, "snext4");
	        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
	        //Log.d(TAG, "snext5");
		}
		else
		{
			stop=false;
		}
	}
	
	
	public static void stop()
	{
		stop=true;
	}
}
