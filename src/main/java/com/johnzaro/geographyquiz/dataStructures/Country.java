package com.johnzaro.geographyquiz.dataStructures;


public class Country
{
	private String nameInGreek, nameInEnglish, capitalName;
	private String   articleForCountry;
	private String   genitiveCaseOfCountry;
	private String   articleForCapital;
	private String   languagesString;
	private String[] languagesToAsk;
	private String[] largestCities;
	private String   continent;
	private String   sovereignState;

	private Currency currency;
	private Area area;
	private Population population;

	//OTHER VALUES
	private short   positionInCapitals;
	private byte    askCapital;
	private byte    askCurrency;
	private byte    askLargestCity;
	private byte    askLanguage;
	private byte    askGeographicalCharacteristics;
	private byte    askContinent;
	private boolean isSovereignState;
	private boolean hasEasyLocation;
	private boolean hasSea;
	private boolean isIslandCountry;
	private boolean hasEasyFlag;
	private boolean askForCoatOfArms;
	private String localeCountryCode;
	private String localeLanguageCode;
	
	public String getLocaleLanguageCode()
	{
		return localeLanguageCode;
	}
	
	public void setLocaleLanguageCode(String localeLanguageCode)
	{
		this.localeLanguageCode = localeLanguageCode;
	}
	
	public String getLocaleCountryCode()
	{
		return localeCountryCode;
	}
	
	public void setLocaleCountryCode(String localeCountryCode)
	{
		this.localeCountryCode = localeCountryCode;
	}
	
	public String getGenitiveCaseOfCountry()
	{
		return genitiveCaseOfCountry;
	}
	
	public void setGenitiveCaseOfCountry(String genitiveCaseOfCountry)
	{
		this.genitiveCaseOfCountry = genitiveCaseOfCountry;
	}
	
	public String getArticleForCapital()
	{
		return articleForCapital;
	}
	
	public void setArticleForCapital(String articleForCapital)
	{
		this.articleForCapital = articleForCapital;
	}
	
	public String getArticleForCountry()
	{
		return articleForCountry;
	}
	
	public void setArticleForCountry(String articleForCountry)
	{
		this.articleForCountry = articleForCountry;
	}
	
	public short getPositionInCapitals()
	{
		return positionInCapitals;
	}
	
	public void setPositionInCapitals(short positionInCapitals)
	{
		this.positionInCapitals = positionInCapitals;
	}
	
	public byte askLargestCity()
	{
		return askLargestCity;
	}
	
	public void setAskLargestCity(byte askLargestCity)
	{
		this.askLargestCity = askLargestCity;
	}
	
	public Area getArea()
	{
		return area;
	}

	public void setArea(Area area)
	{
		this.area = area;
	}
	
	public String getLanguagesString()
	{
		return languagesString;
	}
	
	public void setLanguagesString(String languagesString)
	{
		this.languagesString = languagesString;
	}
	
	public byte askLanguage()
	{
		return askLanguage;
	}

	public void setAskLanguage(byte askLanguage)
	{
		this.askLanguage = askLanguage;
	}
	
	public byte askGeographicalCharacteristics()
	{
		return askGeographicalCharacteristics;
	}
	
	public void setAskGeographicalCharacteristics(byte askGeographicalCharacteristics)
	{
		this.askGeographicalCharacteristics = askGeographicalCharacteristics;
	}
	
	public byte askContinent()
	{
		return askContinent;
	}
	
	public void setAskContinent(byte askContinent)
	{
		this.askContinent = askContinent;
	}
	
	public String getCapitalName()
	{
		return capitalName;
	}

	public void setCapitalName(String capitalName)
	{
		this.capitalName = capitalName;
	}

	public String getContinent()
	{
		return continent;
	}

	public void setContinent(String continent)
	{
		this.continent = continent;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public byte askCapital()
	{
		return askCapital;
	}

	public void setAskCapital(byte askCapital)
	{
		this.askCapital = askCapital;
	}

	public boolean hasSea()
	{
		return hasSea;
	}

	public void setHasSea(boolean hasSea)
	{
		this.hasSea = hasSea;
	}

	public boolean hasEasyLocation()
	{
		return hasEasyLocation;
	}

	public void setHasEasyLocation(boolean hasEasyLocation)
	{
		this.hasEasyLocation = hasEasyLocation;
	}

	public boolean isIslandCountry()
	{
		return isIslandCountry;
	}

	public void setIsIslandCountry(boolean isIslandCountry)
	{
		this.isIslandCountry = isIslandCountry;
	}

	public boolean isSovereignState()
	{
		return isSovereignState;
	}

	public void setIsSovereignState(boolean isSovereignState)
	{
		this.isSovereignState = isSovereignState;
	}

	public String[] getLargestCities()
	{
		return largestCities;
	}

	public void setLargestCities(String[] largestCities)
	{
		this.largestCities = largestCities;
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

	public String[] getLanguagesToAsk()
	{
		return languagesToAsk;
	}

	public void setLanguagesToAsk(String[] languagesToAsk)
	{
		this.languagesToAsk = languagesToAsk;
	}

	public Population getPopulation()
	{
		return population;
	}

	public void setPopulation(Population population)
	{
		this.population = population;
	}

	public String getSovereignState()
	{
		return sovereignState;
	}

	public void setSovereignState(String sovereignState)
	{
		this.sovereignState = sovereignState;
	}
	
	public byte askCurrency()
	{
		return askCurrency;
	}
	
	public void setAskCurrency(byte askCurrency)
	{
		this.askCurrency = askCurrency;
	}
	
	public boolean hasEasyFlag()
	{
		return hasEasyFlag;
	}
	
	public void setHasEasyFlag(boolean hasEasyFlag)
	{
		this.hasEasyFlag = hasEasyFlag;
	}
	
	public boolean askForCoatOfArms()
	{
		return askForCoatOfArms;
	}
	
	public void setAskForCoatOfArms(boolean askForCoatOfArms)
	{
		this.askForCoatOfArms = askForCoatOfArms;
	}
}