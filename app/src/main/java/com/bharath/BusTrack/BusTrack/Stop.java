package com.bharath.BusTrack.BusTrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class Stop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);

        final MaterialSpinner stop = (MaterialSpinner) findViewById(R.id.stop);
        final MaterialSpinner bus = (MaterialSpinner) findViewById(R.id.bus);

        info.hoang8f.widget.FButton go;

        go=(info.hoang8f.widget.FButton)findViewById(R.id.primary_button);


        bus.setItems("T70","5B");
        stop.setItems("CMBT", "MMDA_Colony", "Jaffarkhanpet", "Cipet", "Guindy_Tvk_Estate", "Anna_University", "Adyar_BS", "Adyar_Depot", "Thiruvanmiyur");

        bus.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 1: {
                        stop.setItems("Mylapore", "Mandaveli", "AMS_Hospital", "Adayar", "Anna_University", "Saidapet", "T_Nagar");
                        break;
                    }
                }
            }
        });

    }
}
