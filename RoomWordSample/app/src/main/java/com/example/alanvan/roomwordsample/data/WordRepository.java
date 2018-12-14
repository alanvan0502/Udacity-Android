package com.example.alanvan.roomwordsample.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.alanvan.roomwordsample.data.database.AppDatabase;
import com.example.alanvan.roomwordsample.data.database.WordDao;
import com.example.alanvan.roomwordsample.data.database.WordEntry;

import java.util.List;

public class WordRepository {
    private final WordDao mWordDao;
    private final LiveData<List<WordEntry>> mWords;
    private static final Object LOCK = new Object();
    private static WordRepository sInstance;

    private WordRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mWordDao = db.wordDao();
        mWords = mWordDao.loadAllWords();
    }

    public synchronized static WordRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new WordRepository(context);
            }
        }
        return sInstance;
    }

    public synchronized LiveData<List<WordEntry>> getAllWords() {
        return mWords;
    }

    public synchronized void insertWord(WordEntry word) {
        mWordDao.insertWord(word);
    }

    public synchronized void deleteAll() {
        mWordDao.deleteAll();
    }

    public void deleteWord(WordEntry word) {
        mWordDao.deleteWord(word);
    }
}
