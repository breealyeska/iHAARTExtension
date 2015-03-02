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

import android.util.Log;
import com.adobe.air.ComAlyeskaAndroidActivityWrapper;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.Map;

public class IHAARTContext extends FREContext // implements ActivityResultCallback
{
	private static final String LOCAL_TAG = "IHAARTContext";

	static final int LOGIN = 1;  // The request code

	private ComAlyeskaAndroidActivityWrapper myAAW = null;

	public void initialize()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "Now in IHAARTContext.initialize()");
//		Activity activity = this.getActivity();
//		myAAW = new ComAlyeskaAndroidActivityWrapper(activity);
//		myAAW.addActivityResultListener(this);
//		myAAW.addActivityStateChangeListener(this);
	}

//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent intent)
//	{
//		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "Now in IHAARTContext.onActivityResult()");
//
//		// Check which request we're responding to
//		if (requestCode == LOGIN)
//		{
//			// Make sure the request was successful
//			if (resultCode == Activity.RESULT_OK)
//			{
//				// get return info from intent bundle
//
//				Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "In onActivityResult");
//
//				// Do something with the contact here (bigger example below)
//			} else
//			{
//				Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "In onActivityResult failed");
//				// request unsuccessful
//			}
//		}
//	}

//	public void onActivityStateChanged(ActivityState state)
//	{
//		switch (state)
//		{
//			case STARTED:
//			case RESTARTED:
//			case RESUMED:
//			case PAUSED:
//			case STOPPED:
//			case DESTROYED:
//		}
//	}
//
//	public void onConfigurationChanged(Configuration paramConfiguration)
//	{
//	}

	@Override
	public Map<String, FREFunction> getFunctions()
	{
		Map<String, FREFunction> map = new HashMap<String, FREFunction>();

		map.put("initIHAART", new IHAARTInitFunction());
		map.put("showProgress", new IHAARTShowProgressFunction());
		map.put("startPasscodeActivity", new IHAARTPasscodeFunction());
		map.put("startClockActivity", new IHAARTClockFunction());
		map.put("testStuff", new IHAARTTestingFunction());

		return map;
	}

	@Override
	public void dispose()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "Now in IHAARTContext.dispose()");
		if (myAAW != null)
		{
//			myAAW.removeActivityResultListener(this);
//			myAAW.removeActivityStateChangeListener(this);
			myAAW = null;
		}
	}

}
