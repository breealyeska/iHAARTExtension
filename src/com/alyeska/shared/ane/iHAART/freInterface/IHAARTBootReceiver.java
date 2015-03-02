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

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class IHAARTBootReceiver extends BroadcastReceiver
{
	private static final String LOCAL_TAG = "IHAARTBootReceiver";

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "now in onReceive");
		Bundle ex = intent.getExtras();
		if (ex != null)
		{

			AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

//			alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//			String state = ex.getString(TelephonyManager.EXTRA_STATE);
			Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "now in onReceive2 " + ex.toString());
//			if (state.equals(TelephonyManager.EXTRA_STATE_RINGING))
			{
//				String phoneNumber = ex
//						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
//				Log.w("MY_DEBUG_TAG", phoneNumber);
			}
		}
	}


}
