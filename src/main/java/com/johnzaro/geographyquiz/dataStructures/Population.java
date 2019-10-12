package com.johnzaro.geographyquiz.dataStructures;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

public class Population
{
	private int population;
	private double populationDensityPerSquareKilometer;
	private double populationDensityPerSquareMile;
	private int globalRanking;

	public int getGlobalRanking()
	{
		return globalRanking;
	}

	public void setGlobalRanking(int globalRanking)
	{
		this.globalRanking = globalRanking;
	}

	public int getPopulation()
	{
		return population;
	}

	public void setPopulation(int population)
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
}
