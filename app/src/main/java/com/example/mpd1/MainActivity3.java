package com.example.mpd1;
//HAYLEY_BRIDGES_S2333704

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity3 extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView textViewTitle, textViewCN, textViewTime, textViewDateTime;
    String circuitName, raceName, slongitude, slatitude, time;
    ImageView image1, image2, image3;
    double latitude;
    double longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //set widgets
        textViewCN = findViewById(R.id.textViewCN);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewTime = findViewById(R.id.textViewTime);
        textViewDateTime = findViewById(R.id.textViewDateTime);
        textViewDateTime.setText(DateTime.getCurrentDateTime());
        image1 = findViewById(R.id.image1);
        image1.setImageResource(R.drawable.circuit);
        image3 = findViewById(R.id.image3);
        image3.setImageResource(R.drawable.clock);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
//retrieve bundle data and set variables
        circuitName = getIntent().getStringExtra("selectedCN");
        raceName = getIntent().getStringExtra("selectedRace");
        slongitude = getIntent().getStringExtra("selectedLong");
        slatitude = getIntent().getStringExtra("selectedLat");
        time = getIntent().getStringExtra("selectedTime");

        // convert latitude and longtide from string to double values
        if (slatitude != null && slongitude != null) {
            try {
                latitude = Double.parseDouble(slatitude);
                longitude = Double.parseDouble(slongitude);
            } catch (NumberFormatException e) {
                latitude = 0.0;
                longitude = 0.0;
                Log.e("MainActivity3", "Invalid latitude or longitude format", e);
            }
        } else {
            latitude = 0.0;
            longitude = 0.0;
        }
//set textviews
        textViewCN.setText(circuitName);
        textViewTitle.setText(raceName);
        textViewTime.setText(time);
    }
//calls onMapReady
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Set the zoom level for Google Map
        float zoomLevel = 10.0f;

        //use stored latitude and longitude to provide map point for the chosen list item
        LatLng cityLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(cityLocation).title("location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, zoomLevel));
    }
}
