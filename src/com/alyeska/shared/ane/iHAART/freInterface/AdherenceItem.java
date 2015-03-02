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

import android.util.Log;
import org.json.JSONObject;

import java.util.Date;

public class AdherenceItem
{
	private static final String LOCAL_TAG = "AdherenceItem";

	private String _name;
	private String _nameTypeAttr;
	private String _nameValueAttr;
	private String _reportedBy;
	private Date _dateReported;
	private Integer _recurrenceIndex;
	private String _adherence;

	public AdherenceItem(String adherenceJSON)
	{
		try
		{
			JSONObject adherenceObject = new JSONObject(adherenceJSON);
			_name = adherenceObject.getString("name");
			_nameTypeAttr = adherenceObject.getString("nameTypeAttr");
			_nameValueAttr = adherenceObject.getString("nameValueAttr");
			_reportedBy = adherenceObject.getString("reportedBy");
			_dateReported = Utilities.GetDateFromString(adherenceObject.getString("dateReported"));
			_recurrenceIndex = adherenceObject.getInt("recurrenceIndex");
			_adherence = adherenceObject.getString("adherence");

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "adherenceFromJSON failed with error: " + e.toString());
		}
	}

	public String get_adherence()
	{
		return _adherence;
	}

	public void set_adherence(String _adherence)
	{
		this._adherence = _adherence;
	}

	public Date get_dateReported()
	{
		return _dateReported;
	}

	public void set_dateReported(Date _dateReported)
	{
		this._dateReported = _dateReported;
	}

	public String get_name()
	{
		return _name;
	}

	public void set_name(String _name)
	{
		this._name = _name;
	}

	public String get_nameTypeAttr()
	{
		return _nameTypeAttr;
	}

	public void set_nameTypeAttr(String _nameTypeAttr)
	{
		this._nameTypeAttr = _nameTypeAttr;
	}

	public String get_nameValueAttr()
	{
		return _nameValueAttr;
	}

	public void set_nameValueAttr(String _nameValueAttr)
	{
		this._nameValueAttr = _nameValueAttr;
	}

	public Integer get_recurrenceIndex()
	{
		return _recurrenceIndex;
	}

	public void set_recurrenceIndex(Integer _recurrenceIndex)
	{
		this._recurrenceIndex = _recurrenceIndex;
	}

	public String get_reportedBy()
	{
		return _reportedBy;
	}

	public void set_reportedBy(String _reportedBy)
	{
		this._reportedBy = _reportedBy;
	}
}
