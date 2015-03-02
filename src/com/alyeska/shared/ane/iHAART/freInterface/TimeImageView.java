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

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import java.util.Calendar;

public class TimeImageView extends ClockMasterImageView
{

	private static final String LOCAL_TAG = "TimeImageView";

	private String _timeString;

	public TimeImageView(Context context)
	{
		super(context);
		_imageResourceString = ClockMasterImageView.RSTR_DRAWABLE_TIME_INDICATOR;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onLayout " + _imageResourceString);

		try
		{

			updateCurrentTime();
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG,
//						   "   centerPoint.x=" + this.get_centerPoint().x + " centerPoint.y=" + this.get_centerPoint().y);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   x=" + this.getX() + " y=" + this.getY());
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG,
//						   "   left=" + this.getLeft() + " top=" + this.getTop() + " right=" + this.getRight() + " bottom=" + this.getBottom());
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   getWidthCenterPoint=" + getWidthCenterPoint(this.getX()) + " getHeightCenterPoint=" + getHeightCenterPoint(this.getY()));
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "onLayout failed with error: " + e.toString());
		}
	}

	public void updateCurrentTime()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "updateCurrentTime " + _imageResourceString);

		Calendar cal = Calendar.getInstance();
		double hour = getHourInDecimal(cal);
		double angle = getHourAngle(hour);

		Point _currentTimeXY = getTopLeftFromCenter(getCirclePointByAngle(angle));

		try
		{
			this.setX(_currentTimeXY.x);
			this.setY(_currentTimeXY.y);

			_timeString = String.format("%02d:%02d", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
			if (cal.get(Calendar.AM_PM) == Calendar.AM) {
				_timeString = _timeString + " AM";
			} else {
				_timeString = _timeString + " PM";
			}
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "updateCurrentTime failed with error: " + e.toString());
		}
	}

	@Override
	protected void set_frameIndex(int _frameIndex)
	{
		try
		{
			super.set_frameIndex(_frameIndex);
			getClockFrameView().set_timeIndicatorIndex(_frameIndex);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "set_frameIndex failed with error: " + e.toString());
		}
	}

	public String get_timeString()
	{
		return _timeString;
	}

//	private void drawTimeText(Canvas canvas)
//	{
//		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "drawTimeText");
//		Calendar c = Calendar.getInstance();
//		String timeText = String.format("%02d:%02d", c.get(Calendar.HOUR), c.get(Calendar.MINUTE));
//		if (c.get(Calendar.AM_PM) == 1)
//		{
//			timeText = timeText + " PM";
//		} else {
//			timeText = timeText + " AM";
//		}
//
//		Paint timeTextPaint = getTimeTextPaint();
//		canvas.drawText(timeText, get_centerPoint().x, get_centerPoint().y+30, timeTextPaint);
//	}
}
