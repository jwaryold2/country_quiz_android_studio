package edu.uga.cs.project4countryquiz;

/**
 * Class to store information about a country
 */
public class Country {
    public String name;
    public String continent;

    /**
     * Constructor
     * @param name the name of the country
     * @param continent the continent the country is locating in
     */
    public Country(String name, String continent) {
        this.name = name;
        this.continent = continent;
    }

    /**
     * String representation of the country, mainly for purposes of debugging
     * @return a string containing the name and continent of the country
     */
    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", continent='" + continent + '\'' +
                '}';
    }
}

