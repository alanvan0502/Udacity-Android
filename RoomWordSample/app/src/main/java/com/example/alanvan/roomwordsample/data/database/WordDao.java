package com.example.alanvan.roomwordsample.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WordDao {
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<WordEntry>> loadAllWords();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWord(WordEntry wordEntry);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Delete
    void deleteWord(WordEntry word);
}
