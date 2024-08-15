package com.example.mpd1;
//HAYLEY_BRIDGES_S2333704
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTime {


    public DateTime(){ }

    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
