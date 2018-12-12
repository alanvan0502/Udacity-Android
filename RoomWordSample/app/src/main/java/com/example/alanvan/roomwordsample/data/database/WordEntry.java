package com.example.alanvan.roomwordsample.data.database;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "word_table")
public class WordEntry {

    @PrimaryKey
    @ColumnInfo(name = "word")
    private String mWord;

    public WordEntry(String word) {
        mWord = word;
    }

    public String getWord() {
        return mWord;
    }

}