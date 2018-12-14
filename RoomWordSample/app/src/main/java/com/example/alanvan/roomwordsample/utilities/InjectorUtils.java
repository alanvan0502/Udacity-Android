package com.example.alanvan.roomwordsample.utilities;

import android.content.Context;

import com.example.alanvan.roomwordsample.data.WordRepository;

public class InjectorUtils {

    public static WordRepository provideRepository(Context context) {
        return WordRepository.getInstance(context);
    }
}
