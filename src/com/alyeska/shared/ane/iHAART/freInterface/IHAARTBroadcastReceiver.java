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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class IHAARTBroadcastReceiver extends BroadcastReceiver
{

	private static final String LOCAL_TAG = "IHAARTBroadcastReceiver";

//	@Override
//	protected java.lang.String getAlarmIntentServiceClassName(Context context)
//	{
//		String intentClassName = super.getAlarmIntentServiceClassName(context);
//		intentClassName = "com.alyeska.shared.ane.iHAART.freInterface.AlarmIntentService";
//		//FREContext freContext = AlarmExtension.context;
//		//freContext.dispatchStatusEventAsync("intentClassName", intentClassName);
//		return intentClassName;
//	}

	@Override
	public void onReceive(Context context, Intent intent)
	{

		Utilities.LogItem(Log.INFO, LOCAL_TAG, "now in onReceive");
	}
}