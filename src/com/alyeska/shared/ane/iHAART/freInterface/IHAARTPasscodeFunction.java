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

public class IHAARTPasscodeFunction implements FREFunction
{
	private static final String LOCAL_TAG = "IHAARTPasscodeFunction";

	static final int LOGIN = 1;  // The request code

	@Override
	public FREObject call(FREContext context, FREObject[] passedArgs)
	{
		FREObject result;

		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "Starting passcode activity");

		Intent intent = new Intent(context.getActivity(), PasscodeActivity.class);

//		try
//		{
//
//			for (int i=0; i<10; i++)
//			{
//				String idStr = "id.button" + String.valueOf(i);
//				digitButtonIDArray[i] = context.getResourceId(idStr);
//			}
//
//			for (int i = 0; i < Config.PASSCODE_LENGTH; i++)
//			{
//				String idStr = "id.imageView" + String.valueOf(i);
//				imageViewIDArray[i] = context.getResourceId(idStr);
//			}
//
//		} catch (Exception e)
//		{
//			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "loadResources failed with error: " + e.toString());
//			Toast.makeText(context.getActivity(), "loadResources () failed: " + e.toString(), Toast.LENGTH_LONG).show();
//			return null;
//		}
//
//		intent.putExtra("digitButtonIDArray", digitButtonIDArray);
//		intent.putExtra("imageViewIDArray", imageViewIDArray);

		try
		{
			context.getActivity().startActivityForResult(intent, LOGIN);
			result = FREObject.newObject("start passcode activity message");
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