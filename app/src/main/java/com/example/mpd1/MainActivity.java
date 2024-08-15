package com.example.mpd1;
//HAYLEY_BRIDGES_S2333704
import static com.example.mpd1.PullParserDriver.constructorList;
import static com.example.mpd1.PullParserDriver.driverList;
import static com.example.mpd1.PullParserDriver.standingsList;
import static com.example.mpd1.PullParserRaces.circuitList;
import static com.example.mpd1.PullParserRaces.racesList;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements AdapterView.OnClickListener, AdapterView.OnItemClickListener {

    private String urlSource = "http://ergast.com/api/f1/current/driverStandings";
    private String url2Source = "https://ergast.com/api/f1/2024";
    Button btnRaces;
    ListView listView;
    String[] driverWins, driverPos, driverFName, driverPoints, driverSName, driverNationality, constructorName, driverDob,
            round, raceName, date, raceTime, circuitName, longitude, latitude;
    ImageView image1, image2, image3, image4, image5;
    private Handler handler;
    TextView textViewDateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //checkConnection();
        startProgress();

        //initialise components
        btnRaces = findViewById(R.id.BtnRace);
        btnRaces.setOnClickListener(this);
        image1 = findViewById(R.id.image1);
        image1.setImageResource(R.drawable.ranking_3537572);
        image2 = findViewById(R.id.image2);
        image2.setImageResource(R.drawable.tyre_3089000);
        image3 = findViewById(R.id.image3);
        image3.setImageResource(R.drawable.medal_8996781);
        image4 = findViewById(R.id.image4);
        image4.setImageResource(R.drawable.motorbike);
        image5 = findViewById(R.id.image5);
        image5.setImageResource(R.drawable.racingcar);
        listView = findViewById(R.id.list);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), driverPos, driverPoints, driverWins, driverFName, driverSName, driverDob, driverNationality, constructorName);
        listView.setAdapter(customAdapter);
        textViewDateTime = findViewById(R.id.textViewDateTime);
        handler = new Handler(Looper.getMainLooper());
        updateDateTime();
        textViewDateTime.setText(DateTime.getCurrentDateTime());

        //set item on click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < driverDob.length) {
                    // Print the information corresponding to the position
                    System.out.printf("%s, %s, %s%n", driverDob[position], driverNationality[position], constructorName[position]);
                    //create string to be displayed by the toast
                    String info = "Driver Date of Birth: " + driverDob[position] + ", Driver Nationality: " + driverNationality[position] + ", Constructor Driven: " + constructorName[position];
                    //toast set to long
                    Toast.makeText(getApplicationContext(), "Driver Information: " + info, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void startProgress() {
        //thread information access urlSource/url2Source
      //  Thread thread1 = new Thread(new Task(urlSource));
        //Thread thread2 = new Thread(new Task(url2Source));

        //ergast api was temperamental, xml saved in assets folder
        //used for pullparserdriver
        Thread thread1 = new Thread(new Task("f1_current_driverStandings.xml"));
        //used for pullparserraces
        Thread thread2 = new Thread(new Task("f1_race_schedule.xml"));

        //start thread 1
        thread1.start();
        try {
            //wait for thread 1 to finish
            thread1.join();
            System.out.println("Thread 1 has finished execution.");
            //check list has been populated
            System.out.println(driverList.size());

            //check if the list is not empty
            if (!driverList.isEmpty()) {
                //get size of list
                int dsize = driverList.size();
                //initialise arrays
                driverFName = new String[dsize];
                driverSName = new String[dsize];
                driverNationality = new String[dsize];
                driverDob = new String[dsize];

                //iterate through list
                for (int j = 0; j < dsize; j++) {
                    // fer tge drive object at index
                    Driver driver = driverList.get(j);
                    //testing purposes
                    if (driver == null) {
                        Log.e("MainActivity", "Driver at index " + j + " is null");
                        continue;
                    }
                    // get and store values
                    driverFName[j] = driver.getfName();
                    driverSName[j] = driver.getsName();
                    driverNationality[j] = driver.getNationality();
                    driverDob[j] = driver.getDob();
                    //testing
                    if (driverFName[j] == null || driverSName[j] == null) {
                        //testing purposes
                        Log.e("MainActivity", "Driver name at index " + j + " is null");
                    }
                    //testing purposes
                    //  Log.d("MainActivity", "FName: " + driverFName[j]);
                    //  Log.d("MainActivity", "SName: " + driverSName[j]);
                    // Log.d("MainActivity", "dob: " + driverDob[j]);
                   // Log.d("MainActivity", "nationality: " + driverNationality[j]);

                }
            }

            // check size of list
            System.out.println("Size of driverList: " + driverList.size());


            if (!standingsList.isEmpty()) {
                int size = standingsList.size();
                driverPos = new String[size];
                driverPoints = new String[size];
                driverWins = new String[size];

                for (int i = 0; i < size; i++) {
                    Standings standing = standingsList.get(i);
                    driverPos[i] = standing.getPosition();
                    driverPoints[i] = standing.getPoints();
                    driverWins[i] = standing.getWins();

                    //    Log.d("MainActivity", "Position: " + driverPos[i]);
                    // Log.d("MainActivity", "Points: " + driverPoints[i]);
                   // Log.d("MainActivity", "Wins: " + driverWins[i]);
                }
            }

            if (!constructorList.isEmpty()) {
                int csize = constructorList.size();
                constructorName = new String[csize];

                for (int l = 0; l < csize; l++) {
                    Constructor constructor = constructorList.get(l);
                    constructorName[l] = constructor.getCname();

                    Log.d("MainActivity", "Constructor: " + constructorName[l]);
                }
            }
//exception handling
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

        //start thread 2
            thread2.start();
        try{
            thread2.join();
            // Wait for thread2 to finish
            System.out.println("Thread 2 has finished execution.");
            System.out.println(racesList.size());

            if (!racesList.isEmpty()) {
                int rsize = racesList.size();
                round = new String[rsize];
                raceName = new String[rsize];
                date = new String[rsize];
                raceTime = new String[rsize];

                for (int k = 0; k < rsize; k++) {
                    Races race = racesList.get(k);
                    if (race == null) {
                        Log.e("MainActivity", "Driver at index " + k + " is null");
                        continue;
                    }
                    round[k] = race.getRound();
                    raceName[k] = race.getRace();
                    date[k] = race.getDate();
                    raceTime[k] = race.getTime();

                    // Log.d("MainActivity", "round: " + round[k]);
                    //    Log.d("MainActivity", "race: " + raceName[k]);
                    //    Log.d("MainActivity", "date: " + date[k]);
                   // Log.d("MainActivity", "time: " + raceTime[k]);
                }

            }

                if (!circuitList.isEmpty()) {
                    int cisize = circuitList.size();
                    circuitName = new String[cisize];
                    longitude = new String[cisize];
                    latitude = new String[cisize];

                    for (int m = 0; m < cisize; m++) {
                        Circuit circuit = circuitList.get(m);
                        circuitName[m] = circuit.getCircuitName();
                        longitude[m] = circuit.getLongitude();
                        latitude[m] = circuit.getLatitude();
                        // Log.d("MainActivity", "cn: " + circuitName[m]);
                       // Log.d("MainActivity", "long: " + longitude[m]);
                        Log.d("MainActivity", "lat: " + latitude[m]);

                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


    }



        //create and populate bundle
    @Override
    public void onClick(View v) {
        if(v == btnRaces){
            //create and populate bundle
            Bundle bundle = new Bundle();
            bundle.putStringArray("raceName", raceName);
            bundle.putStringArray("raceRound", round);
            bundle.putStringArray("raceDate", date);
            bundle.putStringArray("raceTime" , raceTime);
            bundle.putStringArray("circuitName", circuitName);
            bundle.putStringArray("longitude", longitude);
            bundle.putStringArray("latitude", latitude);
            //initialise and launch activity
            Intent startActivity2 = new Intent(MainActivity.this, MainActivity2.class);
            startActivity2.putExtras(bundle);
            startActivity(startActivity2);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
//task to access xml data from url sources
 /*   private class Task implements Runnable {
        private String url;
        private String result;

        public Task(String aurl) {
            url = aurl;
            result = "";
        }

        @Override
        public void run() {
            /*   URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine;

            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    result += inputLine;
                    Log.e("MyTag", inputLine);
                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception");
            }



//xml data to be parsed
            if (result.startsWith("<?xml")) {
                int i = result.indexOf(">");
                if (i != -1) {
                    result = result.substring(i + 1);
                }
                Log.e("MyTag - cleaned", result);
            } else {
                Log.e("MyTag", "Response is not XML.");
            }


            //parse data methods
            PullParserDriver.parseData(result);
            PullParserRaces.parseDataR(result);


        }
    } */
// boolean to determine internet connection - did not work, alert dialog was displayed when connected
  /*  public boolean isInternetConnected() {
        try (Socket socket = new Socket()) {
            // Connects to Google DNS (8.8.8.8) on port 53 with a 3-second timeout
            socket.connect(new InetSocketAddress("8.8.8.8", 53), 3000);
            return true; // Connection successful
        } catch (Exception e) {
            return false; // Connection failed
        }
    }

    public void checkConnection() {
        try {
            if (isInternetConnected()) {
                System.out.println("Connection Successful");
            } else {
                connectionDialog();
            }

        } catch (Exception e) {
            connectionDialog();
        }
    }
//dialog to alert no internet connection
    private void connectionDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("No Internet Connection")
                .setMessage("Please check your network settings and try again.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

*/

    // task to use xml file saved in assets folder due to ergast api being down
    private class Task implements Runnable {
        private String fileName;
        private String result;

        public Task(String fileName) {
            this.fileName = fileName;
            this.result = "";
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    result += mLine;
                }
            } catch (IOException e) {
                Log.e("MyTag", "IOException while reading " + fileName, e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("MyTag", "IOException while closing reader for " + fileName, e);
                    }
                }
            }

            if (result.startsWith("<?xml")) {
                int i = result.indexOf(">");
                if (i != -1) {
                    result = result.substring(i + 1);
                }
                Log.e("MyTag - cleaned", result);
            } else {
                Log.e("MyTag", "Response is not XML.");
            }

            // Parse data methods
            PullParserDriver.parseData(result);
            PullParserRaces.parseDataR(result);
        }
    }





    //replace runOnUiThread using handler class to schedule messages, used TimeUnit.MILLI for demo
    public void updateDateTime() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewDateTime.setText(DateTime.getCurrentDateTime());
                // Update every minute for demo purposes
                handler.postDelayed(this, TimeUnit.HOURS.toMillis(2));
            }
        }, 0);
    }
}
