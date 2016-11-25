package com.example.shridhar.distributorapp;

import com.example.shridhar.distributorapp.CreateEvent.Event;
import com.example.shridhar.distributorapp.FavouritePlaces.PlaceInfo;
import com.example.shridhar.distributorapp.NewContactDetails.Contact;

import java.util.ArrayList;

/**
 * Created by Shridhar on 11/12/16.
 */

public class MarriageInfo {
    public String brideFirstName;
    public String brideLastName;
    public String groomFirstName;
    public String groomLastName;

    public String brideProfilePic;
    public String groomProfilePic;

    public String marriageDate;
    public String marriageTime;

    public String marriageAddress;

    public ArrayList<Event> events;

    public ArrayList<Contact> brideContactDetails;
    public ArrayList<Contact> groomContactDetails;

    public ArrayList<String> adminEmailIds;

    public ArrayList<PlaceInfo> nearestPlaces;
}
