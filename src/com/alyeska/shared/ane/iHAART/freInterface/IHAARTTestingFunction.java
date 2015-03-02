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

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class IHAARTTestingFunction implements FREFunction
{

	private static final String LOCAL_TAG = "IHAARTTestingFunction";

	@Override
	public FREObject call(FREContext context, FREObject[] passedArgs)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "Testing functions...");

		int layoutID, buttonID, textViewID;
		Intent intent = new Intent(context.getActivity(), TimerActivity.class);

		try
		{
			layoutID = context.getResourceId("layout.util_main");
			buttonID = context.getResourceId("id.button");
			textViewID = context.getResourceId("id.timerTextView");

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getResourceID()failed with error: " + e.toString());
			Toast.makeText(context.getActivity(), "getResourceID() failed" +
					e.toString(), Toast.LENGTH_LONG).show();
			return null;
		}
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "layout ID: " + layoutID);

		intent.putExtra("layoutID", layoutID);
		intent.putExtra("buttonID", buttonID);
		intent.putExtra("timerTextViewID", textViewID);

		try
		{
			context.getActivity().startActivity(intent);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "startActivity() failed with error: " + e.toString());
			Toast.makeText(context.getActivity(), "startActivity() failed: " +
					e.toString(), Toast.LENGTH_LONG).show();
		}
		return null;
	}
}