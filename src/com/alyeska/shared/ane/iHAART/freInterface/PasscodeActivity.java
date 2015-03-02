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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.adobe.fre.FREContext;

import java.util.ArrayList;


public class PasscodeActivity extends Activity
{
	private static final String LOCAL_TAG = "PasscodeActivity";

	private static final int STATE_SETTING = 0;
	private static final int STATE_CONFIRMING = 1;
	private static final int STATE_LOGGING_IN = 2;
	private static final int STATE_CHANGING = 3;


	private int[] digitButtonIDArray, imageViewIDArray;
	private Menu passcodeMenu;
	private TextView passcodeInstructionsTextView;
	private ImageButton deleteButton;

	private ArrayList passcode = new ArrayList(Config.PASSCODE_LENGTH);

	private Boolean boolLoggedIn = false, pcodeComplete;
	private String storedPasscode, firstPasscode;
	private int state;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onCreate");
//		setTheme(android.R.style.Theme_Holo_Light_DarkActionBar); //Has to come before contentview is set.
		super.onCreate(savedInstanceState);

		FREContext freContext = IHAARTExtension.context;
		storedPasscode = getCurrentPasscode(this);
		state = STATE_SETTING;
		pcodeComplete = false;


		try {

			setContentView(Utilities.GetFreContextResourceId(Config.RSTR_LAYOUT_PASSCODE_ACTIVITY));
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//			enterPasscodeString = getResources().getString(Utilities.GetFreContextResourceId("string.enter_passcode"));

			deleteButton = (ImageButton) findViewById(Utilities.GetFreContextResourceId("id.deleteButton"));

			setButtonIDArrays();

			configureActionBar(freContext);
			sizeButtons();
			passcodeInstructionsTextView = (TextView) findViewById(Utilities.GetFreContextResourceId("id.passcodeInstructionTextView"));
		} catch (Exception e) {
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getResourceID()failed with error: " + e.toString());
		}

