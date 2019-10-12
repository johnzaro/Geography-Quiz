package com.johnzaro.geographyquiz.dataStructures;

import java.time.LocalDate;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

public class StateOfUSA
{
	public static final int NUMBER_OF_USA_STATES = 50;
	
//	------- GREEK NAME -------
	private String   nameInGreek;
	private String   nameInEnglish;
	private String   articleForState;
	private String   capitalName;
	private String   articleForCapital;
	private String   abbreviation;
	private String[] largestCities;
	private String   spokenLanguages;
	private String   timeZones;
	private int    numberOfCounties;
	private int     houseSeats;
	
	private LocalDate dateEnteredUnion;
	
	private int population;
	private int populationRank;
	private double populationDensityPerSquareKilometer;
	private double populationDensityPerSquareMile;

	private int totalAreaInKilometers;
	private int totalAreaInMiles;
	private int areaRanking;
	private int landAreaInKilometers;
	private int landAreaInMiles;
	private double landPercent;
	private int waterAreaInKilometers;
	private int waterAreaInMiles;
	private double waterPercent;
	private int coastlineLengthInKilometers;
	private int coastlineLengthInMiles;
	private boolean hasAccessToTheOcean;
	private String highestPoint;
	private String meanPoint;
	private String lowestPoint;
	
	private int positionInCapitals;
	
	public String getArticleForState()
	{
		return articleForState;
	}
	
	public void setArticleForState(String articleForState)
	{
		this.articleForState = articleForState;
	}
	
	public String getArticleForCapital()
	{
		return articleForCapital;
	}
	
	public void setArticleForCapital(String articleForCapital)
	{
		this.articleForCapital = articleForCapital;
	}
	
	public int getPositionInCapitals()
	{
		return positionInCapitals;
	}
	
	public void setPositionInCapitals(int positionInCapitals)
	{
		this.positionInCapitals = positionInCapitals;
	}
	
	public String getAbbreviation()
	{
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation)
	{
		this.abbreviation = abbreviation;
	}
	
