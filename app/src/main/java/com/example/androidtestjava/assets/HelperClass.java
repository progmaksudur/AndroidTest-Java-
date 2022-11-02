package com.example.androidtestjava.assets;


import com.example.androidtestjava.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class HelperClass {

    public String[] countryName= {"Bangladesh","Pakistan","India","USA","UK",};
    String[] citiesBangladesh={"Dhaka","Chattogram","Sylhet","Khulna","Rajshahi","Rangpur","Gazipur","Narayanganj","Barishal","Cumilla"};
    String[] citiesPakistan={"Islamabad","karachi","Shialkot","Kashmir","Lahore"};
    String[] citiesIndia={"Delhi","Mumbai","Bangalore","Hyderabad","Chennai","Kolkata","Raipur","Chandigarh"};
    String[] citiesUSA={"New York","California","Texas","Florida","Washington","Georgia","Michigan","Ohio"};
    String[] citiesUK={"Bangor","Birmingham","Cambridge","Glasgow","Liverpool","London","Manchester","Nottingham"};



   public  String[] CheckCity(String country){
        if(country.equals("Bangladesh")){
            return citiesBangladesh;
        }
        else if(country.equals("Pakistan")){
            return citiesPakistan;
        }
        else if(country.equals("India")){
            return  citiesIndia;
        }
        else if(country.equals("USA")){
            return citiesUSA;
        }
        else {
            return citiesUK;
        }
    }




}
