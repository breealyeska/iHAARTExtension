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
import android.widget.*;

import java.util.Calendar;

public class MedPopoverFrameView extends FrameLayout
{
	private static final String LOCAL_TAG = "MedPopoverFrameView";
	private static final int FRAME_PADDING = 20;
	private static final int TEXT_PADDING = 20;
	private static final int TEXT_Y_PADDING = 50;
	private static final int TEXT_RECT_PADDING = 3;

	public static final String RSTR_ID_BUTTON_CANCEL = "id.med_popover_button_cancel";
	public static final String RSTR_ID_BUTTON_SAVE = "id.med_popover_button_save";
	public static final String RSTR_ID_RADIO_NOW = "id.med_popover_rb_now";
	public static final String RSTR_ID_RADIO_LATER = "id.med_popover_rb_later";
	public static final String RSTR_ID_RADIOG_NOW_LATER = "id.med_popover_rg";
	public static final String RSTR_ID_TEXT_NAME = "id.med_popover_tv_name";
	public static final String RSTR_ID_TEXT_TIME = "id.med_popover_tv_time";
	public static final String RSTR_ID_TEXT_DOSE = "id.med_popover_tv_dose";
	public static final String RSTR_ID_TEXT_INSTRUCTIONS = "id.med_popover_tv_instructions";
	public static final String RSTR_ID_TIME_PICKER = "id.med_popover_time_picker";
	public static final String RSTR_ID_PICKER_LAYOUT = "id.time_picker_layout";
	public static final String RSTR_ID_LINEAR_LAYOUT = "id.mp_linear_layout";
	public static final String RSTR_ID_RELATIVE_LAYOUT = "id.mp_relative_layout";
	public static final String RSTR_DRAWABLE_TEXT_BACKGROUND = "drawable.bg_vgradient_white";

	private TextView _nameTextView = null;
	private TextView _timeTextView = null;
	private TextView _doseTextView = null;
	private TextView _instructionsTextView = null;
	private Button _cancelButton = null;
	private Button _saveButton = null;
	private RadioButton _nowRadioButton = null;
	private RadioButton _laterRadioButton = null;
	private RadioGroup _nowLaterRadioGroup = null;
	private TimePicker _timePicker = null;
	private LinearLayout _timePickerLayout = null;
	private RelativeLayout _relativeLayout = null;

	private Calendar _timeTakenCal = null;
//	private Point _touchPoint = null;

//	public MedPopoverFrameView(Context context, String popoverText, float touchX, float touchY)
//	{
//		super(context);
//		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "popover constructor ... touch x=" + touchX + " y=" + touchY);
//
//		try {
//			this.setWillNotDraw(false);
//			this.setBackgroundColor(Color.parseColor("#96000000"));
//
//		} catch (Exception e)
//		{
//			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "popover constructor failed with error: " + e.toString());
//		}
//	}

//	public MedPopoverFrameView(Context context)
//	{
//		super(context);
//		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "popover constructor(context)");
//
//		try
//		{
//			this.setWillNotDraw(false);
//			this.setBackgroundColor(Color.parseColor("#96000000"));
//			this.setClickable(true);
//			this.setPadding(TEXT_PADDING, TEXT_Y_PADDING, TEXT_PADDING, 0);
//
//			_timeTakenCal = Calendar.getInstance();
//
//		} catch (Exception e)
//		{
//			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "popover constructor failed with error: " + e.toString());
//		}
//	}

	public MedPopoverFrameView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "popover constructor(context, attrs)");

		try
		{
			this.setWillNotDraw(false);
			this.setBackgroundColor(Color.parseColor("#96000000"));
			this.setClickable(true);
			this.setPadding(TEXT_PADDING, TEXT_Y_PADDING, TEXT_PADDING, 0);

			_timeTakenCal = Calendar.getInstance();

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "popover constructor failed with error: " + e.toString());
		}
	}

//	public MedPopoverFrameView(Context context, AttributeSet attrs, int defStyleAttr)
//	{
//		super(context, attrs, defStyleAttr);
//		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "popover constructor(context, attrs, defStyleAttr)");
//
//		try
//		{
//			this.setWillNotDraw(false);
//			this.setBackgroundColor(Color.parseColor("#96000000"));
//
//		} catch (Exception e)
//		{
//			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "popover constructor failed with error: " + e.toString());
//		}
//	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onLayout");
		super.onLayout(changed, left, top, right, bottom);

