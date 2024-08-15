package com.example.mpd1;
//HAYLEY_BRIDGES_S2333704
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RacesAdapter extends BaseAdapter {


        Context Context;
        String[] raceName, round, date, circuitName;

        LayoutInflater inflter;


    public RacesAdapter(android.content.Context applicationContext, String[] round, String[] raceName, String[] date, String[] time, String[] circuitName) {
        this.Context = applicationContext;
        this.raceName = raceName;
        this.round = round;
        this.date = date;
        this.circuitName = circuitName;
        inflter = (LayoutInflater.from(applicationContext));
    }


    @Override
        public int getCount() {return round.length;  }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view2, ViewGroup viewGroup) {
            view2 = inflter.inflate(R.layout.activity_listview2, null);
            TextView raceView = view2.findViewById(R.id.textViewRaceName);
            TextView roundView = view2.findViewById(R.id.textViewRound);
            TextView dateView = view2.findViewById(R.id.textViewDate);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date raceDate = null;
            try {
                raceDate = format.parse(date[i]);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            System.out.println(date);

            ImageView raceStatus = view2.findViewById(R.id.imageViewRaceStatus);
            raceView.setText(raceName[i]);
            roundView.setText(round[i]);
//            timeView.setText(time[i]);

            if (date[i] != null) {
                dateView.setText(date[i]);
            } else {
                dateView.setText("Unknown Date");
            }

            //determine the image used in regards to if the race has been completed or not
            if (raceDate != null) {
                if (raceDate.before(new Date())) {
                    //if the race has occurred
                    raceStatus.setImageResource(R.drawable.greentick);
                } else {
                    //if the race is upcoming
                    raceStatus.setImageResource(R.drawable.redline);
                }
            } else {
                //incase null value
                raceStatus.setImageResource(R.drawable.ic_launcher_background);
            }


                return view2;
            }
        }

