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
import android.widget.ImageView;

import java.util.Calendar;

public class ClockMasterImageView extends ImageView
{

	private static final String LOCAL_TAG = "ClockMasterImageView";

	protected static final float RADIUS_MULTIPLIER = 0.5f;
	protected static final double TOTAL_HOURS = 24.0;
	protected static final double TOTAL_DEGREES = 360.0;

	public static final int TIME_INDICATOR_SIDE = 146;

	public static final String RSTR_DRAWABLE_CLOCK_BACKGROUND = "drawable.clock_bubble_ring";
	public static final String RSTR_DRAWABLE_TIME_INDICATOR = "drawable.time_indicator";

	protected String _imageResourceString = "";

	private Point _centerPoint = new Point();
	private Point _topLeftPoint = new Point();
	private float _clockRadius = 0.0f;

	private int _clockTypeAngleCorrection;
	private int _xCorrection;
	private int _yCorrection;

	private int _frameIndex;

	public ClockMasterImageView(Context context)
	{
		super(context);

		if (Config.TWENTY_FOUR_HOUR_DISPLAY) {
			_clockTypeAngleCorrection = Clocks.TWENTYFOUR.typeAngleCorrection;
			_xCorrection = Clocks.TWENTYFOUR.xCorrection;
			_yCorrection = Clocks.TWENTYFOUR.yCorrection;
		} else {
			_clockTypeAngleCorrection = Clocks.TWELVE.typeAngleCorrection;
			_xCorrection = Clocks.TWELVE.xCorrection;
			_yCorrection = Clocks.TWELVE.yCorrection;
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onSizeChanged " + _imageResourceString);
		super.onSizeChanged(w, h, oldw, oldh);

//		_centerX = w / 2.0;
//		_centerY = h / 2.0;
//		_centerPoint.set((int) (w / 2.0), (int) (h / 2.0));
		_centerPoint.set((int) ((this.getRight()-this.getLeft())/2.0), (int) ((this.getBottom() - this.getTop()) / 2.0));

//		Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   old: width=" + oldw + " height=" + oldh + "  current: width=" + w + " height=" + h);
//		Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   _centerX=" + _centerX + " _centerY=" + _centerY);
//		Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   _centerPoint.x=" + _centerPoint.x + " _centerPoint.y=" + _centerPoint.y);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onLayout " + _imageResourceString);
		centerInFrame();
	}

	protected ClockFrameView getClockFrameView()
	{
//		todo bree this needs to be error checked for parent classtypew
		return (ClockFrameView) this.getParent();
	}

	protected ClockBackgroundImageView getClockBackgroundImageView()
	{
//		Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "getClockBackgroundImageView");
//		todo bree this needs to be error checked for existence of clock background imageview
		ClockFrameView frameView = getClockFrameView();
		return (ClockBackgroundImageView) frameView.getChildAt(frameView.get_clockBackgroundIndex());
	}

	protected TimeImageView getTimeIndicatorImageView()
	{
//		todo bree this needs to be error checked for existence of time Indicator imageview
		ClockFrameView frameView = getClockFrameView();
		return (TimeImageView) frameView.getChildAt(frameView.get_timeIndicatorIndex());
	}

	protected double getHourInDecimal(Calendar cal)
	{
		return cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) / 60.0 + cal.get(Calendar.SECOND) / 360.0;
	}

	protected double getHourAngle(double hour)
	{
		return (((hour / TOTAL_HOURS) * TOTAL_DEGREES) + _clockTypeAngleCorrection);
	}

	protected double getHourAngle(double hour, double axisAngleCorrection)
	{
		return (((hour / TOTAL_HOURS) * TOTAL_DEGREES) + axisAngleCorrection + _clockTypeAngleCorrection);
	}

