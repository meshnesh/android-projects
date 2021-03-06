package com.spot.e.espot_beta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by imaya on 3/9/16.
 */
public class MainAppIntro extends AppIntro {

    // Please DO NOT override onCreate. Use init.
    @Override
    public void init(Bundle savedInstanceState) {

        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(SampleSlide.newInstance(R.layout.first_fragment));
        addSlide(SampleSlide.newInstance(R.layout.second_fragment));
        addSlide(SampleSlide.newInstance(R.layout.third_fragment));
        addSlide(SampleSlide.newInstance(R.layout.fourth_fragment));

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
//        addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#75BE6A"));
        setSeparatorColor(Color.parseColor("#68b85c"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
//        setVibrate(true);
//        setVibrateIntensity(30);

    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        final Context context = this;
        Intent intent = new Intent(context,Main.class);
        startActivity(intent);
    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.

        final Context context = this;
        Intent intent = new Intent(context,Main.class);
        startActivity(intent);

    }

    @Override
    public void onSlideChanged() {
        // Do something when the slide changes.
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }

}
