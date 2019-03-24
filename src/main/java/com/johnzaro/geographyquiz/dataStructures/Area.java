package com.johnzaro.geographyquiz.dataStructures;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

public class Area
{
	private float areaInKilometers;
	private float areaInMiles;

	private int globalRanking;

	private float percentOfWater;

	private int coastlineInKilometers;
	private int coastlineInMiles;

	private int bordersInKilometers;
	private int bordersInMiles;
	
	public String getCoastlineBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(coastlineInKilometers) + "km";
		else return numberFormatForUI.format(coastlineInMiles) + "mi";
	}
	
	public String getBordersBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(bordersInKilometers) + "km";
		else return numberFormatForUI.format(bordersInMiles) + "mi";
	}
	
	public String getAreaBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(areaInKilometers) + "km\u00B2";
		else return numberFormatForUI.format(areaInMiles) + " sq mi";
	}

	public float getAreaInKilometers()
	{
		return areaInKilometers;
	}

	public void setAreaInKilometers(float areaInKilometers)
	{
		this.areaInKilometers = areaInKilometers;
	}

	public float getAreaInMiles()
	{
		return areaInMiles;
	}

	public void setAreaInMiles(float areaInMiles)
	{
		this.areaInMiles = areaInMiles;
	}

	public int getGlobalRanking()
	{
		return globalRanking;
	}

	public void setGlobalRanking(int globalRanking)
	{
		this.globalRanking = globalRanking;
	}

	public float getPercentOfWater()
	{
		return percentOfWater;
	}

	public void setPercentOfWater(float percentOfWater)
	{
		this.percentOfWater = percentOfWater;
	}

	public int getBordersInKilometers()
	{
		return bordersInKilometers;
	}

	public void setBordersInKilometers(int bordersInKilometers)
	{
		this.bordersInKilometers = bordersInKilometers;
	}

	public int getBordersInMiles()
	{
		return bordersInMiles;
	}

	public void setBordersInMiles(int bordersInMiles)
	{
		this.bordersInMiles = bordersInMiles;
	}

	public int getCoastlineInKilometers()
	{
		return coastlineInKilometers;
	}

	public void setCoastlineInKilometers(int coastlineInKilometers)
	{
		this.coastlineInKilometers = coastlineInKilometers;
	}

	public int getCoastlineInMiles()
	{
		return coastlineInMiles;
	}

	public void setCoastlineInMiles(int coastlineInMiles)
	{
		this.coastlineInMiles = coastlineInMiles;
	}
}
