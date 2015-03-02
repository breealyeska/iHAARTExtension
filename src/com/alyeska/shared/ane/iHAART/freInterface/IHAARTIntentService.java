/*
 * Copyright 2014 Bree Alyeska.
 *
 * This file is part of the IHAART Library, developed in conjunction with and distributed with iHAART/CollaboRhythm.
 *
 * iHAART and CollaboRhythm are free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * iHAART and CollaboRhythm are distributed in the hope that they will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with iHAART and CollaboRhythm. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.alyeska.shared.ane.iHAART.freInterface;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.adobe.fre.FREContext;

import java.util.List;

public class IHAARTIntentService extends IntentService
{

	private static final String LOCAL_TAG = "IHAARTIntentService";

	public IHAARTIntentService()
	{
		super("IHAARTIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		Bundle ex = intent.getExtras();
		String title = ex.getString("EXTRA_ALARM_COUNT");
		String alert = "Alert message here...";

//        context.getActivity().getApplication();

		Context context = this.getApplicationContext();
		String action = intent.getAction();

		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "in onHandleIntent: " + action);

		FREContext freContext = IHAARTExtension.context;

		Context appContext;
		///App is in foreground
		if (freContext != null)
		{
			freContext.dispatchStatusEventAsync("added", "testing");
			appContext = freContext.getActivity();
		} else  //App is not in foreground
		{
			appContext = context.getApplicationContext();
		}

		if (freContext != null)
		{
			freContext.dispatchStatusEventAsync("added", "testing2");
		}


//        if (action.equals("android.intent.action.SET_ALARM"))
//        {
//            GCMRegistrar.setRetryBroadcastReceiver(context);
//            this.handleRegistration(context, intent);
//        }


//        if (!isAppInForeground(appContext))
//        {
//            String ns = Context.NOTIFICATION_SERVICE;
//            NotificationManager mNotificationManager = (NotificationManager) appContext.getSystemService(ns);
//
//            int iconSmall = Resources.getResourceIdByName(appContext.getPackageName(), "drawable", "notifysmall");
//            int iconLarge = Resources.getResourceIdByName(appContext.getPackageName(), "drawable",
//                                                          "notifylarge"); //not currently used
//            CharSequence tickerText = alert;
//            long when = System.currentTimeMillis();
//
//            CharSequence contentTitle = title == null ? "Incoming Notification" : title;
//            CharSequence contentText = alert;
//
//            // define sound URI, the sound to be played when there's a notification
//            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//            long[] vibratePattern = new long[60];
//            long duration = 2000;
//            for (int i = 0; i < 60; i = i + 1)
//            {
//                long mod = i % 2;
//                if (mod > 0)
//                {
//                    vibratePattern[i] = duration;
//                } else
//                {
//                    vibratePattern[i] = 200;
//                }
//            }

//			FLAG_INSISTENT FLAG_SHOW_LIGHTS PRIORITY_MAX (priority = 2)

//            try
//            {
//                Intent notificationIntent = new Intent(appContext,
//                                                       Class.forName(appContext.getPackageName() + ".AppEntry"));
//                notificationIntent.putExtra("data", "Data string is going here");
//
//                Notification builder = new Notification.Builder(appContext)
//                        .setContentTitle("IHAART Notification")
//                        .setContentText(alert)
//                        .setTicker(tickerText)
//                        .setSmallIcon(iconSmall)
//                        .setWhen(when)
//                        .setPriority(2)
//                        .setOngoing(true)
//                        .setVibrate(vibratePattern)
//                        .setLights(Color.RED, 500, 500)
//                        .setDefaults(Notification.DEFAULT_SOUND)
//                        .setContentIntent(PendingIntent.getActivity(appContext, 0, notificationIntent,
//                                                                    PendingIntent.FLAG_UPDATE_CURRENT))
//                        .build();
//                //PendingIntent contentIntent = PendingIntent.getActivity(appContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                //notification.setLatestEventInfo(appContext, contentTitle, contentText, contentIntent);
//
//                // Vibrate the mobile phone
////                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
////                vibrator.vibrate(8000);
//
//                int HELLO_ID = 1;
////				todo bree make tag dynamic
//                String tag = "AlarmANE";
//                mNotificationManager.notify(tag, HELLO_ID, builder);
//            } catch (IllegalStateException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (ClassNotFoundException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }


	}

//	@Override
//	public void onRegistered(Context context, String regId)
//	{
//		FREContext freContext = AlarmExtension.context;
//		freContext.dispatchStatusEventAsync("registered", regId);
//	}
//
//	@Override
//	public void onUnregistered(Context context, String regId)
//	{
//		FREContext freContext = AlarmExtension.context;
//		freContext.dispatchStatusEventAsync("unregistered", regId);
//	}

//	@Override
//	public void onMessage(Context context, Intent intent)
//	{
//		Bundle ex = intent.getExtras();
//		String title = ex.getString("title");
//		String alert = ex.getString("alert");
//		String type = ex.getString("type");
//		String id = ex.getString("id");
//		String payload = "title: " + title + ",alert: " + alert + ",type: " + type + "," + "id: " + id;
//
//		FREContext freContext = AlarmExtension.context;
//
//		Context appContext;
//		if(freContext != null)
//		{
//			freContext.dispatchStatusEventAsync("foregroundMessage", payload);
//			appContext = freContext.getActivity();
//		}
//		else
//		{
//			appContext = context.getApplicationContext();
//		}
//
//		if(freContext != null)
//		{
//			freContext.dispatchStatusEventAsync("message", payload);
//		}
//
//		if(!isAppInForeground(appContext))
//		{
//			String ns = Context.NOTIFICATION_SERVICE;
//			NotificationManager mNotificationManager = (NotificationManager) appContext.getSystemService(ns);
//
//			int iconSmall = Resources.getResourceIdByName(appContext.getPackageName(), "drawable", "notifysmall");
//			int iconLarge = Resources.getResourceIdByName(appContext.getPackageName(), "drawable", "notifylarge"); //not currently used
//			CharSequence tickerText = alert;
//			long when = System.currentTimeMillis();
//
//			//Notification notification = new Notification(icon, tickerText, when);
//			//notification.defaults = Notification.DEFAULT_ALL;
//			//notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//			CharSequence contentTitle = title == null ? "Incoming Notification" : title;
//			CharSequence contentText = alert;
//
//			// define sound URI, the sound to be played when there's a notification
//			Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//			long[] vibratePattern = new long[60];
//			long duration = 2000;
//			for (int i = 0; i < 60; i = i + 1)
//			{
//				long mod = i % 2;
//				if (mod > 0) {
//					vibratePattern[i] = duration;
//				}
//				else {
//					vibratePattern[i] = 200;
//				}
//			}
//
////			FLAG_INSISTENT FLAG_SHOW_LIGHTS PRIORITY_MAX (priority = 2)
//
//			try
//			{
//				Intent notificationIntent = new Intent(appContext,
//						Class.forName(appContext.getPackageName() + ".AppEntry"));
//				notificationIntent.putExtra("data", payload);
//
//				Notification builder = new Notification.Builder(appContext)
//						.setContentTitle("IHAART Notification")
//						.setContentText(alert)
//						.setTicker(tickerText)
//						.setSmallIcon(iconSmall)
//						.setWhen(when)
//						.setPriority(2)
//						.setOngoing(true)
//						.setVibrate(vibratePattern)
//						.setLights(Color.RED, 500, 500)
//						.setDefaults(Notification.DEFAULT_SOUND)
//						.setContentIntent(PendingIntent.getActivity(appContext, 0, notificationIntent,
//																	PendingIntent.FLAG_UPDATE_CURRENT))
//						.build();
//				//PendingIntent contentIntent = PendingIntent.getActivity(appContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//				//notification.setLatestEventInfo(appContext, contentTitle, contentText, contentIntent);
//
//				int HELLO_ID = 1;
////				todo bree make tag dynamic
//				String tag = "iHAART";
//				//mNotificationManager.notify(HELLO_ID, notification);
//				mNotificationManager.notify(tag, HELLO_ID, builder);
//			}
//			catch (IllegalStateException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			catch (ClassNotFoundException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	private boolean isAppInForeground(Context context)
	{
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses != null)
		{
			final String packageName = context.getPackageName();
			for (RunningAppProcessInfo appProcess : appProcesses)
			{
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(
						packageName))
				{
					return true;
				}
			}
		}

		return false;
	}
}