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

public class ClockMedicationImageView extends ClockMasterImageView
{
	private static final String LOCAL_TAG = "ClockMedicationImageView";

	private static final int ARC_STROKE_THICKNESS = 30;
	private static final int ARC_OUTLINE_THICKNESS = 3;

	private double _drugStartTime;
	private double _drugEndTime;
	private Bitmap _medBitmap;

	private Medication _medication;

	private float _medCircleRadius, _clockRadius;

	private Path _medCircleArcOutlinePath = null;
	private Paint _medCircleOutlinePaint = null, _medCircleFillPaint = null, _arcFillPaint = null;
	private RectF _medImageRectF = null, _medCircleRectF = null, _clockCircleRectF = null, _clockArcCircleRectF = null;

	private Region _onTouchRegion = null;

	public ClockMedicationImageView(Context context, Medication medication, Bitmap bitmap)
	{
		super(context);
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "ClockMedicationImageView constructor");

		_medication = medication;
		_drugStartTime = getHourInDecimal(Utilities.GetCalendarFromDate(_medication.get_startTime()));
		_drugEndTime = getHourInDecimal(Utilities.GetCalendarFromDate(_medication.get_endTime()));
		_medBitmap = bitmap;

		_medCircleArcOutlinePath = new Path();
		_medImageRectF = new RectF();
		_medCircleRectF = new RectF();
		_clockCircleRectF = new RectF();
		_clockArcCircleRectF = new RectF();
		_medCircleOutlinePaint = getMedCircleOutlinePaint(new Paint());
		_medCircleFillPaint = getMedCircleFillPaint(new Paint());
		_arcFillPaint = getArcFillPaint(new Paint());

	}


	@Override
	protected void set_frameIndex(int _frameIndex)
	{
		try
		{
			super.set_frameIndex(_frameIndex);
			getClockFrameView().set_medicationImageIndex(_frameIndex);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "set_frameIndex failed with error: " + e.toString());
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onLayout");
		super.onLayout(changed, l, t, r, b);

		if (changed)
		{
			_clockRadius = get_clockRadius();
			_medCircleRadius = _clockRadius - ClockMasterImageView.TIME_INDICATOR_SIDE * 1.2f;
			_medCircleArcOutlinePath = getMedCircleArcOutlinePath(_medCircleArcOutlinePath);
			_medImageRectF = getMedImageRectF(_medImageRectF, _medCircleRadius * 0.75f);
			_medCircleRectF = getMedCircleRectF(_medCircleRectF, _medCircleRadius);
			_clockCircleRectF = getClockCircleRectF(_clockCircleRectF);
			_clockArcCircleRectF = getClockArcCircleRectF(_clockArcCircleRectF);
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onDraw");
		super.onDraw(canvas);

		if (_medBitmap != null)
		{

			_medCircleArcOutlinePath = getMedCircleArcOutlinePath(_medCircleArcOutlinePath);
			drawArcFill(canvas);
			drawMedCircle(canvas);

			try
			{
				canvas.drawBitmap(_medBitmap, null, _medImageRectF, null);
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "onDraw drawBitmap failed with error: " + e.toString());
			}

			try
			{
				getTimeIndicatorImageView().bringToFront();
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG,
								  "onDraw bring time indicator to front failed with error: " + e.toString());
			}

		}
	}

	private void drawArcFill(Canvas canvas)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "drawArcFill");
		double startAngle = getHourAngle(_drugStartTime, -90); //-90 to convert y axis as 0 to x axis as 0
		double endAngle = getHourAngle(_drugEndTime, -90); //-90 to convert y axis as 0 to x axis as 0
		double sweepAngle = endAngle - startAngle;

		try
		{
			canvas.drawArc(_clockCircleRectF, (float) startAngle, (float) sweepAngle, false, _arcFillPaint);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "drawArcFill failed with error: " + e.toString());
		}
	}

	private void drawMedCircle(Canvas canvas)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "drawMedCircle");

		double startAngle = getHourAngle(_drugStartTime) - 90;
		double endAngle = getHourAngle(_drugEndTime) - 90;
		double outerSweepAngle = endAngle - startAngle;
		double medSweepAngle = 360.0 - Math.abs(endAngle - startAngle);

		Point startMedArc = getCirclePointByAngle(endAngle + 90, _medCircleRadius);
		Point endMedArc = getCirclePointByAngle(startAngle + 90, _medCircleRadius);
		Point startOuterArc = getCirclePointByAngle((startAngle + 90), _clockRadius + (ARC_STROKE_THICKNESS / 2.0f));
		Point endOuterArc = getCirclePointByAngle(endAngle + 90, _clockRadius + (ARC_STROKE_THICKNESS / 2.0f));

		try
		{
			Path path = new Path();
			path.setFillType(Path.FillType.EVEN_ODD);

			path.arcTo(_medCircleRectF, (float) endAngle, (float) medSweepAngle);
			path.lineTo(startOuterArc.x, startOuterArc.y);
			path.arcTo(_clockArcCircleRectF, (float) startAngle, (float) outerSweepAngle);
			path.lineTo(startMedArc.x, startMedArc.y);


			canvas.drawPath(path, _medCircleOutlinePaint);
			canvas.drawPath(path, _medCircleFillPaint);

			_onTouchRegion = new Region();
			Region clip = new Region(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
			_onTouchRegion.setPath(path, clip);

		} catch (NullPointerException e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "drawMedCircle failed with NullPointerException error: " + e.getMessage());
		}
		catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "drawMedCircle failed with error: " + e.toString());
		}
	}

