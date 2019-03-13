package code.dataStructures;

import static code.core.GlobalVariables.METRIC_SYSTEM;
import static code.core.GlobalVariables.getCurrentPlayer;
import static code.core.GlobalVariables.numberFormatForUI;

/**
 * Created by John on 25/6/2016.
 */
public class GreekRegion
{
	private String nameInGreek;
	private String nameInEnglish;
	private String seat;
	private String largestCity;
	private String largestMunicipality;

	private int areaInKilometers;
	private int areaInMiles;
	private int areaRanking;

	private int population;
	private int populationDensityPerSquareKilometer;
	private int populationDensityPerSquareMile;
	private int populationRanking;

	private String highestPoint;

	private String website;
	private String wikipediaLink;

	private String decentralizedAdministration;
	private String[] regionalUnits;
	private String[] municipalities;

	private int numberOfRegionalUnits;
	private int numberOfMunicipalities;
	
	public String getAreaBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == METRIC_SYSTEM) return numberFormatForUI.format(areaInKilometers) + "km\u00B2";
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

	public int getAreaRanking()
	{
		return areaRanking;
	}

	public void setAreaRanking(int areaRanking)
	{
		this.areaRanking = areaRanking;
	}

	public String getDecentralizedAdministration()
	{
		return decentralizedAdministration;
	}

	public void setDecentralizedAdministration(String decentralizedAdministration)
	{
		this.decentralizedAdministration = decentralizedAdministration;
	}

	public String getHighestPoint()
	{
		return highestPoint;
	}

	public void setHighestPoint(String highestPoint)
	{
		this.highestPoint = highestPoint;
	}

	public String getLargestCity()
	{
		return largestCity;
	}

	public void setLargestCity(String largestCity)
	{
		this.largestCity = largestCity;
	}

	public String getLargestMunicipality()
	{
		return largestMunicipality;
	}

	public void setLargestMunicipality(String largestMunicipality)
	{
		this.largestMunicipality = largestMunicipality;
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

	public int getPopulation()
	{
		return population;
	}

	public void setPopulation(int population)
	{
		this.population = population;
	}

	public int getPopulationDensityPerSquareKilometer()
	{
		return populationDensityPerSquareKilometer;
	}

	public void setPopulationDensityPerSquareKilometer(int populationDensityPerSquareKilometer)
	{
		this.populationDensityPerSquareKilometer = populationDensityPerSquareKilometer;
	}

	public int getPopulationDensityPerSquareMile()
	{
		return populationDensityPerSquareMile;
	}

	public void setPopulationDensityPerSquareMile(int populationDensityPerSquareMile)
	{
		this.populationDensityPerSquareMile = populationDensityPerSquareMile;
	}

	public int getPopulationRanking()
	{
		return populationRanking;
	}

	public void setPopulationRanking(int populationRanking)
	{
		this.populationRanking = populationRanking;
	}

	public String[] getRegionalUnits()
	{
		return regionalUnits;
	}

	public void setRegionalUnits(String[] regionalUnits)
	{
		this.regionalUnits = regionalUnits;
	}

	public String getSeat()
	{
		return seat;
	}

	public void setSeat(String seat)
	{
		this.seat = seat;
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
}
