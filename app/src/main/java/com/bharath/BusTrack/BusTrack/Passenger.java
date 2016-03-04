package com.bharath.BusTrack.BusTrack;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hanks.htextview.HTextViewType;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.UserAuthenticationCallback;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class Passenger extends AppCompatActivity implements OnMapReadyCallback {

    private MobileServiceClient mClient;


    enum stops5A{

        Mylapore, Mandaveli, AMS_Hospital, Adayar, Anna_University,Saidapet,T_Nagar,Testing,Custom;

    }

    enum stopsT70{

        CMBT,MMDA_Colony,Jaffarkhanpet,Cipet,Guindy_Tvk_Estate,Anna_University,Adyar_BS,Adyar_Depot,Thiruvanmiyur;
    }


    stops5A st5A=null;
    stopsT70 stt70=null;

    GoogleMap t=null;

    double latSTOP,lonSTOP;

    TextView durr;

    Double lats=13.026611111111112,longs=80.26583333333333;

    String refresh,buss,stop,id,custom;

    ProgressDialog progressDialog;

    int rr;

    Handler mHandler = null;

    EditText input=null;

    private MobileServiceTable<HelloAzure> bus;

    info.hoang8f.widget.FButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);




        refresh=getIntent().getStringExtra("refresh");
        buss=getIntent().getStringExtra("bus");
        stop=getIntent().getStringExtra("stop");
        custom=getIntent().getStringExtra("custom");
        durr=(TextView)findViewById(R.id.durr);


        if(buss.equals("5B")){
            id="5B";
            st5A=stops5A.valueOf(stop);
        }
        else
        {
            id="T70";
            stt70=stopsT70.valueOf(stop);
        }

        progressDialog = new ProgressDialog(Passenger.this);
        progressDialog.setMessage("Getting the estimated time");
        progressDialog.show();


        String []destinations5A={"13.03425,80.26802777777777",
                "13.026611111111112,80.26583333333333",
                "13.017805555555556,80.26063888888889",
                "13.006805555555555,80.25558333333333",
                "13.007361111111111,80.23672222222223",
                "13.020861111111111,80.22522222222223",
                "13.0345,80.23027777777779",
                "11.152824,76.944300"};

        String []destinationsT70={"13.069162,80.204624",
                "13.065174,80.211393",
                "13.029213,80.208849",
                "13.015478,80.204942",
                "13.009941,80.211276",
                "13.006805555555555,80.25558333333333",
                "13.006805555555555,80.25558333333333",
                "12.997715,80.256733",
                "12.986826,80.258997"};

        String destination=null;

        String destination5A=null;
        String destinationT70=null;




        if(st5A!=null && stt70==null) {

            switch (st5A) {
                case Mylapore: {
                    destination5A = destinations5A[0];
                    break;
                }
                case Mandaveli: {
                    destination5A = destinations5A[1];
                    break;
                }
                case AMS_Hospital: {
                    destination5A = destinations5A[2];
                    break;
                }
                case Adayar: {
                    destination5A = destinations5A[3];
                    break;
                }
                case Anna_University: {
                    destination5A = destinations5A[4];
                    break;
                }
                case Saidapet: {
                    destination5A = destinations5A[5];
                    break;
                }
                case T_Nagar: {
                    destination5A = destinations5A[6];
                    break;
                }
                case Testing: {
                    destination5A = destinations5A[7];
                    break;
                }
                case Custom: {
                    destination5A = custom;
                    break;
                }

            }

            destination=destination5A;
        }

        else if(stt70!=null && st5A==null)
        {
            switch (stt70)
            {
                case CMBT:
                {
                    destinationT70=destinationsT70[0];
                    break;
                }
                case MMDA_Colony:
                {
                    destinationT70=destinationsT70[1];
                    break;
                }
                case Jaffarkhanpet:
                {
                    destinationT70=destinationsT70[2];
                    break;
                }
                case Cipet:
                {
                    destinationT70=destinationsT70[3];
                    break;
                }
                case Guindy_Tvk_Estate:
                {
                    destinationT70=destinationsT70[4];
                    break;
                }
                case Anna_University:
                {
                    destinationT70=destinationsT70[5];
                    break;
                }
                case Adyar_BS:
                {
                    destinationT70=destinationsT70[6];
                    break;
                }
                case Adyar_Depot:
                {
                    destinationT70=destinationsT70[7];
                    break;
                }
                case Thiruvanmiyur:
                {
                    destinationT70=destinationsT70[8];
                    break;
                }
            }
            destination=destinationT70;
        }



        latSTOP=Double.parseDouble(destination.split(",")[0]);
        lonSTOP=Double.parseDouble(destination.split(",")[1]);


        rr=Integer.parseInt(refresh);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        back=(info.hoang8f.widget.FButton)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Passenger.this, Stop.class);
                startActivity(i);
                finish();
            }
        });

        init();

        mHandler = new Handler();



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        t=googleMap;

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

        mClient.login(MobileServiceAuthenticationProvider.Google, new UserAuthenticationCallback() {
            @Override
            public void onCompleted(MobileServiceUser user, Exception exception, ServiceFilterResponse response) {

                if(exception==null)
                {
                   // Toast.makeText(getApplicationContext(), "Login Success ", Toast.LENGTH_SHORT).show();
                    timer();

                }
                else
                {
                   // Toast.makeText(getApplicationContext(), "Login error ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        bus=mClient.getTable(HelloAzure.class);

    }

    public void timer() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    try {
                        Thread.sleep(rr*1000);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                getAzure();

                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        }).start();


    }

    public void getAzure(){

        bus.where().field("id").eq(id).execute(new TableQueryCallback<HelloAzure>() {
            @Override
            public void onCompleted(List<HelloAzure> result, int count, Exception exception, ServiceFilterResponse response) {

                if (exception != null) {
                    //Toast.makeText(getApplicationContext(), "Error " + exception.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "Success ", Toast.LENGTH_SHORT).show();
                    for (HelloAzure t : result) {

                        lats = t.latitude;
                        longs = t.longitude;

                    }
                }

                busPos(lats,longs,t,latSTOP,lonSTOP,id);
                JSONAsyncTask JSONfetcher = new JSONAsyncTask();
                JSONfetcher.execute(lats.toString(), longs.toString(), "");

            }
        });
    }

    public void busPos(Double lat,Double lon,GoogleMap m,Double x,Double y,String z)
    {
        m.clear();
        LatLng sydney = new LatLng(lat, lon);

        Marker bus = m.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lon))
                .title(z)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.bus1)));

        Marker sss = m.addMarker(new MarkerOptions()
                .position(new LatLng(x,y))
                .title("Stop")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.stop)));


        sss.showInfoWindow();
        bus.showInfoWindow();


        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(sydney, 17);
        m.animateCamera(yourLocation);

    }


    class JSONAsyncTask extends AsyncTask<String,String,String> {




        @Override
        protected void onPreExecute() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    durr.setTextColor(Color.BLACK);
                }
            });
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    JSONAsyncTask.this.cancel(true);
                }
            });

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {

            String data=null;

            String []destinations5A={"13.03425,80.26802777777777",
                    "13.026611111111112,80.26583333333333",
                    "13.017805555555556,80.26063888888889",
                    "13.006805555555555,80.25558333333333",
                    "13.007361111111111,80.23672222222223",
                    "13.020861111111111,80.22522222222223",
                    "13.0345,80.23027777777779",
                    "11.152824,76.944300"};

            String []destinationsT70={"13.069162,80.204624",
                    "13.065174,80.211393",
                    "13.029213,80.208849",
                    "13.015478,80.204942",
                    "13.009941,80.211276",
                    "13.006805555555555,80.25558333333333",
                    "13.008289,80.233449",
                    "12.997715,80.256733",
                    "12.986826,80.258997"};

            String destination=null;

            String destination5A=null;
            String destinationT70=null;

            if(!isNetworkAvailable() && !isWiFiAvailable())
            {
                JSONAsyncTask.this.cancel(true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Please activate network connection for updates.",Toast.LENGTH_SHORT).show();
                    }
                });


            }



            if(st5A!=null && stt70==null) {

                switch (st5A) {
                    case Mylapore: {
                        destination5A = destinations5A[0];
                        break;
                    }
                    case Mandaveli: {
                        destination5A = destinations5A[1];
                        break;
                    }
                    case AMS_Hospital: {
                        destination5A = destinations5A[2];
                        break;
                    }
                    case Adayar: {
                        destination5A = destinations5A[3];
                        break;
                    }
                    case Anna_University: {
                        destination5A = destinations5A[4];
                        break;
                    }
                    case Saidapet: {
                        destination5A = destinations5A[5];
                        break;
                    }
                    case T_Nagar: {
                        destination5A = destinations5A[6];
                        break;
                    }
                    case Testing: {
                        destination5A = destinations5A[7];
                        break;
                    }
                    case Custom: {
                        destination5A = custom;
                        break;
                    }

                }

                destination=destination5A;
            }

            else if(stt70!=null && st5A==null)
            {
                switch (stt70)
                {
                    case CMBT:
                    {
                        destinationT70=destinationsT70[0];
                        break;
                    }
                    case MMDA_Colony:
                    {
                        destinationT70=destinationsT70[1];
                        break;
                    }
                    case Jaffarkhanpet:
                    {
                        destinationT70=destinationsT70[2];
                        break;
                    }
                    case Cipet:
                    {
                        destinationT70=destinationsT70[3];
                        break;
                    }
                    case Guindy_Tvk_Estate:
                    {
                        destinationT70=destinationsT70[4];
                        break;
                    }
                    case Anna_University:
                    {
                        destinationT70=destinationsT70[5];
                        break;
                    }
                    case Adyar_BS:
                    {
                        destinationT70=destinationsT70[6];
                        break;
                    }
                    case Adyar_Depot:
                    {
                        destinationT70=destinationsT70[7];
                        break;
                    }
                    case Thiruvanmiyur:
                    {
                        destinationT70=destinationsT70[8];
                        break;
                    }
                }
                destination=destinationT70;
            }

            try {

                String origin="="+urls[0]+","+urls[1];

                String URL="https://maps.googleapis.com/maps/api/directions/json?origin"+origin+"&destination="+destination+"&sensor=false&mode=driving";


                HttpGet httppost = new HttpGet(URL);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);


                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toString(entity);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        protected void onPostExecute(final String result) {

            JSONObject jsonObject;
            String duration=null;
            Integer time=0;
            try {
                jsonObject = new JSONObject(result);

                JSONArray routesArray = jsonObject.getJSONArray("routes");

                JSONObject route = routesArray.getJSONObject(0);

                JSONArray legs = route.getJSONArray("legs");

                JSONObject leg = legs.getJSONObject(0);


                for(int i=0;i<leg.length();i++){

                    //time+=Integer.parseInt(legs.getJSONObject(i).toString());
                    JSONObject t=legs.getJSONObject(i);
                    JSONObject durationObject = t.getJSONObject("duration");
                    duration = durationObject.getString("value");

                    time+=Integer.parseInt(duration);


                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            //this.progressDialog.dismiss();

            int sec;


            final  float t=(float)time/60;

            time=time/60;

            sec=(int)(60*(t-time));

            final Integer finalDuration = time;

            final Integer fsec=sec;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    // Toast.makeText(getApplicationContext(), "Duration:" + finalDuration +" mins and"+" "+fsec+" seconds", Toast.LENGTH_LONG).show();
                    durr.setText("Estd.Time of arrival:" + finalDuration+" mins and "+fsec+" seconds");
                    durr.setTextColor(Color.RED);
                    progressDialog.dismiss();
                }
            });

        }
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