	protected Point getCirclePointByAngle(double angle)
	{
//		angle is in degrees
//		Assumes 0 degrees being at y axis

		Point point = new Point();

		angle = Math.toRadians(angle);
		double deltaX = get_clockRadius() * (Math.sin(angle)) * _xCorrection;
		double deltaY = get_clockRadius() * (Math.cos(angle)) * _yCorrection;

		int newCenterX = (int) (deltaX + getClockBackgroundImageView().get_centerPoint().x);
		int newCenterY = (int) (deltaY + getClockBackgroundImageView().get_centerPoint().y);

		point.set(newCenterX, newCenterY);

		return point;
	}

	protected Point getCirclePointByAngle(double angle, double radius)
	{
//		angle is in degrees
//		Assumes 0 degrees being at y axis

		Point point = new Point();

		angle = Math.toRadians(angle);
		double deltaX = radius * (Math.sin(angle)) * _xCorrection;
		double deltaY = radius * (Math.cos(angle)) * _yCorrection;

		int newCenterX = (int) (deltaX + getClockBackgroundImageView().get_centerPoint().x);
		int newCenterY = (int) (deltaY + getClockBackgroundImageView().get_centerPoint().y);

		point.set(newCenterX, newCenterY);

		return point;
	}

	protected float getLeftFromCenter(float centerX)
	{
		try {
			int width = this.getWidth();
			return centerX - (width / 2.0f);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getLeftFromCenter failed with error: " + e.toString());
			return -1.0f;
		}

	}

	protected float getTopFromCenter(float centerY)
	{
		try
		{
			int height = this.getHeight();
			return centerY - (height / 2.0f);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getTopFromCenter failed with error: " + e.toString());
			return -1.0f;
		}
	}

	protected float getRightFromCenter(float centerX)
	{
		try
		{
			int width = this.getWidth();
			return centerX + (width / 2.0f);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getRightFromCenter failed with error: " + e.toString());
			return -1.0f;
		}

	}

	protected float getBottomFromCenter(float centerY)
	{
		try
		{
			int height = this.getHeight();
			return centerY + (height / 2.0f);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getBottomFromCenter failed with error: " + e.toString());
			return -1.0f;
		}
	}

	protected Point getTopLeftFromCenter(Point centerPoint)
	{
		Point topLeft = new Point();
		topLeft.set((int) getLeftFromCenter(centerPoint.x), (int) getTopFromCenter(centerPoint.y));
		return topLeft;
	}

	protected void centerInFrame() {
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "centerInFrame");
		ClockFrameView frame = getClockFrameView();
		Point newCoords = getTopLeftFromCenter(frame.get_centerPoint());
		this.setX(newCoords.x);
		this.setY(newCoords.y);
	}

//
//	GETTERS & SETTERS
//
	protected float get_clockRadius()
	{
		try
		{
//		todo bree this needs to be error checked for existence of clock background imageview
		return (getClockBackgroundImageView().getWidth() / 2.0f) - RADIUS_MULTIPLIER * getTimeIndicatorImageView().getWidth();

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_clockRadius failed with error: " + e.toString());
			return 0.0f;
		}

	}

	protected float get_innerCircleRadius()
	{
		//		todo bree this needs to be error checked for existence of clock background imageview
		return (get_clockRadius() - TIME_INDICATOR_SIDE * 1.2f);
	}

	protected Point get_centerPoint()
	{
		return _centerPoint;
	}

	protected void set_centerPoint(Point _centerPoint)
	{
		this._centerPoint = _centerPoint;
	}

	protected Point get_topLeftPoint()
	{
		return _topLeftPoint;
	}

	protected String getImageSrc()
	{
		return _imageResourceString;
	}

	public int get_frameIndex()
	{
		return _frameIndex;
	}

	protected void set_frameIndex(int _frameIndex)
	{
		this._frameIndex = _frameIndex;
	}

	public enum Clocks
	{
		TWELVE(0, 1, 1),
		TWENTYFOUR(-180, 1, -1);

		private int typeAngleCorrection;
		private int xCorrection;
		private int yCorrection;

		private Clocks(int angleCorrectionParam, int xCorrectionParam, int yCorrectionParam)
		{
			typeAngleCorrection = angleCorrectionParam;
			xCorrection = xCorrectionParam;
			yCorrection = yCorrectionParam;
		}
	}

}
