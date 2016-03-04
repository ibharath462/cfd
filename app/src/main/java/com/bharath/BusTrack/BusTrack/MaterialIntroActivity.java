package com.bharath.BusTrack.BusTrack;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.view.Window;
import android.view.WindowManager;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MaterialIntroActivity extends IntroActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        MultiDex.install(this);

        super.onCreate(savedInstanceState);


        addSlide(new SimpleSlide.Builder()
                .title(R.string.oneTitle)
                .description(R.string.onedes)
                .image(R.drawable.five)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_metaphor)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title(R.string.twoTitle)
                .description(R.string.twodes)
                .image(R.drawable.one)
                .background(R.color.color_material_bold)
                .backgroundDark(R.color.color_dark_material_bold)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title(R.string.threeTitle)
                .description(R.string.threedes)
                .image(R.drawable.two)
                .background(R.color.color_material_motion)
                .backgroundDark(R.color.color_dark_material_motion)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title(R.string.fourTitle)
                .description(R.string.fourdes)
                .image(R.drawable.three)
                .background(R.color.color_material_shadow)
                .backgroundDark(R.color.color_dark_material_shadow)
                .build());



    }

    @Override
    protected void onDestroy() {
        Intent i=new Intent(MaterialIntroActivity.this,Home.class);
        startActivity(i);
        finish();
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