		try {
			deleteButton.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					backspaceClicked(v);
				}
			});

			for (int i = 0; i < digitButtonIDArray.length; i++)
			{
				setButtonOnClick(digitButtonIDArray[i]);
			}
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "set onClick failed with error: " + e.toString());
		}

		if (isNewUser())
		{
			state = STATE_SETTING;
			passcodeInstructionsTextView.setText(getResources().getString(Utilities.GetFreContextResourceId(
					"string.set_passcode")));
		}
		else
		{
			if (boolLoggedIn) {
				state = STATE_CHANGING;
				passcodeInstructionsTextView.setText(getResources().getString(Utilities.GetFreContextResourceId("string.change_passcode")));
			}
			else {
				state = STATE_LOGGING_IN;
				passcodeInstructionsTextView.setText(getResources().getString(Utilities.GetFreContextResourceId("string.enter_passcode")));
			}
		}

		Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   activity.toString()" + Utilities.GetFREActivity().toString());

	}

	private void setButtonOnClick(int buttonID)
	{
		Button digitButton = (Button) findViewById(buttonID);
		digitButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				digitClicked(v);
			}
		});
	}

	private void configureActionBar(FREContext freContext)
	{
		ActionBar ab = this.getActionBar();
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayShowHomeEnabled(true);
		ab.setDisplayUseLogoEnabled(true);
//		ab.setIcon(R.drawable.ic_launcher);
		Drawable abBackground = getResources().getDrawable(Utilities.GetFreContextResourceId(
				"drawable.ab_background_textured_ihaart"));
		ab.setBackgroundDrawable(abBackground);
		Utilities.LogItem(Log.INFO, LOCAL_TAG, "Actionbar configured");

	}

	private void sizeButtons()
	{
		Button digitButton = (Button) findViewById(digitButtonIDArray[0]);
		deleteButton.setMinimumWidth(digitButton.getWidth());
		deleteButton.setMaxWidth(digitButton.getWidth());
		deleteButton.setMinimumHeight(digitButton.getHeight());
		deleteButton.setMaxHeight(digitButton.getHeight());
		Utilities.LogItem(Log.INFO, LOCAL_TAG, "Delete button configured");

	}

	public void backspaceClicked(View v)
	{
		if (passcode.size() > 0) {
			collectPasscode(v, false);
			togglePasscodeImage(passcode.size(), false);
		}
	}

	public void digitClicked(View v)
	{
		collectPasscode(v, true);
		togglePasscodeImage(passcode.size() - 1, true);

		if (pcodeComplete)
		{
			try {
				String pcode = "";
				for (Object aPasscode : passcode)
				{
					pcode = pcode + aPasscode;
				}

				Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "Passcode entered: " + pcode);

				switch (state)
				{
					case STATE_SETTING:
						firstPasscode = pcode;
						resetOnEntry(STATE_CONFIRMING);
						break;
					case STATE_CONFIRMING:
						if (firstPasscode.equals(pcode))
						{
							storeNewPasscode(this.getBaseContext(), pcode);
							onPasscodeChanged(this.getBaseContext());
						} else
						{
							Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "Passcodes didn't match: " + firstPasscode + "  & " + pcode);
							showToast("Passcodes do not match. Try again please.");
							resetOnEntry(STATE_SETTING);
						}
						break;
					case STATE_LOGGING_IN:
						if (pcode.equals(storedPasscode))
						{
							onLoggedIn(v.getContext());
						} else
						{
							boolLoggedIn = false;
							resetOnEntry(STATE_LOGGING_IN);
							showToast("You have entered an incorrect passcode. Try again please.");
						}
						break;
					case STATE_CHANGING:
						if (pcode.equals(storedPasscode))
						{
							resetOnEntry(STATE_SETTING);
						} else
						{
							resetOnEntry(STATE_CHANGING);
							showToast("You have entered an incorrect passcode. Try again please.");
						}
						break;
				}

			} catch (Exception e)
			{
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "digitClicked failed with error: " + e.toString());
			}
		}
	}

	private void setButtonIDArrays() {
		try
		{
			digitButtonIDArray = new int[10];
			imageViewIDArray = new int[Config.PASSCODE_LENGTH];

			for (int i = 0; i < 10; i++)
			{
				String idStr = "id.button" + String.valueOf(i);
				digitButtonIDArray[i] = Utilities.GetFreContextResourceId(idStr);
			}

			for (int i = 0; i < Config.PASSCODE_LENGTH; i++)
			{
				String idStr = "id.imageView" + String.valueOf(i);
				imageViewIDArray[i] = Utilities.GetFreContextResourceId(idStr);
			}

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "setButtonIDArrays failed with error: " + e.toString());
		}
	}

	public void resetOnEntry(int baseState)
	{

		switch (baseState) {
			case STATE_SETTING:
				passcodeInstructionsTextView.setText("Please set a " + Config.PASSCODE_LENGTH + " digit passcode");
				break;
			case STATE_LOGGING_IN:
				passcodeInstructionsTextView.setText(getResources().getString(Utilities.GetFreContextResourceId(
						"string.enter_passcode")));
				break;
			case STATE_CONFIRMING:
				passcodeInstructionsTextView.setText(getResources().getString(Utilities.GetFreContextResourceId(
						"string.confirm_passcode")));
				break;
			case STATE_CHANGING:
				passcodeInstructionsTextView.setText(getResources().getString(Utilities.GetFreContextResourceId(
						"string.change_passcode")));
				break;
		}
		state = baseState;
		passcode.clear();
		pcodeComplete = false;

		for (int i = 0; i < 4; i++)
		{
			togglePasscodeImage(i, false);
		}

		if (baseState != STATE_CONFIRMING)
		{
			firstPasscode = "";
		}
	}

	public void collectPasscode(View v, Boolean add)
	{
		if (add) {
			if (passcode.size() < 4)
			{
				Button b = (Button) v;
				String buttonText = b.getText().toString();

				passcode.add(buttonText);
				if (passcode.size() == 4)
				{
					pcodeComplete = true;
				}
			}
		}
		else {
			if (passcode.size() > 0)
			{
				passcode.remove(passcode.size()-1);
			}
		}
	}

	public void togglePasscodeImage(int index, Boolean add)
	{

		ImageView imageView = (ImageView) findViewById(imageViewIDArray[index]);

		if (add) {
			imageView.setImageResource(Utilities.GetFreContextResourceId("drawable.dot_dark_60x60"));
		}
		else {
			imageView.setImageResource(Utilities.GetFreContextResourceId("drawable.dash_dark_60x60"));
		}
	}

	private String getCurrentPasscode(Context context)
	{
		final SharedPreferences prefs = getPreferences(this);
		return prefs.getString(Config.PROPERTY_PASSCODE, "");
	}

	private void storeNewPasscode(Context context, String pcode)
	{
		try {
			final SharedPreferences prefs = getPreferences(context);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(Config.PROPERTY_PASSCODE, pcode);
			editor.commit();
			Utilities.LogItem(Log.INFO, LOCAL_TAG, "Passcode stored successfully");
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "storeNewPasscode failed with error: " + e.toString());
		}
	}

	private Boolean isNewUser()
	{
		return storedPasscode.equals(Config.PASSCODE_NOT_SET_DEFAULT);
	}

	private void onLoggedIn(Context context)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onLoggedIn");
		storedPasscode = getCurrentPasscode(context);
		boolLoggedIn = true;
		resetOnEntry(STATE_CHANGING);
		showToast("You have logged in successfully.");
		invalidateOptionsMenu();
	}

	private void onPasscodeChanged(Context context)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onPasscodeChanged");
		storedPasscode = getCurrentPasscode(context);
		boolLoggedIn = true;
		resetOnEntry(STATE_CHANGING);
		showToast("New passcode saved!");
		invalidateOptionsMenu();
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	public static SharedPreferences getPreferences(Context context)
	{
		return context.getSharedPreferences(PasscodeActivity.class.getSimpleName(),
											Context.MODE_PRIVATE);
	}

	public void showToast(String textToToast)
	{
//		Utilities.LogItem(Log.INFO, LOCAL_TAG, "showToast");
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showToast: " + textToToast);
		try {

			LayoutInflater inflater = getLayoutInflater();
			// Inflate the Layout
			int customToastLayoutID = Utilities.GetFreContextResourceId("layout.view_toast");
			int customToastLayoutViewID = Utilities.GetFreContextResourceId("id.toast_layout");
			View layout = inflater.inflate(customToastLayoutID, (ViewGroup) findViewById(customToastLayoutViewID));

			TextView text = (TextView) layout.findViewById(Utilities.GetFreContextResourceId("id.toastTextToShow"));
			text.setText(textToToast);

			Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		} catch (Exception e) {
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "showToast failed with error: " + e.toString());
		}
	}

	private void showPasscodeState()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showPasscodeState");

		try
		{
			setContentView(Utilities.GetFreContextResourceId("layout.activity_passcode"));
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "showPasscodeState failed with error: " + e.toString());
		}
	}

	private void showAboutState()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showAboutState");


		try {
			setContentView(Utilities.GetFreContextResourceId("layout.view_about"));
		} catch (Exception e) {
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "showAboutState failed with error: " + e.toString());
		}
	}

	private void showClockState()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showClockState");

		try
		{
			Intent intent = new Intent(this, ClockActivity.class);
			startActivity(intent);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "showClockState failed with error: " + e.toString());
		}
	}

	private void showChangeState()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showChangeState");

		state = STATE_CHANGING;
		passcodeInstructionsTextView.setText(Utilities.GetFreContextResourceId("string.change_passcode"));
	}

	private void showLoginState()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showLoginState");

		state = STATE_LOGGING_IN;
		passcodeInstructionsTextView.setText(Utilities.GetFreContextResourceId("string.enter_passcode"));
	}

	private void showLogoutState()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showLogoutState");
		close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onCreateOptionsMenu");

				// Inflate the menu; this adds items to the action bar if it is present.

		passcodeMenu = menu;

		try
		{
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(Utilities.GetFreContextResourceId("menu.menu_passcode"), menu);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "Error inflating menu in onCreateOptionsMenu: " + e.toString());
		}


		try {
			menu.findItem(Utilities.GetFreContextResourceId("id.menu_about_id")).setVisible(true);
			menu.setGroupVisible(Utilities.GetFreContextResourceId("id.menu_group_passcode_id"), true);
		}
		catch(Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "Error getting menu resources in onCreateOptionsMenu: " + e.toString());
		}

		return true;
	}

