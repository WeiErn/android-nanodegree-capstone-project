package com.udacity.findaflight.data;

public class SearchResultPlace {

    private int placeId;
    private String iataCode;
    private String name;
    private String type;
    private String skyscannerCode;
    private String cityName;
    private String cityId;
    private String countryName;

    public SearchResultPlace(int placeId, String iataCode, String name, String type, String skyscannerCode, String cityName, String cityId, String countryName) {
        this.placeId = placeId;
        this.iataCode = iataCode;
        this.name = name;
        this.type = type;
        this.skyscannerCode = skyscannerCode;
        this.cityName = cityName;
        this.cityId = cityId;
        this.countryName = countryName;
    }

    public int getPlaceId() {
        return placeId;
    }

    public String getIataCode() {
        return iataCode;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSkyscannerCode() {
        return skyscannerCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCountryName() {
        return countryName;
    }
}
