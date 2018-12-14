package com.example.alanvan.roomwordsample.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.alanvan.roomwordsample.data.WordRepository;
import com.example.alanvan.roomwordsample.data.database.WordEntry;
import com.example.alanvan.roomwordsample.utilities.InjectorUtils;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private final WordRepository repository;
    private final LiveData<List<WordEntry>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        repository = InjectorUtils.provideRepository(application.getApplicationContext());
        mAllWords = repository.getAllWords();
    }

    LiveData<List<WordEntry>> getAllWords() {
        return mAllWords;
    }
}