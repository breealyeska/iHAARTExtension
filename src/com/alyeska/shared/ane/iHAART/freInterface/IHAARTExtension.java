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
import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class IHAARTExtension implements FREExtension
{
	private static final String LOCAL_TAG = "IHAARTContext";

	public static FREContext context;
//	private Context mContext;
//
//	public IHAARTExtension(Context context)
//	{
//		mContext = context;
//	}

	@Override
	public FREContext createContext(String arg0)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "Now in IHAARTExtension.createContext()");
		context = new IHAARTContext();
		return context;
	}

	@Override
	public void dispose()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "Now in IHAARTExtension.dispose()");
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "Now in IHAARTExtension.initialize()");
		// TODO Auto-generated method stub

	}

}
