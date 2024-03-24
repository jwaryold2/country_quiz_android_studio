package edu.uga.cs.project4countryquiz;

public class Country {
    public String name;
    public String continent;

    public Country(String name, String continent) {
        this.name = name;
        this.continent = continent;
    }

    // Getters and setters for name and continent

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", continent='" + continent + '\'' +
                '}';
    }
}

