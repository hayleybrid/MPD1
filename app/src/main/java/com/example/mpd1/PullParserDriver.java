package com.example.mpd1;
//HAYLEY_BRIDGES_S2333704

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class PullParserDriver implements Runnable {

    //initialise lists
    public static List<Standings> standingsList = new ArrayList<>();
    public static List<Driver> driverList = new ArrayList<>();
    public static List<Constructor> constructorList = new ArrayList<>();


    private String url;
    private String result;

    public static void parseData(String result) {
        //create storage object
        Standings DriverStandings = null;
        //create booleans to determine drive or constructor object
        Driver DriverInfo = null;
        Constructor ConstructorInfo = null;
//set to false
        boolean inDriver = false;
        boolean inConstructor = false;

        //pull parser
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(result));
            int eventType = xpp.getEventType();
            String currentTag = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    //start tag is used to handle nested tags within the xml
                    case XmlPullParser.START_TAG:
                        currentTag = xpp.getName();
                       // Log.d("MyTag", "START_TAG: " + currentTag);
                        if (currentTag.equalsIgnoreCase("DriverStanding")) {
                            //create storage
                            DriverStandings = new Standings();
                            //set attributes in Standings clas
                            DriverStandings.setPosition(xpp.getAttributeValue(null, "position"));
                            DriverStandings.setPoints(xpp.getAttributeValue(null, "points"));
                            DriverStandings.setWins(xpp.getAttributeValue(null, "wins"));
                           //testing purposes
                            System.out.println("Position: " + DriverStandings.getPosition());
                            System.out.println("Points: " + DriverStandings.getPoints());
                            System.out.println("Wins: " + DriverStandings.getWins());

                        } else if (currentTag.equalsIgnoreCase("Driver")) {
                            //set attributes in Driver class
                            DriverInfo = new Driver();
                            DriverInfo.setDriverId(xpp.getAttributeValue(null, "driverId"));
                            DriverInfo.setCode(xpp.getAttributeValue(null, "code"));
                            DriverInfo.setUrl(xpp.getAttributeValue(null, "url"));
                            System.out.println("Driver ID: " + DriverInfo.getDriverId());
                            System.out.println("Driver Code: " + DriverInfo.getCode());
                            System.out.println("Driver URL: " + DriverInfo.getUrl());
                            inDriver = true;

                        } else if (currentTag.equalsIgnoreCase("Constructor")) {
                            //set attributes in Constructor class
                            ConstructorInfo = new Constructor();
                            ConstructorInfo.setConstructorId(xpp.getAttributeValue(null, "constructorId"));
                            ConstructorInfo.setcUrl(xpp.getAttributeValue(null, "url"));
                            System.out.println("Constructor ID: " + ConstructorInfo.getConstructorId());
                            System.out.println("Constructor URL: " + ConstructorInfo.getcUrl());
                            //set boolean to true
                            inConstructor = true;
                        }
                        break;

                        //text functionality to parse in nested tags
                    case XmlPullParser.TEXT:
                        // trim text to remove leading/trailing whitespace
                        String text = xpp.getText().trim();
                        if (currentTag != null && !text.isEmpty()) {
                            switch (currentTag.toLowerCase()) {
                                case "permanentnumber":
                                    if (DriverInfo != null) DriverInfo.setPermanentNumber(text);
                                    System.out.println("Permanent Number: " + DriverInfo.getPermanentNumber());
                                    break;
                                case "givenname":
                                    if (DriverInfo != null) DriverInfo.setfName(text);
                                    System.out.println("Given Name: " + DriverInfo.getfName());
                                    break;
                                case "familyname":
                                    if (DriverInfo != null) DriverInfo.setsName(text);
                                    System.out.println("Family Name: " + DriverInfo.getsName());
                                    break;
                                case "dateofbirth":
                                    if (DriverInfo != null) DriverInfo.setDob(text);
                                    System.out.println("DOB: " + DriverInfo.getDob());
                                    break;
                                case "name":
                                    if (ConstructorInfo != null) ConstructorInfo.setCname(text);
                                    System.out.println("Constructor Name: " + ConstructorInfo.getCname());
                                    break;
                                    //determine nationality of driver or constructor
                                case "nationality":
                                    if (inDriver && DriverInfo != null) {
                                        DriverInfo.setNationality(text);
                                        System.out.println("Driver Nationality: " + DriverInfo.getNationality());
                                    } else if (inConstructor && ConstructorInfo != null) {
                                        ConstructorInfo.setcNationality(text);
                                        System.out.println("Constructor Nationality: " + ConstructorInfo.getcNationality());
                                    }
                            }
                        }
                        break;
                    //end tags and store lists
                    case XmlPullParser.END_TAG:
                        currentTag = xpp.getName();
                     //   Log.d("MyTag", "END_TAG: " + currentTag);
                        if (currentTag.equalsIgnoreCase("DriverStanding") && DriverStandings != null) {
                            standingsList.add(DriverStandings);
                            DriverStandings = null;
                        } else if (currentTag.equalsIgnoreCase("Driver") && DriverInfo != null) {
                            driverList.add(DriverInfo);
                            DriverInfo = null;
                            inDriver = false;
                        } else if (currentTag.equalsIgnoreCase("Constructor") && ConstructorInfo != null) {
                            constructorList.add(ConstructorInfo);
                            ConstructorInfo = null;
                            inConstructor = false;
                        }
                        break;
                }
                //iterate through list
                eventType = xpp.next();
            }
        } catch (XmlPullParserException ae1) {
            Log.e("MyTag", "XmlPullParserException: " + ae1.toString());
        } catch (IOException ae1) {
            Log.e("MyTag", "IOException during parsing");
        }
        Log.d("MyTag", "End of document reached");
    }




    @Override
    public void run() {

    }
}




