package com.example.androidtestjava.model;

public class UserInfo {
    String name;
    String dateOfBirth;
    String countryName;
    String cityName;
    String languageSkill;
    String resumePath;

    public UserInfo(String name, String dateOfBirth, String countryName, String cityName, String languageSkill, String resumePath) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.countryName = countryName;
        this.cityName = cityName;
        this.languageSkill = languageSkill;
        this.resumePath = resumePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLanguageSkill() {
        return languageSkill;
    }

    public void setLanguageSkill(String languageSkill) {
        this.languageSkill = languageSkill;
    }

    public String getResumePath() {
        return resumePath;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", countryName='" + countryName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", languageSkill='" + languageSkill + '\'' +
                ", resumePath='" + resumePath + '\'' +
                '}';
    }
}
