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

public class MedicationAdministration
{
	private static final String LOCAL_TAG = "MedicationAdministration";

	private String _name;
	private String _nameAbbrevAttr;
	private String _nameTypeAttr;
	private String _nameValueAttr;
	private String _reportedBy;
	private Date _dateReported;
	private Date _dateAdministered;
	private Integer _amountAdministered_Value;
	private String _amountAdministered_Unit;
	private String _amountAdministered_UnitTypeAttr;
	private String _amountAdministered_UnitValueAttr;
	private String _amountAdministered_UnitAbbrevAttr;


	public MedicationAdministration(String administrationJSON)
	{
		try
		{
			JSONObject administrationObject = new JSONObject(administrationJSON);
			_name = administrationObject.getString("name");
			_nameAbbrevAttr = administrationObject.getString("nameAbbrevAttr");
			_nameTypeAttr = administrationObject.getString("nameTypeAttr");
			_nameValueAttr = administrationObject.getString("nameValueAttr");
			_reportedBy = administrationObject.getString("reportedBy");
			_dateReported = Utilities.GetDateFromString(administrationObject.getString("dateReported"));
			_dateAdministered = Utilities.GetDateFromString(administrationObject.getString("_dateAdministered"));
			_amountAdministered_Value = administrationObject.getInt("_amountAdministered_Value");
			_amountAdministered_Unit = administrationObject.getString("_amountAdministered_Unit");
			_amountAdministered_UnitTypeAttr = administrationObject.getString("_amountAdministered_UnitTypeAttr");
			_amountAdministered_UnitValueAttr = administrationObject.getString("_amountAdministered_UnitValueAttr");
			_amountAdministered_UnitAbbrevAttr = administrationObject.getString("_amountAdministered_UnitAbbrevAttr");

		} catch (Exception e)
		{
			Utilities.LogItem(Log.ERROR, LOCAL_TAG, "administrationFromJSON failed with error: " + e.toString());
		}
	}

	public String get_nameAbbrevAttr()
	{
		return _nameAbbrevAttr;
	}

	public void set_nameAbbrevAttr(String _nameAbbrevAttr)
	{
		this._nameAbbrevAttr = _nameAbbrevAttr;
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

	public String get_reportedBy()
	{
		return _reportedBy;
	}

	public void set_reportedBy(String _reportedBy)
	{
		this._reportedBy = _reportedBy;
	}

	public String get_amountAdministered_Unit()
	{
		return _amountAdministered_Unit;
	}

	public void set_amountAdministered_Unit(String _amountAdministered_Unit)
	{
		this._amountAdministered_Unit = _amountAdministered_Unit;
	}

	public String get_amountAdministered_UnitAbbrevAttr()
	{
		return _amountAdministered_UnitAbbrevAttr;
	}

	public void set_amountAdministered_UnitAbbrevAttr(String _amountAdministered_UnitAbbrevAttr)
	{
		this._amountAdministered_UnitAbbrevAttr = _amountAdministered_UnitAbbrevAttr;
	}

	public String get_amountAdministered_UnitTypeAttr()
	{
		return _amountAdministered_UnitTypeAttr;
	}

	public void set_amountAdministered_UnitTypeAttr(String _amountAdministered_UnitTypeAttr)
	{
		this._amountAdministered_UnitTypeAttr = _amountAdministered_UnitTypeAttr;
	}

	public String get_amountAdministered_UnitValueAttr()
	{
		return _amountAdministered_UnitValueAttr;
	}

	public void set_amountAdministered_UnitValueAttr(String _amountAdministered_UnitValueAttr)
	{
		this._amountAdministered_UnitValueAttr = _amountAdministered_UnitValueAttr;
	}

	public Integer get_amountAdministered_Value()
	{
		return _amountAdministered_Value;
	}

	public void set_amountAdministered_Value(Integer _amountAdministered_Value)
	{
		this._amountAdministered_Value = _amountAdministered_Value;
	}

	public Date get_dateAdministered()
	{
		return _dateAdministered;
	}

	public void set_dateAdministered(Date _dateAdministered)
	{
		this._dateAdministered = _dateAdministered;
	}
}
