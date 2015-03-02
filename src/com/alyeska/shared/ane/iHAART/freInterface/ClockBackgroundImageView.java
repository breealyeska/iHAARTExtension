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
import android.graphics.*;
import android.util.Log;

import java.util.Date;

public class ClockBackgroundImageView extends ClockMasterImageView
{
	private static final String LOCAL_TAG = "ClockBackgroundImageView";
	private static final int ARC_STROKE_THICKNESS = 30;
	private static final int ARC_OUTLINE_THICKNESS = 3;

	private double _drugStartTime;
	private double _drugEndTime;

	public ClockBackgroundImageView(Context context)
	{

		super(context);
		_imageResourceString = ClockMasterImageView.RSTR_DRAWABLE_CLOCK_BACKGROUND;

	}

	public ClockBackgroundImageView(Context context, Date startTime, Date endTime)
	{

		super(context);
		_imageResourceString = ClockMasterImageView.RSTR_DRAWABLE_CLOCK_BACKGROUND;

//		Calendar cal = Utilities.GetCalendarFromDate(startTime);
//		getHourInDecimal(Utilities.GetCalendarFromDate(startTime));

		_drugStartTime = getHourInDecimal(Utilities.GetCalendarFromDate(startTime));
		_drugEndTime = getHourInDecimal(Utilities.GetCalendarFromDate(endTime));
//		this.setBackgroundColor(Color.parseColor("#fef5f5"));
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onDraw " + _imageResourceString);
		super.onDraw(canvas);

//		drawArcFill(canvas);
//
//		float medCircleRadius = get_clockRadius() - ClockMasterImageView.TIME_INDICATOR_SIDE * 1.2f;
//		drawMedCircle(canvas, medCircleRadius);
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

	private void drawArcFill(Canvas canvas)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "drawArcFill");
		RectF rectWedge = getClockCircleRectF();
		Paint arcFill = getArcFillPaint();
		double startAngle = getHourAngle(_drugStartTime, -90); //-90 to convert y axis as 0 to x axis as 0
		double endAngle = getHourAngle(_drugEndTime, -90); //-90 to convert y axis as 0 to x axis as 0
		double sweepAngle = endAngle - startAngle;

		try {
			canvas.drawArc(rectWedge, (float) startAngle, (float) sweepAngle, false, arcFill);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "drawArcFill failed with error: " + e.toString());
		}
	}

	private void drawMedCircle(Canvas canvas, float radius)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "drawMedCircle");

//		RectF rectMedCircle = getMedCircleRectF(radius);
		Paint medCircleOutline = getMedCircleOutlinePaint();
		Paint medCircleFill = getMedCircleFillPaint();
//		double startAngle = getHourAngle(_drugStartTime, -90); //-90 to convert y axis as 0 to x axis as 0
//		double endAngle = getHourAngle(_drugEndTime, -90); //-90 to convert y axis as 0 to x axis as 0
//		double sweepAngle = TOTAL_DEGREES - Math.abs(endAngle - startAngle);

		try {
			canvas.drawPath(getMedCircleArcOutlinePath(radius), medCircleOutline);
			canvas.drawPath(getMedCircleArcOutlinePath(radius), medCircleFill);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "drawMedCircle failed with error: " + e.toString());
		}
	}

	private Paint getArcFillPaint()
	{
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(ARC_STROKE_THICKNESS);
		paint.setColor(Color.GREEN);
		paint.setAlpha(Utilities.PercentToRGBInteger(80));

		return paint;
	}

	private Paint getMedCircleOutlinePaint()
	{
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(ARC_OUTLINE_THICKNESS);
		paint.setColor(Color.BLACK);
		paint.setAlpha(Utilities.PercentToRGBInteger(10));

		return paint;
	}

	private Paint getMedCircleFillPaint()
	{
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(ARC_OUTLINE_THICKNESS);
		paint.setColor(Color.GREEN);
		paint.setAlpha(Utilities.PercentToRGBInteger(10));

		return paint;
	}

	private RectF getClockCircleRectF() {
		float radius = get_clockRadius();
		Point centerPoint = get_centerPoint();
		float circleLeft = centerPoint.x - radius;
		float circleTop = centerPoint.y - radius;
		float circleRight = centerPoint.x + radius;
		float circleBottom = centerPoint.y + radius;

		return new RectF(circleLeft, circleTop, circleRight, circleBottom);
	}

	private RectF getClockArcCircleRectF()
	{
		float radius = get_clockRadius()+(ARC_STROKE_THICKNESS/2.0f);
		Point centerPoint = get_centerPoint();
		float circleLeft = centerPoint.x - radius;
		float circleTop = centerPoint.y - radius;
		float circleRight = centerPoint.x + radius;
		float circleBottom = centerPoint.y + radius;

		return new RectF(circleLeft, circleTop, circleRight, circleBottom);
	}

	private RectF getMedCircleRectF(float radius)
	{
		Point centerPoint = get_centerPoint();
		float circleLeft = centerPoint.x - radius;
		float circleTop = centerPoint.y - radius;
		float circleRight = centerPoint.x + radius;
		float circleBottom = centerPoint.y + radius;

		return new RectF(circleLeft, circleTop, circleRight, circleBottom);
	}

	private Path getMedCircleArcOutlinePath(float medCircleRadius)
	{
		Path path = new Path();
		path.setFillType(Path.FillType.EVEN_ODD);

		double startAngle = getHourAngle(_drugStartTime) - 90;
		double endAngle = getHourAngle(_drugEndTime) - 90;
		double outerSweepAngle = endAngle - startAngle;
		double medSweepAngle = 360.0 - Math.abs(endAngle - startAngle);

		RectF rectArcCircle = getClockArcCircleRectF();
		RectF rectMedCircle = getMedCircleRectF(medCircleRadius);

		Point startMedArc = getCirclePointByAngle(endAngle + 90, medCircleRadius);
		Point startOuterArc = getCirclePointByAngle(startAngle + 90);

		try
		{
			path.arcTo(rectMedCircle, (float) endAngle, (float) medSweepAngle);
			path.lineTo(startOuterArc.x, startOuterArc.y);
			path.arcTo(rectArcCircle, (float) startAngle, (float) outerSweepAngle);
			path.lineTo(startMedArc.x, startMedArc.y);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getMedCircleOutlinePath failed with error: " + e.toString());
		}

		return path;
	}

	@Override
	protected void set_frameIndex(int _frameIndex)
	{
		try
		{
			super.set_frameIndex(_frameIndex);
			getClockFrameView().set_clockBackgroundIndex(_frameIndex);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "set_frameIndex failed with error: " + e.toString());
		}
	}
}
