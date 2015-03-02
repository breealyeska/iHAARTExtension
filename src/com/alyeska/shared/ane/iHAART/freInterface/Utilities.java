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

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import com.adobe.fre.FREContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utilities
{

	public static final String TAG = "IHAARTANE";
	private static final String LOCAL_TAG = "Utilities";

	public static void LogItem(int logLevel, String localTag, String logText)
	{
		switch (logLevel)
		{
			case Log.VERBOSE:
				Log.v(TAG, "(in " + localTag + "): " + logText);
				break;
			case Log.INFO:
				Log.i(TAG, "(in " + localTag + "): " + logText);
				break;
			case Log.DEBUG:
				Log.d(TAG, "(in " + localTag + "): " + logText);
				break;
			case Log.WARN:
				Log.w(TAG, "(in " + localTag + "): " + logText);
				break;
			case Log.ERROR:
				Log.e(TAG, "(in " + localTag + "): " + logText);
				break;
			default:
				Log.v(TAG, "(in " + localTag + "): " + logText);
				break;
		}
	}

	public static int PercentToRGBInteger(int percent)
	{
//		Used for converting alpha percent values to int usable in, for example, .setAlpha(int a)
		return (int) ((percent / 100.0) * 255.0);
	}

	public static int StringToInt(String inputString)
	{

		try
		{
			return Integer.parseInt(inputString);
		} catch (NumberFormatException e)
		{
			LogItem(Log.ERROR, LOCAL_TAG, "StringToInt failed with error: " + e.toString());
			return -1;
		}
	}

	public static int GetFreContextResourceId(String resource)
	{
		FREContext freContext = IHAARTExtension.context;
		return freContext.getResourceId(resource);
	}

	public static FREContext GetFreContext()
	{
		return IHAARTExtension.context;
	}

	public static Activity GetFREActivity()
	{

		return GetFreContext().getActivity();
	}

	public static Context GetFREActivityContext()
	{

		return GetFreContext().getActivity();
	}

//	public static Bitmap getBitmapFromURL(String src)
//	{
//		LogItem(Log.VERBOSE, LOCAL_TAG, "getBitmapFromURL");
//		try
//		{
//			URL url = new URL(src);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setDoInput(true);
//			connection.connect();
//			InputStream input = connection.getInputStream();
//			return BitmapFactory.decodeStream(input);
//		} catch (Exception e)
//		{
//			LogItem(Log.ERROR, LOCAL_TAG, "getBitmapFromURL failed with error: " + e.toString());
//			return null;
//		}
//	}

	public static Date GetDateFromString(String dateString)
	{

		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
		try
		{
			Date date = format.parse(dateString);
			return date;
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			LogItem(Log.ERROR, LOCAL_TAG, "GetDateFromString failed with error: " + e.toString());
			return null;
		}
	}

	public static Calendar GetCalendarFromDate(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static void DoneLoading(Context context) {
		try
		{
			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(context, notification);
			r.play();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
