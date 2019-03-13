package code.dataStructures;

import static code.core.GlobalVariables.METRIC_SYSTEM;
import static code.core.GlobalVariables.getCurrentPlayer;
import static code.core.GlobalVariables.numberFormatForUI;

/**
 * Created by John on 25/6/2016.
 */
public class GreekRegionalUnit
{
    private String nameInGreek;
    private String nameInEnglish;
    private String capital;
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
    private String region;
    private String[] municipalities;

    private int numberOfMunicipalities;
    
    public String getAreaBasedOnLocaleToString()
    {
        if(getCurrentPlayer().getUnitSystem() == METRIC_SYSTEM) return numberFormatForUI.format(areaInKilometers) + "km\u00B2";
        else return numberFormatForUI.format(areaInMiles) + " sq mi";
    }
    
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

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLargestCity() {
        return largestCity;
    }

    public void setLargestCity(String largestCity) {
        this.largestCity = largestCity;
    }

    public String getLargestMunicipality() {
        return largestMunicipality;
    }

    public void setLargestMunicipality(String largestMunicipality) {
        this.largestMunicipality = largestMunicipality;
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

    public int getAreaRanking() {
        return areaRanking;
    }

    public void setAreaRanking(int areaRanking) {
        this.areaRanking = areaRanking;
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

    public int getPopulationRanking() {
        return populationRanking;
    }

    public void setPopulationRanking(int populationRanking) {
        this.populationRanking = populationRanking;
    }

    public String getHighestPoint() {
        return highestPoint;
    }

    public void setHighestPoint(String highestPoint) {
        this.highestPoint = highestPoint;
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

    public String[] getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(String[] municipalities) {
        this.municipalities = municipalities;
    }

    public int getNumberOfMunicipalities() {
        return numberOfMunicipalities;
    }

    public void setNumberOfMunicipalities(int numberOfMunicipalities) {
        this.numberOfMunicipalities = numberOfMunicipalities;
    }
}
