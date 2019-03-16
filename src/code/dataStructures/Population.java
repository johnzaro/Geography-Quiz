package code.dataStructures;

import static code.core.GlobalVariables.*;

public class Population
{
	private int population;
	private float populationDensityPerSquareKilometer;
	private float populationDensityPerSquareMile;
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

	public float getPopulationDensityPerSquareKilometer()
	{
		return populationDensityPerSquareKilometer;
	}

	public void setPopulationDensityPerSquareKilometer(float populationDensityPerSquareKilometer)
	{
		this.populationDensityPerSquareKilometer = populationDensityPerSquareKilometer;
	}

	public float getPopulationDensityPerSquareMile()
	{
		return populationDensityPerSquareMile;
	}

	public void setPopulationDensityPerSquareMile(float populationDensityPerSquareMile)
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
