package com.example.mpd1;
//HAYLEY_BRIDGES_S2333704

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    Context mContext;
    String[] driverPos;
    String[] driverPoints;
    String[] driverFName;
    String[] driverSName;
    String[] driverWins;
    String[] driverNationality;
    String[] driverDob;
    String[] constructorName;
    LayoutInflater inflater;


    public CustomAdapter(Context applicationContext, String[] driverPos, String[] driverPoints, String[] driverWins, String[] driverFName, String[] driverSName, String[] driverDob, String[] driverNationality, String[] constructorName) {
        this.mContext = applicationContext;
        this.driverPos = driverPos;
        this.driverPoints = driverPoints;
        this.driverFName = driverFName;
        this.driverSName = driverSName;
        this.driverWins = driverWins;
        this.driverDob = driverDob;
        this.driverNationality = driverNationality;
        this.constructorName = constructorName;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return driverPos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_listview, null);
        TextView posView = view.findViewById(R.id.textViewPos);
        TextView winsView = view.findViewById(R.id.textViewWins);
        TextView pointsView = view.findViewById(R.id.textViewPoints);
        TextView fNameView = view.findViewById(R.id.textViewFName);
        TextView sNameView = view.findViewById(R.id.textViewSName);

        posView.setText(driverPos[i]);
        pointsView.setText(driverPoints[i]);
        winsView.setText(driverWins[i]);
        fNameView.setText(driverFName[i]);
        sNameView.setText(driverSName[i]);

        return view;
    }
}
