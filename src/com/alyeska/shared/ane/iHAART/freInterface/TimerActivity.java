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

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class TimerActivity extends Activity
{

	private static final String LOCAL_TAG = "TimerActivity";

	TextView timerTextView;
	long startTime = 0;

	//runs without a timer by reposting this handler at the end of the runnable
	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable()
	{

		@Override
		public void run()
		{
			long millis = System.currentTimeMillis() - startTime;
			int seconds = (int) (millis / 1000);
			int minutes = seconds / 60;
			seconds = seconds % 60;

			timerTextView.setText(String.format("%d:%02d", minutes, seconds));

			timerHandler.postDelayed(this, 500);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.util_main);
//
//		timerTextView = (TextView) findViewById(R.id.timerTextView);
//
//		Button b = (Button) findViewById(R.id.button);
//		b.setText(R.string.start);
//		b.setOnClickListener(new View.OnClickListener()
//		{
//
//			@Override
//			public void onClick(View v)
//			{
//				Button b = (Button) v;
//				if (b.getText().equals("stop"))
//				{
//					timerHandler.removeCallbacks(timerRunnable);
//					b.setText(R.string.start);
//				} else
//				{
//					startTime = System.currentTimeMillis();
//					timerHandler.postDelayed(timerRunnable, 0);
//					b.setText(R.string.stop);
//				}
//			}
//		});
//
//		Button myButton = new Button(this);
//		myButton.setText(R.string.quit);
//		myButton.setEnabled(true);
//		myButton.setVisibility(View.VISIBLE);
//		myButton.setOnClickListener(new View.OnClickListener()
//		{
//
//			@Override
//			public void onClick(View v)
//			{
//				finish();
//			}
//		});
	}

	@Override
	public void onPause()
	{
//		super.onPause();
//		timerHandler.removeCallbacks(timerRunnable);
//		Button b = (Button) findViewById(R.id.button);
//		b.setText("start");
	}

}