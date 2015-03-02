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
import android.os.Bundle;
import android.util.Log;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;

public class IHAARTInitFunction implements FREFunction
{

	private static final String LOCAL_TAG = "IHAARTInitFunction";

	@Override
	public FREObject call(FREContext context, FREObject[] args)
	{
		FREObject result;

		Utilities.LogItem(Log.INFO, LOCAL_TAG, "Initializing...");

		String message = "NONE";
		Intent launchIntent = context.getActivity().getIntent();

		if (launchIntent != null)
		{
			Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, launchIntent.toString());
			Bundle ex = launchIntent.getExtras();
			if (ex != null)
			{
				Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, ex.toString());
			}
		}

		try
		{
			result = FREObject.newObject(message);
			return result;
		} catch (FREWrongThreadException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}