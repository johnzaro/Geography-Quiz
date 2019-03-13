package code.dataStructures;

import java.time.LocalDate;

import static code.core.GlobalVariables.*;
import static code.core.GlobalVariables.LANGUAGE_GREEK;

public class StateOfUSA
{
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
	private short    numberOfCounties;
	private byte     houseSeats;
	
	private LocalDate dateEnteredUnion;
	
	private int population;
	private byte populationRank;
	private float populationDensityPerSquareKilometer;
	private float populationDensityPerSquareMile;

	private int totalAreaInKilometers;
	private int totalAreaInMiles;
	private byte areaRanking;
	private int landAreaInKilometers;
	private int landAreaInMiles;
	private float landPercent;
	private int waterAreaInKilometers;
	private int waterAreaInMiles;
	private float waterPercent;
	private int coastlineLengthInKilometers;
	private int coastlineLengthInMiles;
	private boolean hasAccessToTheOcean;
	private String highestPoint;
	private String meanPoint;
	private String lowestPoint;
	
	private short positionInCapitals;
	
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
	
	public short getPositionInCapitals()
	{
		return positionInCapitals;
	}
	
	public void setPositionInCapitals(short positionInCapitals)
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
		if(getCurrentPlayer().getUnitSystem() == METRIC_SYSTEM) return numberFormatForUI.format(coastlineLengthInKilometers) + "km";
		else return numberFormatForUI.format(coastlineLengthInMiles) + "mi";
	}
	
	public String getWaterAreaBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == METRIC_SYSTEM) return numberFormatForUI.format(waterAreaInKilometers) + "km\u00B2";
		else return numberFormatForUI.format(waterAreaInMiles) + " sq mi";
	}
	
	public String getLandAreaBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == METRIC_SYSTEM) return numberFormatForUI.format(landAreaInKilometers) + "km\u00B2";
		else return numberFormatForUI.format(landAreaInMiles) + " sq mi";
	}
	
	public String getTotalAreaBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == METRIC_SYSTEM) return numberFormatForUI.format(totalAreaInKilometers) + "km\u00B2";
		else return numberFormatForUI.format(totalAreaInMiles) + " sq mi";
	}
	
	public byte getAreaRanking()
	{
		return areaRanking;
	}

	public void setAreaRanking(byte areaRanking)
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

	public float getPopulationDensityPerSquareKilometer()
	{
		return populationDensityPerSquareKilometer;
	}

	public void setPopulationDensityPerSquareKilometer(float densityPerSquareKilometer)
	{
		this.populationDensityPerSquareKilometer = densityPerSquareKilometer;
	}

	public float getPopulationDensityPerSquareMile()
	{
		return populationDensityPerSquareMile;
	}
	
	public String getPopulationDensityBasedOnLocaleToString()
	{
		if(getCurrentLanguage() == LANGUAGE_GREEK)
		{
			if(getCurrentPlayer().getUnitSystem() == METRIC_SYSTEM) return numberFormatForUI.format(populationDensityPerSquareKilometer) + " κάτ./km\u00B2";
			else return numberFormatForUI.format(populationDensityPerSquareMile) + " κάτ./ sq mi";
		}
		else
		{
			if(getCurrentPlayer().getUnitSystem() == METRIC_SYSTEM) return numberFormatForUI.format(populationDensityPerSquareKilometer) + " inh./km²";
			else return numberFormatForUI.format(populationDensityPerSquareMile) + " inh./ sq mi";
		}
	}
	
	public void setPopulationDensityPerSquareMile(float populationDensityPerSquareMile)
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

	public byte getHouseSeats()
	{
		return houseSeats;
	}

	public void setHouseSeats(byte houseSeats)
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

	public float getLandPercent()
	{
		return landPercent;
	}

	public void setLandPercent(float landPercent)
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

	public short getNumberOfCounties()
	{
		return numberOfCounties;
	}

	public void setNumberOfCounties(short numberOfCounties)
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

	public byte getPopulationRank()
	{
		return populationRank;
	}

	public void setPopulationRank(byte populationRank)
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

	public float getWaterPercent()
	{
		return waterPercent;
	}

	public void setWaterPercent(float waterPercent)
	{
		this.waterPercent = waterPercent;
	}
}
