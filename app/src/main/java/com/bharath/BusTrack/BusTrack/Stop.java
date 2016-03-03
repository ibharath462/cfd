package com.bharath.BusTrack.BusTrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        final MaterialSpinner refresh = (MaterialSpinner) findViewById(R.id.refresh);

        info.hoang8f.widget.FButton go;

        go=(info.hoang8f.widget.FButton)findViewById(R.id.primary_button);


        bus.setItems("T70","5B");
        stop.setItems("CMBT", "MMDA_Colony", "Jaffarkhanpet", "Cipet", "Guindy_Tvk_Estate", "Anna_University", "Adyar_BS", "Adyar_Depot", "Thiruvanmiyur");
        refresh.setItems("5","10","15","20","30","60");

        bus.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 1: {
                        stop.setItems("Mylapore", "Mandaveli", "AMS_Hospital", "Adayar", "Anna_University", "Saidapet", "T_Nagar","Testing");
                        break;
                    }
                }
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Stop.this,Passenger.class).putExtra("stop",stop.getText().toString()).putExtra("bus",bus.getText().toString()).putExtra("refresh",refresh.getText().toString());
                startActivity(i);
                finish();
            }
        });

    }
}
