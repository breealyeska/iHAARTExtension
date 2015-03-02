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
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.TextView;

public class PopoverFrameView extends FrameLayout
{
	private static final String LOCAL_TAG = "PopoverFrameView";
	private static final int FRAME_PADDING = 40;
	private static final int TEXT_PADDING = 20;
	private static final int TEXT_Y_PADDING = 150;
	private static final int TEXT_RECT_PADDING = 3;
	private static final String RSTR_DRAWABLE_TEXT_BACKGROUND = "drawable.bg_vgradient_white";

	private TextView _textView = null;
	private Point _touchPoint = null;

	public PopoverFrameView(Context context, String popoverText, float touchX, float touchY)
	{
		super(context);
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "popover constructor  touch x=" + touchX + " y=" + touchY);

		try {
			this.setWillNotDraw(false);

			_textView = getTextView(context, popoverText);
			_touchPoint = new Point();
			_touchPoint.set((int) touchX, (int) touchY);

//			this.setPadding(FRAME_PADDING, FRAME_PADDING, FRAME_PADDING, FRAME_PADDING);
			this.setBackgroundColor(Color.parseColor("#96000000"));
	//		this.setGravity(Gravity.CENTER);

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "popover constructor failed with error: " + e.toString());
		}
	}

	public PopoverFrameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PopoverFrameView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onDraw");
		super.onDraw(canvas);

		try
		{

			Paint rectPaint = getOutlinePaintOutline();
			Path path = getOutlinePath();
//			canvas.drawPath(path, rectPaint);

			rectPaint = getOutlinePaintFill();
			canvas.drawPath(path, rectPaint);

		}	catch(Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "onDraw failed with error: " + e.toString());
		}

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onLayout");
		if (changed) {
			super.onLayout(changed, left, top, right, bottom);

			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   frame left=" + getLeft() + " top=" + getTop() + " right=" + getRight() + " bottom=" + getBottom());
			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "");
			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   _textView left=" + _textView.getLeft() + " top=" + _textView.getTop() + " right=" + _textView.getRight() + " bottom=" + _textView.getBottom());
			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "             getX=" + _textView.getX() + " getY=" + _textView.getY());
			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "             width=" + _textView.getWidth() + " height=" + _textView.getHeight());

			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "");
			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "");

			centerTextAboveTouch();

			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "");
			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "");

			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   _textView left=" + _textView.getLeft() + " top=" + _textView.getTop() + " right=" + _textView.getRight() + " bottom=" + _textView.getBottom());
			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "             getX=" + _textView.getX() + " getY=" + _textView.getY());
			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "             width=" + _textView.getWidth() + " height=" + _textView.getHeight());

		}
	}

	private Paint getOutlinePaintOutline()
	{
		Paint paint = new Paint();

		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
//			todo bree switch to getting color resource by string
//		paint.setColor(Color.parseColor("#182843"));
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(4.0f);

		return paint;
	}

	private Paint getOutlinePaintFill() {
		Paint paint = new Paint();

		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		paint.setAlpha(Utilities.PercentToRGBInteger(50));
//			todo bree switch to getting color resource by string
//		paint.setColor(Color.parseColor("#182843"));
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(1.0f);

		return paint;
	}

	private RectF getOutlineRectF() {

		RectF outlineRectF = new RectF();

		float outlineRectLeft = _textView.getX() - TEXT_RECT_PADDING;
		float outlineRectTop = _textView.getY() - TEXT_RECT_PADDING;
		float outlineRectRight = _textView.getX() + _textView.getWidth() + TEXT_RECT_PADDING;
		float outlineRectBottom = _textView.getY() + _textView.getHeight() + TEXT_RECT_PADDING;

		outlineRectF.set(outlineRectLeft,
						outlineRectTop,
						outlineRectRight,
						outlineRectBottom);

		return outlineRectF;

	}

	private Path getOutlinePath() {

		RectF outlineRectF = getOutlineRectF();

		Path path = new Path();

		path.setFillType(Path.FillType.EVEN_ODD);
		path.addRoundRect(getOutlineRectF(), 20, 20, Path.Direction.CW);
		path.moveTo(outlineRectF.centerX() - 20, outlineRectF.bottom);
		path.lineTo(_touchPoint.x, _touchPoint.y);
		path.lineTo(outlineRectF.centerX() + 20, outlineRectF.bottom);


		return path;
	}

	private TextView getTextView(Context context, String text) {

		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "getTextView text=" + text);

		TextView textView = null;

		for (int i = 0; i < this.getChildCount(); i++)
		{
			if (this.getChildAt(i).getClass() == TextView.class) {
				Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   textView found");
				textView = (TextView) this.getChildAt(i);
				i = this.getChildCount();
			}
		}

		if (textView == null)
		{
			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   textView not found");
			textView = new TextView(context);
			this.addView(textView);

			LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			try
			{
				textView.setLayoutParams(layoutParams);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f);
				textView.setPadding(TEXT_PADDING, TEXT_PADDING, TEXT_PADDING, TEXT_PADDING);
				textView.setTextColor(Color.BLACK);
				textView.setTextAppearance(this.getContext(), android.R.style.TextAppearance_DeviceDefault_Widget_PopupMenu_Large);
				textView.setBackground(
						getResources().getDrawable(Utilities.GetFreContextResourceId(RSTR_DRAWABLE_TEXT_BACKGROUND)));
//				textView.setGravity(Gravity.CENTER);
				textView.setText(text);
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getTextView failed with error: " + e.toString());
			}
		}

		return textView;
	}

	private void centerTextAboveTouch()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "centerTextAboveTouch");

		float textWidth = (float) _textView.getWidth();
		float textHeight = (float) _textView.getHeight();

		float setX = _touchPoint.x - (textWidth / 2.0f);
		float setY = _touchPoint.y - textHeight - TEXT_Y_PADDING;

		Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   setX=" + setX + " setY=" + setY);

		if (setX < this.getLeft() + FRAME_PADDING)
		{
			setX = this.getLeft() + FRAME_PADDING;
			_textView.setX(setX);
		}

		if (setY < this.getTop() + FRAME_PADDING)
		{
			setY = this.getTop() + FRAME_PADDING;
			_textView.setY(setY);
			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   2 setY=" + setX);
		}

		if (setX + _textView.getWidth() > this.getRight() - FRAME_PADDING)
		{
			setX = this.getRight() - textWidth - FRAME_PADDING;
			_textView.setX(setX);
		}

		if (setY + _textView.getHeight() > this.getBottom() - FRAME_PADDING)
		{
			setY = getBottom() - textHeight - FRAME_PADDING;
			_textView.setY(setY);
			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   3 setY=" + setY);
		}

		_textView.setX(setX);
		_textView.setY(setY);

//			Utilities.LogCornerPoints(this);

	}

	//
//	GETTERS & SETTERS
//
	public TextView get_textView()
	{
		if (_textView == null)
		{
			getTextView(this.getContext(), "testing");
		}
		return _textView;
	}
}
