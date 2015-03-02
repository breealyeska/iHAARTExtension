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
import com.adobe.fre.*;

public class IHAARTClockFunction implements FREFunction
{
	private static final String LOCAL_TAG = "IHAARTClockFunction";

	static final int LOGIN = 1;  // The request code

	@Override
	public FREObject call(FREContext context, FREObject[] passedArgs)
	{
		FREObject result;

		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "Starting clock activity");

		String medicationJSON = "";

		Intent intent = new Intent(context.getActivity(), ClockActivity.class);

		try
		{
			medicationJSON = passedArgs[0].getAsString();
		} catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FRETypeMismatchException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FREInvalidObjectException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FREWrongThreadException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		intent.putExtra("medicationJSON", medicationJSON);

		try
		{
			context.getActivity().startActivityForResult(intent, LOGIN);
			result = FREObject.newObject("clock activity started message");
			return result;
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "startActivity() failed with error: " + e.toString());
			Toast.makeText(context.getActivity(), "startActivity() failed: " +
					e.toString(), Toast.LENGTH_LONG).show();
		}
		return null;
	}
}