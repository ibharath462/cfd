package com.bharath.BusTrack.BusTrack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

public class Stop extends AppCompatActivity {

    MaterialSpinner stop;
    MaterialSpinner bus;
    MaterialSpinner refresh;
    String s="None";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);

        stop = (MaterialSpinner) findViewById(R.id.stop);
        bus = (MaterialSpinner) findViewById(R.id.bus);
        refresh = (MaterialSpinner) findViewById(R.id.refresh);




        final info.hoang8f.widget.FButton go;

        go=(info.hoang8f.widget.FButton)findViewById(R.id.primary_button);


        bus.setItems("T70","5B");
        stop.setItems("CMBT", "MMDA_Colony", "Jaffarkhanpet", "Cipet", "Guindy_Tvk_Estate", "Anna_University", "Adyar_BS", "Adyar_Depot", "Thiruvanmiyur");
        refresh.setItems("5","10","15","20","30","60");

        bus.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 1: {
                        stop.setItems("Mylapore", "Mandaveli", "AMS_Hospital", "Adayar", "Anna_University", "Saidapet", "T_Nagar","Testing","Custom");
                        break;
                    }
                }
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(stop.getText().equals("Custom"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Stop.this);
                    builder.setTitle("Custom Place");

                    final EditText input = new EditText(Stop.this);

                    final String[] m_Text = new String[1];


// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                            input.setInputType(InputType.TYPE_CLASS_TEXT);
                            input.setHint("Latitude,Longitude.Eg:11.082215,76.940895");


                    builder.setView(input);

// Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text[0] = input.getText().toString();
                            s=m_Text[0];
                            go();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(),"Please enter valid coordinates",Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.show();
                }
                else{

                    go();
                }

            }
        });

    }

    public void go(){
        Intent i =new Intent(Stop.this,Passenger.class).putExtra("stop",stop.getText().toString()).putExtra("bus",bus.getText().toString()).putExtra("refresh",refresh.getText().toString()).putExtra("custom",s);
        startActivity(i);
        finish();
    }
}
