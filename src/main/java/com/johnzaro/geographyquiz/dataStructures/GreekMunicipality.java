package com.johnzaro.geographyquiz.dataStructures;

/**
 * Created by John on 25/6/2016.
 */
public class GreekMunicipality
{
    private String nameInGreek;
    private String nameInEnglish;

    private int areaInKilometers;
    private int areaInMiles;

    private int population;
    private int populationDensityPerSquareKilometer;
    private int populationDensityPerSquareMile;

    private String website;
    private String wikipediaLink;

    private String decentralizedAdministration;
    private String region;
    private String regionalUnit;

    public String getNameInGreek() {
        return nameInGreek;
    }

    public void setNameInGreek(String nameInGreek) {
        this.nameInGreek = nameInGreek;
    }

    public String getNameInEnglish() {
        return nameInEnglish;
    }

    public void setNameInEnglish(String nameInEnglish) {
        this.nameInEnglish = nameInEnglish;
    }

    public int getAreaInKilometers() {
        return areaInKilometers;
    }

    public void setAreaInKilometers(int areaInKilometers) {
        this.areaInKilometers = areaInKilometers;
    }

    public int getAreaInMiles() {
        return areaInMiles;
    }

    public void setAreaInMiles(int areaInMiles) {
        this.areaInMiles = areaInMiles;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getPopulationDensityPerSquareKilometer() {
        return populationDensityPerSquareKilometer;
    }

    public void setPopulationDensityPerSquareKilometer(int populationDensityPerSquareKilometer) {
        this.populationDensityPerSquareKilometer = populationDensityPerSquareKilometer;
    }

    public int getPopulationDensityPerSquareMile() {
        return populationDensityPerSquareMile;
    }

    public void setPopulationDensityPerSquareMile(int populationDensityPerSquareMile) {
        this.populationDensityPerSquareMile = populationDensityPerSquareMile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWikipediaLink() {
        return wikipediaLink;
    }

    public void setWikipediaLink(String wikipediaLink) {
        this.wikipediaLink = wikipediaLink;
    }

    public String getDecentralizedAdministration() {
        return decentralizedAdministration;
    }

    public void setDecentralizedAdministration(String decentralizedAdministration) {
        this.decentralizedAdministration = decentralizedAdministration;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionalUnit() {
        return regionalUnit;
    }

    public void setRegionalUnit(String regionalUnit) {
        this.regionalUnit = regionalUnit;
    }
}