// Android Activity Lifecycle Method
// Called when a panel's menu is opened by the user.
	@Override
	public boolean onMenuOpened(int featureId, Menu menu)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onMenuOpened");


		Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "onMenuOpened passcodeMenu.item(0).toString " + passcodeMenu.getItem(0).toString());
		Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "onMenuOpened passcodeMenu.item(1).toString " + passcodeMenu.getItem(1).toString());

		Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "onMenuOpened after setting menuAccounts visible");

		MenuItem menuLogin = menu.findItem(Utilities.GetFreContextResourceId("id.menu_login_id"));
		MenuItem menuLogout = menu.findItem(Utilities.GetFreContextResourceId("id.menu_logout_id"));
		MenuItem menuChange = menu.findItem(Utilities.GetFreContextResourceId("id.menu_change_passcode_id"));

		try
		{
			//set the menu options depending on login status
			if (!isNewUser())
			{
				if (boolLoggedIn)
				{
					//show the log out option
					menuLogout.setVisible(true);
					menuLogin.setVisible(false);

					//show the change selection
					menuChange.setVisible(true);
				} else
				{
					//show the log in option
					menuLogout.setVisible(false);
					menuLogin.setVisible(true);

					//hide the hide selection
					menuChange.setVisible(false);
				}
			} else
			{
//				menu.getItem(0).setVisible(false);
				Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "onMenuOpened menu.toString: " + menu.toString());
				Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "onMenuOpened menu.getItem(0): " + menu.getItem(0).toString());
				//show the log in option
				menuLogout.setVisible(false);
				menuLogin.setVisible(false);

				//hide the hide selection
				menuChange.setVisible(false);
			}

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "Error in onMenuOpened: " + e.toString());
		}

		return true;
	}


// Android Activity Lifecycle Method
// called whenever an item in your options menu is selected
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onOptionsItemSelected");


		if (item.getItemId() == Utilities.GetFreContextResourceId("id.menu_passcode_accounts_id"))
		{
			showPasscodeState();
		}
		else if (item.getItemId() == Utilities.GetFreContextResourceId("id.menu_about_id"))
		{
			showAboutState();
		} else if (item.getItemId() == Utilities.GetFreContextResourceId("id.menu_clock_id"))
		{
			showClockState();
		} else if (item.getItemId() == Utilities.GetFreContextResourceId("id.menu_login_id"))
		{
			showLoginState();
		} else if (item.getItemId() == Utilities.GetFreContextResourceId("id.menu_logout_id"))
		{
			showLogoutState();
		} else if (item.getItemId() == Utilities.GetFreContextResourceId("id.menu_change_passcode_id"))
		{
			showChangeState();
		}
		return true;
	}

	public void close()
	{
		finish(); // Call once you redirect to another activity
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onStart");
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onRestart");
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onResume");
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onPause");
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onStop");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onDestroy");
	}
}
