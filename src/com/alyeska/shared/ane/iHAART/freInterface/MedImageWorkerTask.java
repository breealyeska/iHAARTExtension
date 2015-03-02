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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

class MedImageWorkerTask extends AsyncTask<URL, Void, Bitmap>
{
	private static final String LOCAL_TAG = "MedImageWorkerTask";

	private final WeakReference<Context> contextReference;
	private final WeakReference<ClockFrameView> frameReference;
	private final WeakReference<Medication> medicationReference;

	public MedImageWorkerTask(Context context, ClockFrameView frameView, Medication medication)
	{
		// Use a WeakReference to ensure the ImageView can be garbage collected
		contextReference = new WeakReference<Context>(context);
		frameReference = new WeakReference<ClockFrameView>(frameView);
		medicationReference = new WeakReference<Medication>(medication);
	}

	// Decode image in background.
	@Override
	protected Bitmap doInBackground(URL... urls)
	{

		try
		{
//			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			return BitmapFactory.decodeStream(input);
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	// Once complete, see if ImageView is still around and set bitmap.
	@Override
	protected void onPostExecute(Bitmap bitmap)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onPostExecute");
		if (bitmap != null)
		{
			final Context context = contextReference.get();
			final ClockFrameView frameView = frameReference.get();
			final Medication medication = medicationReference.get();
			if (context != null && frameView != null && medication != null)
			{
				ClockMedicationImageView medImageView = new ClockMedicationImageView(context, medication, bitmap);
				ViewGroup.LayoutParams medLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
																					ViewGroup.LayoutParams.MATCH_PARENT);
				frameView.addView(medImageView, medLayoutParams);
				ClockActivity activity = (ClockActivity) context;
				activity.setMedImageOnTouch(medImageView);
			}
		}
	}
}
