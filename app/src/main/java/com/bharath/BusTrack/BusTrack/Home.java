package com.bharath.BusTrack.BusTrack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

public class Home extends AppCompatActivity {

    HTextView title;

    ImageView help,quit;

    HTextView mode;

    int i=0;

    Handler mHandler=null;

    CardView v0,v1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        help=(ImageView)findViewById(R.id.help);
        quit=(ImageView)findViewById(R.id.quit);

        mHandler=new Handler();

        title=(HTextView)findViewById(R.id.title);
        mode=(HTextView)findViewById(R.id.text);

        v0=(CardView)findViewById(R.id.view0);
        v1=(CardView)findViewById(R.id.view1);

        Typeface future=Typeface.createFromAsset(this.getAssets(),"fonts/future.ttf");

        if(!isWiFiAvailable() || !isNetworkAvailable())
        {
            Toast.makeText(getApplicationContext(),"Network Not Available",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }


        //title.setTypeface(future);

        v0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Home.this,Login.class);
                startActivity(i);
                finish();
            }
        });

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Home.this,Stop.class);
                startActivity(i);
                finish();

            }
        });



        mode.setAnimateType(HTextViewType.LINE);
        timer();

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Home.this,Help.class);
                startActivity(i);
                finish();
            }
        });

quit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setMessage("Do you want to quit?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
});


    }

    public void timer()
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    try {
                        Thread.sleep(2000);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                i++;
                                if(i%2==0)
                                {
                                    mode.animateText("Driver mode to transmit.");
                                }
                                else if(i%3==0)
                                {
                                    mode.animateText("Choose a mode");
                                    title.animateText("BusTrack");
                                }
                                else
                                {
                                    mode.animateText("Passenger mode for users.");
                                }
                                mode.animate();
                                title.animate();
                               // driver.animate();

                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        }).start();


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isWiFiAvailable(){
        boolean i=false;
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            i=true;
        }
        return i;
    }


}
