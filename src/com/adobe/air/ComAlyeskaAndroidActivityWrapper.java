package com.adobe.air;

import android.app.Activity;
//import com.adobe.air.AndroidActivityWrapper.ActivityState;

public class ComAlyeskaAndroidActivityWrapper
{
	private static ActivityState ActivityState_STARTED = ActivityState.STARTED;
	private static ActivityState ActivityState_RESTARTED = ActivityState.RESTARTED;
	private static ActivityState ActivityState_RESUMED = ActivityState.RESUMED;
	private static ActivityState ActivityState_PAUSED = ActivityState.PAUSED;
	private static ActivityState ActivityState_STOPPED = ActivityState.STOPPED;
	private static ActivityState ActivityState_DESTROYED = ActivityState.DESTROYED;

	private static AndroidActivityWrapper sAndroidActivityWrapper = null;

	private Activity m_activity = null;

	public static enum ActivityState
	{
		STARTED,
		RESTARTED,
		RESUMED,
		PAUSED,
		STOPPED,
		DESTROYED;

		private ActivityState()
		{
		}
	}

	public ComAlyeskaAndroidActivityWrapper(Activity activity)
	{
		m_activity = activity;
		sAndroidActivityWrapper = AndroidActivityWrapper.CreateAndroidActivityWrapper(m_activity, false);
	}

	public void addActivityResultListener(AndroidActivityWrapper.ActivityResultCallback activityResultCallback)
	{
		sAndroidActivityWrapper.addActivityResultListener(activityResultCallback);
	}

	public void removeActivityResultListener(AndroidActivityWrapper.ActivityResultCallback activityResultCallback)
	{
		sAndroidActivityWrapper.removeActivityResultListener(activityResultCallback);
	}

	public void addActivityStateChangeListener(AndroidActivityWrapper.StateChangeCallback stateChangeCallback)
	{
		sAndroidActivityWrapper.addActivityStateChangeListner(stateChangeCallback);
	}

	public void removeActivityStateChangeListener(AndroidActivityWrapper.StateChangeCallback stateChangeCallback)
	{
		sAndroidActivityWrapper.removeActivityStateChangeListner(stateChangeCallback);
	}

	public Activity GetActivity()
	{
		return m_activity;
	}
}