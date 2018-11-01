package com.example.ernestoramos.apptrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Splash extends AppCompatActivity implements View.OnClickListener {

    ImageView imgLogo;
    Button btnComenzar;
    Animation uptdown,downtop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btnComenzar = findViewById(R.id.btnComenzar);
        imgLogo = findViewById(R.id.imgLogoSplash);
        uptdown = AnimationUtils.loadAnimation(this,R.anim.uptdown);
        downtop = AnimationUtils.loadAnimation(this,R.anim.downtop);

        imgLogo.setAnimation(uptdown);
        btnComenzar.setAnimation(downtop);
        btnComenzar.setOnClickListener(this);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnComenzar:
                Intent act = new Intent(this,MainActivity.class);
                startActivity(act);
                break;
        }
    }
}
