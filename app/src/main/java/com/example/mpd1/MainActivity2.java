package com.example.mpd1;
//HAYLEY_BRIDGES_S2333704
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//activity 2
public class MainActivity2 extends AppCompatActivity implements AdapterView.OnClickListener, AdapterView.OnItemClickListener {

    //declare variables
    String[] round, raceName, date, time, circuitName, longitude, latitude;
    ListView listview1;
    Button btnDrivers;
    ImageView image1, image2, image3, image4, image5;
    TextView textViewDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewDateTime = findViewById(R.id.textViewDateTime);
        textViewDateTime.setText(DateTime.getCurrentDateTime());
        btnDrivers = findViewById(R.id.BtnDrivers);
        btnDrivers.setOnClickListener(this);
        listview1 = findViewById(R.id.list1);
        listview1.setOnItemClickListener(this);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        raceName = bundle.getStringArray("raceName");
        round = bundle.getStringArray("raceRound");
        date = bundle.getStringArray("raceDate");
        circuitName = bundle.getStringArray("circuitName");
        time = bundle.getStringArray("raceTime");
        longitude = bundle.getStringArray("longitude");
        latitude = bundle.getStringArray("latitude");
        RacesAdapter raceAdapter;
        raceAdapter = new RacesAdapter(getApplicationContext(), round, raceName, date, time, circuitName);
        listview1.setAdapter(raceAdapter);

        image1 = findViewById(R.id.image1);
        image1.setImageResource(R.drawable.steeringwheel);
        image1 = findViewById(R.id.image2);
        image1.setImageResource(R.drawable.flag);
        image3 = findViewById(R.id.image3);
        image3.setImageResource(R.drawable.calendar);



    }
    @Override
    public void onClick(View view) {
        //launch main activity
        if (view == btnDrivers) {
            Intent startActivity = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(startActivity);
        }
    }

    //on item click, stores specific information to be passed onto activity 3, using [position] to handle accordingly
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedLong = longitude[position];
        String selectedLat = latitude[position];
        String selectedCircuitName = circuitName[position];
        String selectedRace = raceName[position];
        String selectedTime = time[position];
        Bundle bundle2 = getBundle2(position, selectedCircuitName, selectedRace, selectedLat, selectedLong, selectedTime);
        Intent startActivity3 = new Intent(MainActivity2.this, MainActivity3.class);
        startActivity3.putExtras(bundle2);
        startActivity(startActivity3);


    }
//method to retrieve bundle
    private Bundle getBundle2(int position, String selectedCircuitName, String selectedRace, String selectedLat, String selectedLong, String selectedTime) {

        Bundle bundle2 = new Bundle();
        bundle2.putString("selectedCN", selectedCircuitName);
        bundle2.putString("selectedRace", selectedRace);
        bundle2.putString("selectedLong" , selectedLong);
        bundle2.putString("selectedLat", selectedLat);
        bundle2.putString("selectedTime", selectedTime);

        return bundle2;
    }



}