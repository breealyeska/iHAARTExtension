package com.alyeska.shared.ane.iHAART.freInterface;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.adobe.fre.FREContext;

import java.net.URL;
import java.text.SimpleDateFormat;

public class ClockActivity extends Activity
{
	private static final String LOCAL_TAG = "ClockActivity";

	private Menu passcodeMenu;

	private String medicationJSON;
	private Medication medication;

	private ClockFrameView clockFrameView;
	private ClockBackgroundImageView clockBackgroundImageView;
	private TimeImageView timeIndicatorImageView;
	private View popoverView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "onCreate");
//		setTheme(android.R.style.Theme_Holo_Light_DarkActionBar); //Has to come before contentview is set.
		super.onCreate(savedInstanceState);

		FREContext freContext = IHAARTExtension.context;


		try {

			medicationJSON = getIntent().getStringExtra("medicationJSON");

			setContentView(Utilities.GetFreContextResourceId(Config.RSTR_LAYOUT_CLOCK_ACTIVITY));
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

			configureActionBar(freContext);

		} catch (Exception e) {
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "setting content view failed with error: " + e.toString());
		}

		medication = new Medication(medicationJSON);

		try
		{

			clockFrameView = (ClockFrameView) findViewById(Utilities.GetFreContextResourceId(ClockFrameView.RSTR_ID_CLOCK_FRAME_LAYOUT));

			configureClockBackground();

			configureClockTimeIndicator();

			configureClockMedImage();

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "onCreate failed with error: " + e.toString());
		}

		try {

			///todo bree need to create onClickListener for the image views.
			setTimeIndicatorOnTouch(); //timeIndicatorImageView

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "set onTouch failed with error: " + e.toString());
		}

		Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   activity.toString()" + Utilities.GetFREActivity().toString());
		Utilities.DoneLoading(getApplicationContext());

	}

	public void configureClockBackground() {

		clockBackgroundImageView = new ClockBackgroundImageView(this, medication.get_startTime(), medication.get_endTime());
		clockBackgroundImageView.setImageResource(Utilities.GetFreContextResourceId(ClockMasterImageView.RSTR_DRAWABLE_CLOCK_BACKGROUND));
		clockBackgroundImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		clockBackgroundImageView.setTag(Utilities.GetFreContextResourceId(ClockMasterImageView.RSTR_DRAWABLE_CLOCK_BACKGROUND), ClockMasterImageView.RSTR_DRAWABLE_CLOCK_BACKGROUND);
		ViewGroup.LayoutParams clockLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
																			  ViewGroup.LayoutParams.WRAP_CONTENT);

		try
		{
			getClockFrameView().addView(clockBackgroundImageView, clockLayoutParams);
		} catch (Exception e) {
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "configureClockBackground failed with error: " + e.toString());
		}
	}

	public void configureClockTimeIndicator()
	{

		timeIndicatorImageView = new TimeImageView(this);
		timeIndicatorImageView.setImageResource(Utilities.GetFreContextResourceId(
				ClockMasterImageView.RSTR_DRAWABLE_TIME_INDICATOR));
		timeIndicatorImageView.setTag(Utilities.GetFreContextResourceId(
											  ClockMasterImageView.RSTR_DRAWABLE_TIME_INDICATOR),
									  ClockMasterImageView.RSTR_DRAWABLE_TIME_INDICATOR);
		ViewGroup.LayoutParams timeLayoutParams = new ViewGroup.LayoutParams(ClockMasterImageView.TIME_INDICATOR_SIDE,
																			 ClockMasterImageView.TIME_INDICATOR_SIDE);

		try
		{
			getClockFrameView().addView(timeIndicatorImageView, timeLayoutParams);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "configureClockTimeIndicator failed with error: " + e.toString());
		}

		setTimeIndicatorOnTouch();
	}

	public void configureClockMedImage()
	{

		try
		{
			MedImageWorkerTask medWorkerTask = new MedImageWorkerTask(this, clockFrameView, medication);
			URL imgURL = new URL(medication.get_imgSource());
			medWorkerTask.execute(imgURL);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "configureClockMedImage failed with error: " + e.toString());
		}
	}

	public void setTimeIndicatorOnTouch() //final TimeImageView timeIndicatorImageView
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "setTimeIndicatorOnTouch");
		timeIndicatorImageView.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "TimeIndicatorOnTouch");
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   getX=" + event.getX() + " getY=" + event.getY());
					Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   getRawX=" + event.getRawX() + " getRawY=" + event.getRawY());
					showPopover(((TimeImageView) v).get_timeString(), event.getRawX(), event.getRawY() - 146);
				} else if (event.getAction() == MotionEvent.ACTION_UP)
				{
					hidePopover();
				}
				return true;
			}
		});
	}

	public void setMedImageOnTouch(ClockMedicationImageView medicationImageView)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "setMedImageOnTouch");

		medicationImageView.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "MedImageOnTouch");
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   getX=" + event.getX() + " getY=" + event.getY());
					Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   getRawX=" + event.getRawX() + " getRawY=" + event.getRawY());

					ClockMedicationImageView medView = (ClockMedicationImageView) v;
					Region region = medView.get_onTouchRegion();

					if (region.contains((int) event.getX(), (int) event.getY())) {
						showMedPopover(medication, event.getX(), event.getY());
					} else {
						Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   does not contain point");
					}
				} else if (event.getAction() == MotionEvent.ACTION_UP)
				{
//					hidePopover();
				}
				return true;
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
		Drawable abBackground = getResources().getDrawable(Utilities.GetFreContextResourceId("drawable.ab_background_textured_ihaart"));
		ab.setBackgroundDrawable(abBackground);
		Utilities.LogItem(Log.INFO, LOCAL_TAG, "Actionbar configured");

	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	public static SharedPreferences getPreferences(Context context)
	{
		return context.getSharedPreferences(ClockActivity.class.getSimpleName(),
											Context.MODE_PRIVATE);
	}

	public void showToast(String textToToast)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showToast: " + textToToast);
		try {

			LayoutInflater inflater = getLayoutInflater();
			// Inflate the Layout
			int customToastLayoutID = Utilities.GetFreContextResourceId(Config.RSTR_LAYOUT_TOAST_VIEW);
			int customToastLayoutViewID = Utilities.GetFreContextResourceId(Config.RSTR_ID_TOAST_LAYOUT);
			View layout = inflater.inflate(customToastLayoutID, (ViewGroup) findViewById(customToastLayoutViewID));

			TextView text = (TextView) layout.findViewById(Utilities.GetFreContextResourceId("id.toastTextToShow"));
			// Set the Text to show in TextView
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

	public void showPopover(String textToPopover, float touchX, float touchY)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showPopover: " + textToPopover);

		try
		{
			PopoverFrameView popover = new PopoverFrameView(this, textToPopover, touchX, touchY);
			ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
																			 ViewGroup.LayoutParams.MATCH_PARENT);

			this.addContentView(popover, layoutParams);
			popoverView = popover;

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "showPopover failed with error: " + e.toString());
		}
	}

	public void showMedPopover(Medication medication, float touchX, float touchY)
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showMedPopover");

		try
		{

			LayoutInflater inflater = getLayoutInflater();
			// Inflate the Layout
			int medPopoverLayoutID = Utilities.GetFreContextResourceId(Config.RSTR_LAYOUT_MED_POPOVER_VIEW);
			int medPopoverLayoutViewID = Utilities.GetFreContextResourceId(Config.RSTR_ID_MED_POPOVER_LAYOUT);
			View layout = inflater.inflate(medPopoverLayoutID, (ViewGroup) findViewById(medPopoverLayoutViewID));

			TextView tvName = (TextView) layout.findViewById(Utilities.GetFreContextResourceId(MedPopoverFrameView.RSTR_ID_TEXT_NAME));
			tvName.setText(medication.get_name());
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   name Text=" + tvName.getText());

			TextView tvDose = (TextView) layout.findViewById(Utilities.GetFreContextResourceId(MedPopoverFrameView.RSTR_ID_TEXT_DOSE));
			tvDose.setText(medication.get_doseValue() + " " + medication.get_doseUnitText());
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   dose Text=" + tvDose.getText());

			TextView tvInstructions = (TextView) layout.findViewById(
					Utilities.GetFreContextResourceId(MedPopoverFrameView.RSTR_ID_TEXT_INSTRUCTIONS));
			tvInstructions.setText(medication.get_instructions());
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   instructions Text=" + tvInstructions.getText());

			TextView tvTime = (TextView) layout.findViewById(Utilities.GetFreContextResourceId(MedPopoverFrameView.RSTR_ID_TEXT_TIME));

			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
			timeFormat.format(medication.get_startTime());
			tvTime.setText(timeFormat.format(medication.get_startTime()) + " - " + timeFormat.format(medication.get_endTime()));
//			Utilities.LogItem(Log.DEBUG, LOCAL_TAG, "   time Text=" + tvTime.getText());

			ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
																			 ViewGroup.LayoutParams.MATCH_PARENT);

			this.addContentView(layout, layoutParams);
