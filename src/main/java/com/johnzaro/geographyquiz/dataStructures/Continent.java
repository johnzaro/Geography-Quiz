package com.johnzaro.geographyquiz.dataStructures;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

public class Continent
{
	public final static int NUMBER_OF_CONTINENTS = 7;
	
	private String nameInGreek;
	private String nameInEnglish;
	private String genitiveCaseOfContinent;
	private String numberOfCountries;
	private String[] largestCountriesByArea;
	private String[] largestCountriesByPopulation;
	private String[] largestCities;
	private String languages;
	private String highestPoint;
	private String lowestPoint;
	private String longestRiver;
	private String largestLake;
	private String timeZones;
	private String populationString;

	private int areaInKilometers;
	private int areaInMiles;
	private int globalAreaRanking;
	private double percentOfEarth;
	private double percentOfLandOfEarth;
	private int coastlineInKilometers;
	private int coastlineInMiles;

	private long population;
	private int globalPopulationRanking;
	private double populationDensityPerSquareKilometer;
	private double populationDensityPerSquareMile;
	
	public String getGenitiveCaseOfContinent()
	{
		return genitiveCaseOfContinent;
	}
	
	public void setGenitiveCaseOfContinent(String genitiveCaseOfContinent)
	{
		this.genitiveCaseOfContinent = genitiveCaseOfContinent;
	}
	
	public String[] getLargestCountriesByArea()
	{
		return largestCountriesByArea;
	}
	
	public void setLargestCountriesByArea(String[] largestCountriesByArea)
	{
		this.largestCountriesByArea = largestCountriesByArea;
	}
	
	public String[] getLargestCountriesByPopulation()
	{
		return largestCountriesByPopulation;
	}
	
	public void setLargestCountriesByPopulation(String[] largestCountriesByPopulation)
	{
		this.largestCountriesByPopulation = largestCountriesByPopulation;
	}
	
	public String getAreaBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(areaInKilometers) + "km\u00B2";
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
	
	public String getCoastlineBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(coastlineInKilometers) + "km";
		else return numberFormatForUI.format(coastlineInMiles) + "mi";
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

	public int getGlobalAreaRanking()
	{
		return globalAreaRanking;
	}

	public void setGlobalAreaRanking(int globalAreaRanking)
	{
		this.globalAreaRanking = globalAreaRanking;
	}

	public int getGlobalPopulationRanking()
	{
		return globalPopulationRanking;
	}

	public void setGlobalPopulationRanking(int globalPopulationRanking)
	{
		this.globalPopulationRanking = globalPopulationRanking;
	}

	public String getHighestPoint()
	{
		return highestPoint;
	}

	public void setHighestPoint(String highestPoint)
	{
		this.highestPoint = highestPoint;
	}

	public String[] getLargestCities()
	{
		return largestCities;
	}

	public void setLargestCities(String[] largestCities)
	{
		this.largestCities = largestCities;
	}

	public String getLargestLake()
	{
		return largestLake;
	}

	public void setLargestLake(String largestLake)
	{
		this.largestLake = largestLake;
	}

	public String getLongestRiver()
	{
		return longestRiver;
	}

	public void setLongestRiver(String longestRiver)
	{
		this.longestRiver = longestRiver;
	}

	public String getLowestPoint()
	{
		return lowestPoint;
	}

	public void setLowestPoint(String lowestPoint)
	{
		this.lowestPoint = lowestPoint;
	}

	public String getNameInGreek()
	{
		return nameInGreek;
	}

	public void setNameInGreek(String nameInGreek)
	{
		this.nameInGreek = nameInGreek;
	}

	public String getNameInEnglish()
	{
		return nameInEnglish;
	}

	public void setNameInEnglish(String nameInEnglish)
	{
		this.nameInEnglish = nameInEnglish;
	}

	public String getNumberOfCountries()
	{
		return numberOfCountries;
	}

	public void setNumberOfCountries(String numberOfCountries)
	{
		this.numberOfCountries = numberOfCountries;
	}

	public String getLanguages()
	{
		return languages;
	}

	public void setLanguages(String languages)
	{
		this.languages = languages;
	}

	public double getPercentOfEarth()
	{
		return percentOfEarth;
	}

	public void setPercentOfEarth(double percentOfEarth)
	{
		this.percentOfEarth = percentOfEarth;
	}

	public double getPercentOfLandOfEarth()
	{
		return percentOfLandOfEarth;
	}

	public void setPercentOfLandOfEarth(double percentOfLandOfEarth)
	{
		this.percentOfLandOfEarth = percentOfLandOfEarth;
	}
	
	public String getPopulationDensityBasedOnLocaleToString()
	{
		if(getCurrentLanguage() == LANGUAGE.GREEK)
		{
			if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(populationDensityPerSquareKilometer) + " κάτ./km\u00B2";
			else return numberFormatForUI.format(populationDensityPerSquareMile) + " κάτ./ sq mi";
		}
		else
		{
			if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(populationDensityPerSquareKilometer) + " inh./km²";
			else return numberFormatForUI.format(populationDensityPerSquareMile) + " inh./ sq mi";
		}
	}
	
	public long getPopulation()
	{
		return population;
	}

	public void setPopulation(long population)
	{
		this.population = population;
	}

	public double getPopulationDensityPerSquareKilometer()
	{
		return populationDensityPerSquareKilometer;
	}

	public void setPopulationDensityPerSquareKilometer(double populationDensityPerSquareKilometer)
	{
		this.populationDensityPerSquareKilometer = populationDensityPerSquareKilometer;
	}

	public double getPopulationDensityPerSquareMile()
	{
		return populationDensityPerSquareMile;
	}

	public void setPopulationDensityPerSquareMile(double populationDensityPerSquareMile)
	{
		this.populationDensityPerSquareMile = populationDensityPerSquareMile;
	}

	public String getTimeZones()
	{
		return timeZones;
	}

	public void setTimeZones(String timeZones)
	{
		this.timeZones = timeZones;
	}

	public String getPopulationString()
	{
		return populationString;
	}

	public void setPopulationString(String populationString)
	{
		this.populationString = populationString;
	}
}
