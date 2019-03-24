package com.johnzaro.geographyquiz.dataStructures;

public class Currency
{
	private String name;

	private String symbol;
	private String ISOCode;

	private String subdivision;
	private String numberOfSubDivisions;

	public String getISOCode()
	{
		return ISOCode;
	}

	public void setISOCode(String ISOCode)
	{
		this.ISOCode = ISOCode;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNumberOfSubDivisions()
	{
		return numberOfSubDivisions;
	}

	public void setNumberOfSubDivisions(String numberOfSubDivisions)
	{
		this.numberOfSubDivisions = numberOfSubDivisions;
	}

	public String getSubdivision()
	{
		return subdivision;
	}

	public void setSubdivision(String subdivision)
	{
		this.subdivision = subdivision;
	}

	public String getSymbol()
	{
		return symbol;
	}

	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}
}