	public String getCoastlineBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(coastlineLengthInKilometers) + "km";
		else return numberFormatForUI.format(coastlineLengthInMiles) + "mi";
	}
	
	public String getWaterAreaBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(waterAreaInKilometers) + "km\u00B2";
		else return numberFormatForUI.format(waterAreaInMiles) + " sq mi";
	}
	
	public String getLandAreaBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(landAreaInKilometers) + "km\u00B2";
		else return numberFormatForUI.format(landAreaInMiles) + " sq mi";
	}
	
	public String getTotalAreaBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(totalAreaInKilometers) + "km\u00B2";
		else return numberFormatForUI.format(totalAreaInMiles) + " sq mi";
	}
	
	public int getAreaRanking()
	{
		return areaRanking;
	}

	public void setAreaRanking(int areaRanking)
	{
		this.areaRanking = areaRanking;
	}

	public String getCapitalName()
	{
		return capitalName;
	}

	public void setCapitalName(String capitalName)
	{
		this.capitalName = capitalName;
	}

	public int getCoastlineLengthInKilometers()
	{
		return coastlineLengthInKilometers;
	}

	public void setCoastlineLengthInKilometers(int coastlineLengthInKilometers)
	{
		this.coastlineLengthInKilometers = coastlineLengthInKilometers;
	}

	public int getCoastlineLengthInMiles()
	{
		return coastlineLengthInMiles;
	}

	public void setCoastlineLengthInMiles(int coastlineLengthInMiles)
	{
		this.coastlineLengthInMiles = coastlineLengthInMiles;
	}

	public LocalDate getDateEnteredUnion()
	{
		return dateEnteredUnion;
	}

	public void setDateEnteredUnion(LocalDate dateEnteredUnion)
	{
		this.dateEnteredUnion = dateEnteredUnion;
	}

	public double getPopulationDensityPerSquareKilometer()
	{
		return populationDensityPerSquareKilometer;
	}

	public void setPopulationDensityPerSquareKilometer(double densityPerSquareKilometer)
	{
		this.populationDensityPerSquareKilometer = densityPerSquareKilometer;
	}

	public double getPopulationDensityPerSquareMile()
	{
		return populationDensityPerSquareMile;
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
	
	public void setPopulationDensityPerSquareMile(double populationDensityPerSquareMile)
	{
		this.populationDensityPerSquareMile = populationDensityPerSquareMile;
	}

	public boolean hasAccessToTheOcean()
	{
		return hasAccessToTheOcean;
	}

	public void setHasAccessToTheOcean(boolean hasAccessToTheOcean)
	{
		this.hasAccessToTheOcean = hasAccessToTheOcean;
	}

	public String getHighestPoint()
	{
		return highestPoint;
	}

	public void setHighestPoint(String highestPoint)
	{
		this.highestPoint = highestPoint;
	}

	public int getHouseSeats()
	{
		return houseSeats;
	}

	public void setHouseSeats(int houseSeats)
	{
		this.houseSeats = houseSeats;
	}

	public int getLandAreaInKilometers()
	{
		return landAreaInKilometers;
	}

	public void setLandAreaInKilometers(int landAreaInKilometers)
	{
		this.landAreaInKilometers = landAreaInKilometers;
	}

	public int getLandAreaInMiles()
	{
		return landAreaInMiles;
	}

	public void setLandAreaInMiles(int landAreaInMiles)
	{
		this.landAreaInMiles = landAreaInMiles;
	}

	public double getLandPercent()
	{
		return landPercent;
	}

	public void setLandPercent(double landPercent)
	{
		this.landPercent = landPercent;
	}

	public String[] getLargestCities()
	{
		return largestCities;
	}

	public void setLargestCities(String[] largestCities)
	{
		this.largestCities = largestCities;
	}

	public String getLowestPoint()
	{
		return lowestPoint;
	}

	public void setLowestPoint(String lowestPoint)
	{
		this.lowestPoint = lowestPoint;
	}

	public String getMeanPoint()
	{
		return meanPoint;
	}

	public void setMeanPoint(String meanPoint)
	{
		this.meanPoint = meanPoint;
	}

	public int getNumberOfCounties()
	{
		return numberOfCounties;
	}

	public void setNumberOfCounties(int numberOfCounties)
	{
		this.numberOfCounties = numberOfCounties;
	}

	public int getPopulation()
	{
		return population;
	}

	public void setPopulation(int population)
	{
		this.population = population;
	}

	public int getPopulationRank()
	{
		return populationRank;
	}

	public void setPopulationRank(int populationRank)
	{
		this.populationRank = populationRank;
	}

	public String getSpokenLanguages()
	{
		return spokenLanguages;
	}

	public void setSpokenLanguages(String spokenLanguages)
	{
		this.spokenLanguages = spokenLanguages;
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

	public String getTimeZones()
	{
		return timeZones;
	}

	public void setTimeZones(String timeZones)
	{
		this.timeZones = timeZones;
	}

	public int getTotalAreaInKilometers()
	{
		return totalAreaInKilometers;
	}

	public void setTotalAreaInKilometers(int totalAreaInKilometers)
	{
		this.totalAreaInKilometers = totalAreaInKilometers;
	}

	public int getTotalAreaInMiles()
	{
		return totalAreaInMiles;
	}

	public void setTotalAreaInMiles(int totalAreaInMiles)
	{
		this.totalAreaInMiles = totalAreaInMiles;
	}

	public int getWaterAreaInKilometers()
	{
		return waterAreaInKilometers;
	}

	public void setWaterAreaInKilometers(int waterAreaInKilometers)
	{
		this.waterAreaInKilometers = waterAreaInKilometers;
	}

	public int getWaterAreaInMiles()
	{
		return waterAreaInMiles;
	}

	public void setWaterAreaInMiles(int waterAreaInMiles)
	{
		this.waterAreaInMiles = waterAreaInMiles;
	}

	public double getWaterPercent()
	{
		return waterPercent;
	}

	public void setWaterPercent(double waterPercent)
	{
		this.waterPercent = waterPercent;
	}
}
