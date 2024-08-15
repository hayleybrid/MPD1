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

public class PullParserRaces {

    public static List<Races> racesList = new ArrayList<>();
    public static List<Circuit> circuitList = new ArrayList<>();

    public static void parseDataR(String result) {
        Races racesInfo = null;
        Circuit circuitInfo = null;
        boolean insideRace = false;
        boolean insideCircuit = false;
        boolean timeSet = false;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(result));
            int eventType = xpp.getEventType();
            String currentTag = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTag = xpp.getName();
                        if (currentTag.equalsIgnoreCase("Race")) {
                            racesInfo = new Races();
                            racesInfo.setRound(xpp.getAttributeValue(null, "round"));
                            insideRace = true;
                        } else if (currentTag.equalsIgnoreCase("Circuit")) {
                            circuitInfo = new Circuit();
                            circuitInfo.setCircuitId(xpp.getAttributeValue(null, "circuitId"));
                            insideCircuit = true;
                        } else if (currentTag.equalsIgnoreCase("Location")) {
                            if (circuitInfo != null) {
                                circuitInfo.setLatitude(xpp.getAttributeValue(null, "lat"));
                                circuitInfo.setLongitude(xpp.getAttributeValue(null, "long"));
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        String text = xpp.getText().trim();
                    if (currentTag != null && !text.isEmpty()) {
                        switch (currentTag.toLowerCase()) {
                            case "racename":
                                if (racesInfo != null) racesInfo.setRace(text);
                                break;
                            case "date":
                                if (racesInfo != null) racesInfo.setDate(text);
                                break;
                            case "time":
                                if ((racesInfo != null) && !timeSet) {
                                    racesInfo.setTime(text);
                                    //get first instance of time as there are times for qualifiers not needed
                                    timeSet = true;
                                }
                                break;
                            case "circuitname":
                               if (circuitInfo != null) circuitInfo.setCircuitName(text);{
                                   break;
                            }

                        }
                    }
                          break;

                    case XmlPullParser.END_TAG:
                        currentTag = xpp.getName();
                        if (currentTag.equalsIgnoreCase("Race") && racesInfo != null) {
                            racesList.add(racesInfo);
                            racesInfo = null;
                          //  insideRace = false;
                            timeSet = false;
                        } else if (currentTag.equalsIgnoreCase("Circuit") && circuitInfo != null) {
                            circuitList.add(circuitInfo);
                            circuitInfo = null;
                         //   insideCircuit = false;
                        }
                        break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            Log.e("PullParserRaces", "Error parsing data", e);
        }
    }

}

