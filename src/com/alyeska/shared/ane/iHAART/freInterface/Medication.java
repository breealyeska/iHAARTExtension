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

public class Medication
{

	private static final String LOCAL_TAG = "Medication";

	private String _name;
	private String _nameAbbrevAttr;
	private String _nameTypeAttr;
	private String _nameValueAttr;
	private String _scheduledBy;
	private String _recurrenceFrequency;
	private Integer _recurrenceInterval;
	private Integer _recurrenceCount;
	private Integer _doseValue;
	private String _doseUnitText;
	private String _doseUnitTypeAttr;
	private String _doseUnitValueAttr;
	private String _doseUnitAbbrevAttr;
	private String _indication;
	private String _instructions;
	private Date _startTime;
	private Date _endTime;
	private String _imgSource;
	private String _xmlString;

	public Medication()
	{

	}

	public Medication (String medicationJSON)
	{
		try
		{
			JSONObject medObject = new JSONObject(medicationJSON);
			_name = medObject.getString("name");
			_nameAbbrevAttr = medObject.getString("nameAbbrevAttr");
			_nameTypeAttr = medObject.getString("nameTypeAttr");
			_nameValueAttr = medObject.getString("nameValueAttr");
			_scheduledBy = medObject.getString("scheduledBy");
			_recurrenceFrequency = medObject.getString("recurrenceFrequency");
			_recurrenceInterval = medObject.getInt("recurrenceInterval");
			_recurrenceCount = medObject.getInt("recurrenceCount");
			_doseValue = medObject.getInt("doseAmount");
			_doseUnitText = medObject.getString("doseUnit");
			_doseUnitTypeAttr = medObject.getString("doseUnitTypeAttr");
			_doseUnitValueAttr = medObject.getString("doseUnitValueAttr");
			_doseUnitAbbrevAttr = medObject.getString("doseUnitAbbrevAttr");
			_indication = medObject.getString("indication");
			_instructions = medObject.getString("instructions");
			_startTime = Utilities.GetDateFromString(medObject.getString("startTime"));
			_endTime = Utilities.GetDateFromString(medObject.getString("endTime"));
			_imgSource = medObject.getString("imgSource");
			_xmlString = medObject.getString("xmlString");


		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "medicationFromJSON failed with error: " + e.toString());
		}
	}

	public Integer get_doseValue()
	{
		return _doseValue;
	}

	public String get_doseUnitText()
	{
		return _doseUnitText;
	}

	public String get_doseUnitAbbrevAttr()
	{
		return _doseUnitAbbrevAttr;
	}

	public String get_doseUnitTypeAttr()
	{
		return _doseUnitTypeAttr;
	}

	public String get_doseUnitValueAttr()
	{
		return _doseUnitValueAttr;
	}

	public Date get_endTime()
	{
		return _endTime;
	}

	public String get_imgSource()
	{
		return _imgSource;
	}

	public String get_indication()
	{
		return _indication;
	}

	public String get_instructions()
	{
		return _instructions;
	}

	public String get_name()
	{
		return _name;
	}

	public Date get_startTime()
	{
		return _startTime;
	}

	public String get_xmlString()
	{
		return _xmlString;
	}

	public String get_nameAbbrevAttr()
	{
		return _nameAbbrevAttr;
	}

	public String get_nameTypeAttr()
	{
		return _nameTypeAttr;
	}

	public String get_nameValueAttr()
	{
		return _nameValueAttr;
	}

	public Integer get_recurrenceCount()
	{
		return _recurrenceCount;
	}

	public String get_recurrenceFrequency()
	{
		return _recurrenceFrequency;
	}

	public Integer get_recurrenceInterval()
	{
		return _recurrenceInterval;
	}

	public String get_scheduledBy()
	{
		return _scheduledBy;
	}
}
