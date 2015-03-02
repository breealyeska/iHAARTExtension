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
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class ClockFrameView extends FrameLayout
{
	private static final String LOCAL_TAG = "ClockFrameView";

	public static final String RSTR_ID_CLOCK_FRAME_LAYOUT = "id.clock_frame_layout";

	private static final int FRAME_PADDING = 25;

	private Point _centerPoint = new Point();

	private int _clockBackgroundIndex;
	private int _timeIndicatorIndex;
	private int _medicationImageIndex;

	public ClockFrameView(Context context)
	{
		super(context);
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "ClockFrameView constructor");
		this.setWillNotDraw(false);
	}

	public ClockFrameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.setWillNotDraw(false);
	}

	public ClockFrameView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.setWillNotDraw(false);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onSizeChanged");
		super.onSizeChanged(w, h, oldw, oldh);

		try
		{
			_centerPoint.set((int) ((this.getRight() - this.getLeft()) / 2.0),
							 (int) ((this.getBottom() - this.getTop()) / 2.0));
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "onSizeChanged failed with error: " + e.toString());
		}

	}

	@Override
	public void addView(View child, ViewGroup.LayoutParams params)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "addView(child, layoutparams)");

		int newIndex = this.getChildCount();
		super.addView(child, params);

		ClockMasterImageView view = (ClockMasterImageView) child;
		view.set_frameIndex(newIndex);
	}

	@Override
	public void addView(View child)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "addView(child)");

		int newIndex = this.getChildCount();
		super.addView(child);

		ClockMasterImageView view = (ClockMasterImageView) child;
		view.set_frameIndex(newIndex);
	}

	@Override
	public void addView(View child, int width, int height)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "addView(child, width, height)");

		try
		{


			int newIndex = this.getChildCount();
			super.addView(child, width, height);

			ClockMasterImageView view = (ClockMasterImageView) child;
			view.set_frameIndex(newIndex);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "addView(child, width, height) failed with error: " + e.toString());
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onDraw");

		super.onDraw(canvas);

//		canvas.drawRect(getFrameRect(), getFramePaint());
//		canvas.drawRoundRect(getFrameInnerRectF(), 30, 30, getFrameInnerPaint());
//		canvas.drawRoundRect(getFrameInnerRectF(), 30, 30, getFrameOutlinePaint());

	}

	private Paint getFramePaint()
	{
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(3);
		paint.setColor(Color.BLACK);
		paint.setAlpha(Utilities.PercentToRGBInteger(40));

		return paint;
	}

	private Paint getFrameInnerPaint()
	{
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(3);
		paint.setColor(Color.WHITE);
		paint.setAlpha(Utilities.PercentToRGBInteger(100));

		return paint;
	}

	private Paint getFrameOutlinePaint()
	{
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setColor(Color.BLACK);
		paint.setAlpha(Utilities.PercentToRGBInteger(100));

		return paint;
	}

	private Rect getFrameRect()
	{
		return new Rect(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
	}

	private RectF getFrameInnerRectF()
	{
		return new RectF(this.getLeft()+FRAME_PADDING, this.getTop()+FRAME_PADDING, this.getRight()-FRAME_PADDING, this.getBottom()-FRAME_PADDING);
	}

//	@Override
//	protected void onLayout(boolean changed, int l, int t, int r, int b)
//	{
//		super.onLayout(changed, l, t, r, b);
//
//		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onLayout");
//
////		DisplayMetrics displayMetrics = new DisplayMetrics();
////		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
////		int height = displayMetrics.heightPixels;
////		int width = displayMetrics.widthPixels;
////
////		bg = (ImageView) rootView.findViewById(R.id.rectimage);
////		bg.getLayoutParams().width = width;
////		bg.getLayoutParams().height = 500;
////
////		MarginLayoutParams params = (MarginLayoutParams) bg.getLayoutParams();
////		params.setMargins(0, height - 510, 0, 0);
//
////		for (int i = 0; i < this.getChildCount(); i++)
////		{
////			Utilities.LogItem(Log.DEBUG, LOCAL_TAG,
////						   "onLayout clockFrameView.getChildAt(" + i + ") " + this.getChildAt(i).getClass().toString());
////		}
//	}


//
//	GETTERS & SETTERS
//
	public Point get_centerPoint()
	{
		return _centerPoint;
	}

	public int get_clockBackgroundIndex()
	{
		return _clockBackgroundIndex;
	}

	public int get_medicationImageIndex()
	{
		return _medicationImageIndex;
	}

	public int get_timeIndicatorIndex()
	{
		return _timeIndicatorIndex;
	}

	public void set_clockBackgroundIndex(int _clockBackgroundIndex)
	{
		this._clockBackgroundIndex = _clockBackgroundIndex;
	}

	public void set_medicationImageIndex(int _medicationImageIndex)
	{
		this._medicationImageIndex = _medicationImageIndex;
	}

	public void set_timeIndicatorIndex(int _timeIndicatorIndex)
	{
		this._timeIndicatorIndex = _timeIndicatorIndex;
	}

}
