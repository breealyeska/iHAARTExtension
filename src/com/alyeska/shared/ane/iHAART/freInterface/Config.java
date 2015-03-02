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

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Config
{
	private static final String LOCAL_TAG = "Config";

	public static final String PROPERTY_PASSCODE = "iHAARTPasscode";
	public static final String PASSCODE_NOT_SET_DEFAULT = "";
	public static final int PASSCODE_LENGTH = 4;

	public static final String RSTR_DRAWABLE_SPLASH_66 = "drawable.splash_ab_66_00";
	public static final String RSTR_DRAWABLE_SPLASH_ZOOM = "drawable.splash_ab_zoom";
	public static final String RSTR_DRAWABLE_SPLASH_21 = "drawable.splash_ab_21_00";

	public static final String RSTR_LAYOUT_PASSCODE_ACTIVITY = "layout.activity_passcode";
	public static final String RSTR_LAYOUT_CLOCK_ACTIVITY = "layout.activity_clock";

	public static final String RSTR_LAYOUT_TOAST_VIEW = "layout.view_toast";
	public static final String RSTR_LAYOUT_POPOVER_VIEW = "layout.view_popover";
	public static final String RSTR_LAYOUT_MED_POPOVER_VIEW = "layout.view_medication_popover";

	public static final String RSTR_ID_TOAST_LAYOUT = "id.toast_layout";
	public static final String RSTR_ID_POPOVER_LAYOUT = "id.popover_layout";
	public static final String RSTR_ID_MED_POPOVER_LAYOUT = "id.med_popover_layout";

	public static final Boolean TWENTY_FOUR_HOUR_DISPLAY = Boolean.TRUE;

	public void configureActionBar(ActionBar ab, Drawable background)
	{
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		ab.setDisplayUseLogoEnabled(true);
//		ab.setIcon(R.drawable.ic_launcher);
//		ab.setBackgroundDrawable(R.drawable.ab_background_textured_ihaart);
		Utilities.LogItem(Log.INFO, LOCAL_TAG, "Actionbar configured");

	}
}