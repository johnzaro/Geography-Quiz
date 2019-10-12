package com.johnzaro.geographyquiz.dataStructures;

import com.johnzaro.geographyquiz.core.GlobalVariables;

import static com.johnzaro.geographyquiz.core.GlobalVariables.getCurrentPlayer;
import static com.johnzaro.geographyquiz.core.GlobalVariables.numberFormatForUI;

/**
 * Created by John on 25/6/2016.
 */

public class GreekDecentralizedAdministration
{
	public static final int NUMBER_OF_GREEK_DECENTRALIZED_ADMINISTRATIONS = 7;
	
	private String nameInGreek;
	private String nameInEnglish;
	private String headquarters;
	private String yearFormed;

	private int areaInKilometers;
	private int areaInMiles;

	private int population;

	private String website;
	private String wikipediaLink;

	private int numberOfRegions;
	private int numberOfRegionalUnits;
	private int numberOfMunicipalities;

	private String[] regions;
	private String[] regionalUnits;
	private String[] municipalities;
	
	public String getAreaBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == GlobalVariables.UNIT_SYSTEM.METRIC) return numberFormatForUI.format(areaInKilometers) + "km\u00B2";
		else return numberFormatForUI.format(areaInMiles) + " sq mi";
	}

	public int getAreaInKilometers()
	{
		return areaInKilometers;
	}

	public void setAreaInKilometers(int areaInKilometers)
	{
		this.areaInKilometers = areaInKilometers;
	}

	public int getAreaInMiles()
	{
		return areaInMiles;
	}

	public void setAreaInMiles(int areaInMiles)
	{
		this.areaInMiles = areaInMiles;
	}

	public String getHeadquarters()
	{
		return headquarters;
	}

	public void setHeadquarters(String headquarters)
	{
		this.headquarters = headquarters;
	}

	public String[] getMunicipalities()
	{
		return municipalities;
	}

	public void setMunicipalities(String[] municipalities)
	{
		this.municipalities = municipalities;
	}

	public String getNameInEnglish()
	{
		return nameInEnglish;
	}

	public void setNameInEnglish(String nameInEnglish)
	{
		this.nameInEnglish = nameInEnglish;
	}

	public String getNameInGreek()
	{
		return nameInGreek;
	}

	public void setNameInGreek(String nameInGreek)
	{
		this.nameInGreek = nameInGreek;
	}

	public int getNumberOfMunicipalities()
	{
		return numberOfMunicipalities;
	}

	public void setNumberOfMunicipalities(int numberOfMunicipalities)
	{
		this.numberOfMunicipalities = numberOfMunicipalities;
	}

	public int getNumberOfRegionalUnits()
	{
		return numberOfRegionalUnits;
	}

	public void setNumberOfRegionalUnits(int numberOfRegionalUnits)
	{
		this.numberOfRegionalUnits = numberOfRegionalUnits;
	}

	public int getNumberOfRegions()
	{
		return numberOfRegions;
	}

	public void setNumberOfRegions(int numberOfRegions)
	{
		this.numberOfRegions = numberOfRegions;
	}

	public int getPopulation()
	{
		return population;
	}

	public void setPopulation(int population)
	{
		this.population = population;
	}

	public String[] getRegionalUnits()
	{
		return regionalUnits;
	}

	public void setRegionalUnits(String[] regionalUnits)
	{
		this.regionalUnits = regionalUnits;
	}

	public String[] getRegions()
	{
		return regions;
	}

	public void setRegions(String[] regions)
	{
		this.regions = regions;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		this.website = website;
	}

	public String getWikipediaLink()
	{
		return wikipediaLink;
	}

	public void setWikipediaLink(String wikipediaLink)
	{
		this.wikipediaLink = wikipediaLink;
	}

	public String getYearFormed()
	{
		return yearFormed;
	}

	public void setYearFormed(String yearFormed)
	{
		this.yearFormed = yearFormed;
	}
}
