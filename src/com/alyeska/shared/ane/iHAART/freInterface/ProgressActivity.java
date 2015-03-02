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
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ProgressActivity extends Activity implements MyListener
{

	private static final String LOCAL_TAG = "ProgressActivity";

	private ProgressDialog progress;
	private ProgressBar progressCircle;

	static int DONE = 0;
	static int RUNNING = 1;   // Class constants defining state of the thread

	private Handler progressHandler;
	int mState;
	int total;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);

		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "ProgressActivity.onCreate");

		int layoutID = getIntent().getIntExtra("layoutID", -1);

		setContentView(layoutID);


		View myV = findViewById(android.R.id.content);
		String alphaString = String.valueOf(myV.getAlpha());

		myV.setAlpha((float) 0.6);

		Toast.makeText(this, " background alpha now = " + alphaString,
					   Toast.LENGTH_SHORT).show();

		int showButtonID = getIntent().getIntExtra("showButtonID", -1);
		int doneButtonID = getIntent().getIntExtra("doneButtonID", -1);
		int progressCircleID = getIntent().getIntExtra("progressCircleID", -1);

		progressCircle = (ProgressBar) findViewById(progressCircleID);
		progressCircle.setVisibility(View.GONE);

		Button showButton = (Button) findViewById(showButtonID);
		showButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				open(v);
			}
		});

		Button doneButton = (Button) findViewById(doneButtonID);
		doneButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				close(v);
			}
		});

//		setContentView(R.layout.activity_progress);
		progress = new ProgressDialog(this);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setIndeterminate(true);
	}

	public void close(View view)
	{
		progressCircle.setVisibility(View.GONE);
//		Intent intent = new Intent(this, ProgressActivity.Class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(intent);
		finish(); // Call once you redirect to another activity

	}

	public void open(View view)
	{
		progressCircle.setVisibility(View.VISIBLE);

		final int totalProgressTime = 100;

		final Thread t = new Thread()
		{

			@Override
			public void run()
			{

//				int jumpTime = 0;
//				while (jumpTime < totalProgressTime)
//				{
//					try
//					{
//						sleep(200);
//						jumpTime += 5;
//						progress.setProgress(jumpTime);
//					} catch (InterruptedException e)
//					{
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}

//				todo bree add handler for running progress spinner
			}
		};
		t.start();
	}

	// method invoke
	@Override
	public void callback(View view, String result)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "in callback ");
		Toast.makeText(this, "in callback: " + result,
					   Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "ProgressActivity.onStart");
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "ProgressActivity.onRestart");
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "ProgressActivity.onResume");
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "ProgressActivity.onPause");
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "ProgressActivity.onStop");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "ProgressActivity.onDestroy");
	}
}
