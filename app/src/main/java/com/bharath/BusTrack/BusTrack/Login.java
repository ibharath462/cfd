package com.bharath.BusTrack.BusTrack;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hanks.htextview.HTextViewType;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.UserAuthenticationCallback;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;

public class Login extends AppCompatActivity implements LocationListener{

    private MobileServiceClient mClient;

    String id=null;

    String busno=null;

    Double lat,lon;


    private MobileServiceTable<HelloAzure> bus;

    int lflag=0;

    int i=0;

    Handler mHandler=null;

    info.hoang8f.widget.FButton login,logout;

    com.hanks.htextview.HTextView text,text1,text2,current;

    LocationManager locationManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        mHandler=new Handler();

        login=(info.hoang8f.widget.FButton)findViewById(R.id.login);
        logout=(info.hoang8f.widget.FButton)findViewById(R.id.logout);

        text=(com.hanks.htextview.HTextView)findViewById(R.id.text);
        text1=(com.hanks.htextview.HTextView)findViewById(R.id.text1);
        text2=(com.hanks.htextview.HTextView)findViewById(R.id.text2);
        current=(com.hanks.htextview.HTextView)findViewById(R.id.current);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                authenticate();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClient.logout();
                lflag=0;
                Toast.makeText(getApplicationContext(), "Logged out successfull", Toast.LENGTH_SHORT).show();
            }
        });

        text.setAnimateType(HTextViewType.LINE);
        text1.setAnimateType(HTextViewType.LINE);
        text2.setAnimateType(HTextViewType.LINE);
        current.setAnimateType(HTextViewType.LINE);


        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }

        init();

        timer();


    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Please enable it!")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void init() {
        try {
            mClient = new MobileServiceClient(
                    "https://helloazure.azure-mobile.net/",
                    "hzTVtRFARRSUrTaBbRGokWxMsOSVvv39",
                    this);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    public void createTable()
    {
        bus=mClient.getTable(HelloAzure.class);
    }

    private void authenticate(){

        mClient.login(MobileServiceAuthenticationProvider.Google, new UserAuthenticationCallback() {
            @Override
            public void onCompleted(MobileServiceUser user, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {
                    Toast.makeText(getApplicationContext(), "Login Success " + user.getUserId(), Toast.LENGTH_SHORT).show();
                    createTable();
                    lflag = 1;
                    id = user.getUserId();
                    if (id.toString().equals("Google:113489993407040724482")) {

                        busno = "5B";

                    } else if (id.toString().equals("Google:111987629276466258577")) {

                        busno = "T70";
                    } else {
                        Toast.makeText(getApplicationContext(), "Please login with the anyone of the two ID's.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Login error ", Toast.LENGTH_SHORT).show();
                }

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
                        Thread.sleep(5000);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                i++;
                                if(lflag==2)
                                    updateAzure(lat,lon);
                                else
                                    Toast.makeText(getApplicationContext(), "Login or enable GPS or both! :p", Toast.LENGTH_SHORT).show();
                                if(i%2==0)
                                {
                                    text.animateText("Login for 5B:");
                                    text1.animateText("Username : bustrack5b@gmail.com");
                                    text2.animateText("Password : bustrack123");
                                }
                                else
                                {
                                    text.animateText("Login for T70:");
                                    text1.animateText("Username : bustrackt70b@gmail.com");
                                    text2.animateText("Password : bustrack123");

                                }
                                text.animate();
                                text1.animate();
                                text2.animate();
                                current.animate();

                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        }).start();


    }

    @Override
    public void onLocationChanged(Location location) {

        if(lflag==1) {

            lat = location.getLatitude();
            lon = location.getLongitude();
            lflag = 2;
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void updateAzure(Double lat,Double lon){

        final HelloAzure item=new HelloAzure();
        item.latitude=lat;
        item.longitude=lon;
        item.id=busno;

        current.animateText("Currently logged : " + busno);

        bus.update(item, new TableOperationCallback<HelloAzure>() {
            @Override
            public void onCompleted(HelloAzure entity, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {
                    Toast.makeText(getApplicationContext(), "Success ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error    " + exception.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
