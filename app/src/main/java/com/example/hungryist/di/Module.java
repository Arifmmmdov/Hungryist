package com.example.hungryist.di;

import android.app.Activity;

import com.example.hungryist.MyApp;
import com.example.hungryist.activity.intro.IntroActivity;

import dagger.Provides;

@dagger.Module
public class Module {


    @Provides
    public Activity provideActivity() {
        return new IntroActivity();
    }

    @Provides
    public String provideString() {
        return "Arif";
    }
}