//			popoverView = layout;

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "showMedPopover failed with error: " + e.toString());
		}
	}

	public void hidePopover()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "hidePopover");
		try
		{
			if (popoverView != null)
			{
				ViewGroup popoverViewParent = (ViewGroup) popoverView.getParent();
				popoverViewParent.removeView(popoverView);
				popoverView = null;
			}

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "hidePopover failed with error: " + e.toString());
		}
	}

	private void showPasscodeState()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showPasscodeState");

		try
		{
			Intent intent = new Intent(this, PasscodeActivity.class);
			startActivity(intent);
		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "showPasscodeState failed with error: " + e.toString());
		}
	}

	private void showAboutState()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showAboutState");


		try {
//			setContentView(Utilities.GetFreContextResourceId("layout.view_about"));
		} catch (Exception e) {
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "showAboutState failed with error: " + e.toString());
		}
	}

	private void showClockState()
	{
		Utilities.LogItem(Log.VERBOSE, LOCAL_TAG, "showClockState");

		try
		{
//			FREContext freContext = IHAARTExtension.context;
//			setContentView(Utilities.GetFreContextResourceId(Config.RSTR_LAYOUT_CLOCK_ACTIVITY));

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "showClockState failed with error: " + e.toString());
		}
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

		MenuItem menuLogin = menu.findItem(Utilities.GetFreContextResourceId("id.menu_login_id"));
		MenuItem menuLogout = menu.findItem(Utilities.GetFreContextResourceId("id.menu_logout_id"));
		MenuItem menuChange = menu.findItem(Utilities.GetFreContextResourceId("id.menu_change_passcode_id"));

		try
		{
			//set the menu options depending on login status

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
		} else if (item.getItemId() == Utilities.GetFreContextResourceId("id.menu_logout_id"))
		{
			showLogoutState();
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

//
//	GETTERS & SETTERS
//

	public ClockFrameView getClockFrameView()
	{
		if (clockFrameView == null) {
			try
			{
				clockFrameView = (ClockFrameView) findViewById(Utilities.GetFreContextResourceId(ClockFrameView.RSTR_ID_CLOCK_FRAME_LAYOUT));
			} catch (Exception e) {
				Utilities.LogItem(Log.ERROR, LOCAL_TAG, "getClockFrameView failed with error: " + e.toString());
				return null;
			}
		}
		return clockFrameView;
	}

}