//		if (changed) {
//			centerTextAboveTouch();

		_cancelButton = get_cancelButton();
		_saveButton = get_saveButton();
		_timePicker = get_timePicker();

		setSaveOnClick();
		setCancelOnClick();
		setRGOnCheckedChange();
		setTimePickerOnTimeChange();
//		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onDraw");
		super.onDraw(canvas);

//		Paint outline = new Paint();
//		outline.setColor(Color.WHITE);
//		outline.setStyle(Paint.Style.FILL_AND_STROKE);
//		outline.setStrokeWidth(1);
//		outline.setAntiAlias(true);
//
//		Path outlinePath = new Path();
//		RectF closeOutlineRectF = new RectF(TEXT_PADDING + _cancelButton.getX()+2, TEXT_Y_PADDING+2, TEXT_PADDING + _cancelButton.getX() + _cancelButton.getWidth()-2, TEXT_Y_PADDING + _cancelButton.getHeight()+35);
//		outlinePath.addRoundRect(closeOutlineRectF, 10, 10, Path.Direction.CW);
//
//		canvas.drawPath(outlinePath, outline);


		try
		{
//			Path path = getOutlinePath();
////			canvas.drawPath(path, rectPaint);
//
//			Paint rectPaint = getOutlinePaintFill();
//			canvas.drawPath(path, rectPaint);

		} catch(Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "onDraw failed with error: " + e.toString());
		}

	}

	public void setSaveOnClick()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "setSaveOnClick");

		Button saveButton = _saveButton;

		saveButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onClickListener saveButton");
			}
		});
	}

	public void setCancelOnClick()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "setCancelOnClick");

		Button cancelButton = _cancelButton;

		cancelButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onClickListener cancelButton");

					hidePopover();
				}
		});
	}

	public void setRGOnCheckedChange()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "setRGOnCheckedChange");

		get_nowLaterRadioGroup().setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onCheckedChanged");
				try
				{
					if (checkedId == Utilities.GetFreContextResourceId(RSTR_ID_RADIO_NOW))
					{
						Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   NOW clicked...");
						_timeTakenCal = Calendar.getInstance();
						Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   hour=" + _timeTakenCal.get(Calendar.HOUR_OF_DAY) + " minute=" + _timeTakenCal.get(Calendar.MINUTE));
						get_timePickerLayout().setVisibility(GONE);
						get_relativeLayout().invalidate();
					} else if (checkedId == Utilities.GetFreContextResourceId(RSTR_ID_RADIO_LATER))
					{
						Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   LATER clicked...");
						Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   hour=" + _timeTakenCal.get(Calendar.HOUR_OF_DAY) + " minute=" + _timeTakenCal.get(Calendar.MINUTE));
						get_timePickerLayout().setVisibility(VISIBLE);
						get_relativeLayout().invalidate();
					}
				}
				catch(Exception e)
				{
					Utilities.LogItem(Log.ERROR, LOCAL_TAG, "onCheckedChangeListener failed with error: " + e.toString());
				}
			}
		});
	}

	public void setTimePickerOnTimeChange()
	{

		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "setTimePickerOnTimeChange");

		_timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
		{
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
			{
				Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onTimeChangedListener");

				_timeTakenCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
				_timeTakenCal.set(Calendar.MINUTE, minute);

				Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   hour=" + _timeTakenCal.get(Calendar.HOUR_OF_DAY) + " minute=" + _timeTakenCal.get(Calendar.MINUTE));
			}
		});

	}

	public void hidePopover()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "hidePopover");
		try
		{
			ViewGroup popoverViewParent = (ViewGroup) this.getParent();
			popoverViewParent.removeView(this);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "hidePopover failed with error: " + e.toString());
		}
	}

	private Path getOutlinePath()
	{

		View outlineLayout = this.findViewById(Utilities.GetFreContextResourceId(RSTR_ID_LINEAR_LAYOUT));
		RectF outlineRectF = new RectF(outlineLayout.getLeft()-TEXT_RECT_PADDING, outlineLayout.getTop()-TEXT_RECT_PADDING, outlineLayout.getRight()+TEXT_RECT_PADDING, outlineLayout.getBottom()+TEXT_RECT_PADDING);

		Path path = new Path();

		path.setFillType(Path.FillType.EVEN_ODD);
		path.addRoundRect(outlineRectF, 20, 20, Path.Direction.CW);
		path.moveTo(outlineRectF.centerX() - 20, outlineRectF.bottom);
		path.lineTo(this.getWidth()/2, this.getHeight()/2);
		path.lineTo(outlineRectF.centerX() + 20, outlineRectF.bottom);


		return path;
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

	private Paint getOutlinePaintFill()
	{
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

//
//	GETTERS & SETTERS
//
	public TextView get_nameTextView()
	{
		if (_nameTextView == null)
		{
			try {
				_nameTextView = (TextView) this.findViewById(Utilities.GetFreContextResourceId(RSTR_ID_TEXT_NAME));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_nameTextView failed with error: " + e.toString());
			}
		}
		return _nameTextView;
	}

	public TextView get_timeTextView()
	{
		if (_timeTextView == null)
		{
			try
			{
				_timeTextView = (TextView) this.findViewById(Utilities.GetFreContextResourceId(RSTR_ID_TEXT_TIME));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_timeTextView failed with error: " + e.toString());
			}
		}
		return _timeTextView;
	}

	public TextView get_instructionsTextView()
	{
		if (_instructionsTextView == null)
		{
			try
			{
				_instructionsTextView = (TextView) this.findViewById(Utilities.GetFreContextResourceId(RSTR_ID_TEXT_INSTRUCTIONS));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_instructionsTextView failed with error: " + e.toString());
			}
		}
		return _instructionsTextView;
	}

	public TextView get_doseTextView()
	{
		if (_doseTextView == null)
		{
			try
			{
				_doseTextView = (TextView) this.findViewById(Utilities.GetFreContextResourceId(RSTR_ID_TEXT_DOSE));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_doseTextView failed with error: " + e.toString());
			}
		}
		return _doseTextView;
	}

	public Button get_cancelButton()
	{
		if (_cancelButton == null)
		{
			try
			{
				_cancelButton = (Button) this.findViewById(Utilities.GetFreContextResourceId(
						RSTR_ID_BUTTON_CANCEL));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_cancelButton failed with error: " + e.toString());
				_cancelButton = new Button(this.getContext());
			}
		}
		return _cancelButton;
	}

	public Button get_saveButton()
	{
		if (_saveButton == null)
		{
			try
			{
				_saveButton = (Button) this.findViewById(Utilities.GetFreContextResourceId(RSTR_ID_BUTTON_SAVE));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_saveButton failed with error: " + e.toString());
				_saveButton = new Button(this.getContext());
			}
		}
		return _saveButton;
	}

	public RadioButton get_laterRadioButton()
	{
		if (_laterRadioButton == null)
		{
			try
			{
				_laterRadioButton = (RadioButton) this.findViewById(Utilities.GetFreContextResourceId(RSTR_ID_RADIO_LATER));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_laterRadioButton failed with error: " + e.toString());
			}
		}
		return _laterRadioButton;
	}

	public RadioButton get_nowRadioButton()
	{
		if (_nowRadioButton == null)
		{
			try
			{
				_nowRadioButton = (RadioButton) this.findViewById(
						Utilities.GetFreContextResourceId(RSTR_ID_RADIO_NOW));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_nowRadioButton failed with error: " + e.toString());
			}
		}
		return _nowRadioButton;
	}

	public RadioGroup get_nowLaterRadioGroup()
	{
		if (_nowLaterRadioGroup == null)
		{
			try
			{
				_nowLaterRadioGroup = (RadioGroup) this.findViewById(Utilities.GetFreContextResourceId(RSTR_ID_RADIOG_NOW_LATER));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_nowLaterRadioGroup failed with error: " + e.toString());
			}
		}
		return _nowLaterRadioGroup;
	}

	public LinearLayout get_timePickerLayout()
	{
		if (_timePickerLayout == null)
		{
			try
			{
				_timePickerLayout = (LinearLayout) this.findViewById(Utilities.GetFreContextResourceId(RSTR_ID_PICKER_LAYOUT));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_timePickerLayout failed with error: " + e.toString());
			}
		}
		return _timePickerLayout;
	}

	public RelativeLayout get_relativeLayout()
	{
		if (_relativeLayout == null)
		{
			try
			{
				_relativeLayout = (RelativeLayout) this.findViewById(Utilities.GetFreContextResourceId(RSTR_ID_RELATIVE_LAYOUT));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_relativeLayout failed with error: " + e.toString());
			}
		}
		return _relativeLayout;
	}

	public TimePicker get_timePicker()
	{
		if (_timePicker == null)
		{
			try
			{
				_timePicker = (TimePicker) this.findViewById(Utilities.GetFreContextResourceId(RSTR_ID_TIME_PICKER));
			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "get_timePicker failed with error: " + e.toString());
			}
		}
		return _timePicker;
	}
}