//	private void drawMedCircle(Canvas canvas)
//	{
//		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "drawMedCircle");
//
//		double startAngle = getHourAngle(_drugStartTime) - 90;
//		double endAngle = getHourAngle(_drugEndTime) - 90;
//		double outerSweepAngle = endAngle - startAngle;
//		double medSweepAngle = 360.0 - Math.abs(endAngle - startAngle);
//
//		Point startMedArc = getCirclePointByAngle(endAngle + 90, _medCircleRadius);
//		Point endMedArc = getCirclePointByAngle(startAngle + 90, _medCircleRadius);
//		Point startOuterArc = getCirclePointByAngle((startAngle + 90), _clockRadius + (ARC_STROKE_THICKNESS / 2.0f));
//		Point endOuterArc = getCirclePointByAngle(endAngle + 90, _clockRadius + (ARC_STROKE_THICKNESS / 2.0f));
//
//		try
//		{
//
//			Path path = new Path();
//			path.setFillType(Path.FillType.EVEN_ODD);
//
//			path.arcTo(_medCircleRectF, (float) endAngle, (float) medSweepAngle);
//			path.lineTo(startOuterArc.x, startOuterArc.y);
//			path.arcTo(_clockArcCircleRectF, (float) startAngle, (float) outerSweepAngle);
//			path.lineTo(startMedArc.x, startMedArc.y);
//
//			canvas.drawPath(path, _medCircleOutlinePaint);
//			canvas.drawPath(path, _medCircleFillPaint);
//
//			RectF boundsRect = new RectF();
//			path.computeBounds(boundsRect, true);
//
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   _clockArcCircleRectF");
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       left=" + _clockArcCircleRectF.left);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       top =" + _clockArcCircleRectF.top);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       righ=" + _clockArcCircleRectF.right);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       bott=" + _clockArcCircleRectF.bottom);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       widt=" + _clockArcCircleRectF.width());
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       heig=" + _clockArcCircleRectF.height());
//
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   _medCircleRectF");
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       left=" + _medCircleRectF.left);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       top =" + _medCircleRectF.top);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       righ=" + _medCircleRectF.right);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       bott=" + _medCircleRectF.bottom);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       widt=" + _medCircleRectF.width());
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       heig=" + _medCircleRectF.height());
//
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   boundsRect");
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       left=" + boundsRect.left);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       top =" + boundsRect.top);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       righ=" + boundsRect.right);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       bott=" + boundsRect.bottom);
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       widt=" + boundsRect.width());
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "       heig=" + boundsRect.height());
//
//			_onTouchRegion = new Region((int) boundsRect.left, (int) boundsRect.top, (int) boundsRect.right, (int) boundsRect.bottom);
//		} catch (Exception e)
//		{
//			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getMedCircleOutlinePath failed with error: " + e.toString());
//		}
//
//		try
//		{
////				_onTouchRegion = new Region(startOuterArc.x, (int) (get_centerPoint().y - _clockRadius), endOuterArc.x, (int) (get_centerPoint().y + _medCircleRadius));
//		} catch (Exception e)
//		{
//			Utilities.LogItem(Log.ERROR, LOCAL_TAG,
//							  "onDraw set Region failed with error: " + e.toString());
//		}
//
////		try
////		{
//////			canvas.drawPath(_medCircleArcOutlinePath, _medCircleOutlinePaint);
//////			canvas.drawPath(_medCircleArcOutlinePath, _medCircleFillPaint);
////
////		} catch (Exception e)
////		{
////			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "drawMedCircle failed with error: " + e.toString());
////		}
//	}

	private Paint getArcFillPaint(Paint paint)
	{
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(ARC_STROKE_THICKNESS);
		paint.setColor(Color.GREEN);
		paint.setAlpha(Utilities.PercentToRGBInteger(100));

		return paint;
	}

	private Paint getMedCircleOutlinePaint(Paint paint)
	{
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(ARC_OUTLINE_THICKNESS);
		paint.setColor(Color.BLACK);
		paint.setAlpha(Utilities.PercentToRGBInteger(20));

		return paint;
	}

	private Paint getMedCircleFillPaint(Paint paint)
	{
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(ARC_OUTLINE_THICKNESS);
		paint.setColor(Color.GREEN);
		paint.setAlpha(Utilities.PercentToRGBInteger(10));

		return paint;
	}

	private RectF getClockCircleRectF(RectF rectF)
	{
		float radius = get_clockRadius();
		Point centerPoint = get_centerPoint();
		float circleLeft = centerPoint.x - radius;
		float circleTop = centerPoint.y - radius;
		float circleRight = centerPoint.x + radius;
		float circleBottom = centerPoint.y + radius;

		rectF.set(circleLeft, circleTop, circleRight, circleBottom);
		return rectF;
	}

	private RectF getClockArcCircleRectF(RectF rectF)
	{
		try {
			float radius = _clockRadius + (ARC_STROKE_THICKNESS / 2.0f);
			Point centerPoint = get_centerPoint();
			float circleLeft = centerPoint.x - radius;
			float circleTop = centerPoint.y - radius;
			float circleRight = centerPoint.x + radius;
			float circleBottom = centerPoint.y + radius;

			rectF.set(circleLeft, circleTop, circleRight, circleBottom);
			return rectF;
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getClockArcCircleRectF failed with error: " + e.toString());
			return null;
		}
	}

	private RectF getMedCircleRectF(RectF rectF, float radius)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "getMedCircleRectF");

		Point centerPoint = get_centerPoint();
		float circleLeft = centerPoint.x - radius;
		float circleTop = centerPoint.y - radius;
		float circleRight = centerPoint.x + radius;
		float circleBottom = centerPoint.y + radius;

		rectF.set(circleLeft, circleTop, circleRight, circleBottom);
		return rectF;
	}

	private RectF getMedImageRectF(RectF rectF, float radius)
	{
		Point centerPoint = get_centerPoint();
		float circleLeft = centerPoint.x - radius;
		float circleTop = centerPoint.y - radius;
		float circleRight = centerPoint.x + radius;
		float circleBottom = centerPoint.y + radius;

		rectF.set(circleLeft, circleTop, circleRight, circleBottom);
		return rectF;
	}

	private Path getMedCircleArcOutlinePath(Path path)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "getMedCircleArcOutlinePath");

		path.setFillType(Path.FillType.EVEN_ODD);

		double startAngle = getHourAngle(_drugStartTime) - 90; //-90
		double endAngle = getHourAngle(_drugEndTime) - 90; //-90
		double outerSweepAngle = endAngle - startAngle;
		double medSweepAngle = 360.0 - Math.abs(endAngle - startAngle);

		try
		{
			Point startMedArc = getCirclePointByAngle(endAngle + 90, _medCircleRadius);
//			Point endMedArc = getCirclePointByAngle(startAngle + 90, _medCircleRadius);
			Point startOuterArc = getCirclePointByAngle((startAngle + 90), _clockRadius + (ARC_STROKE_THICKNESS / 2.0f));
//			Point endOuterArc = getCirclePointByAngle(endAngle + 90, _clockRadius + (ARC_STROKE_THICKNESS / 2.0f));

			path.arcTo(_medCircleRectF, (float) endAngle, (float) medSweepAngle);
			path.lineTo(startOuterArc.x, startOuterArc.y);
			path.arcTo(_clockArcCircleRectF, (float) startAngle, (float) outerSweepAngle);
			path.lineTo(startMedArc.x, startMedArc.y);

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getMedCircleOutlinePath failed with error: " + e.toString());
		}

		return path;
	}

	public Bitmap get_medBitmap()
	{
		return _medBitmap;
	}

	public void set_medBitmap(Bitmap _medBitmap)
	{
		this._medBitmap = _medBitmap;
	}

	public Region get_onTouchRegion()
	{
		return _onTouchRegion;
	}

	public Medication get_medication()
	{
		return _medication;
	}
}
