package com.example.alanvan.roomwordsample.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alanvan.roomwordsample.AppExecutors;
import com.example.alanvan.roomwordsample.R;
import com.example.alanvan.roomwordsample.data.WordRepository;
import com.example.alanvan.roomwordsample.data.database.WordEntry;
import com.example.alanvan.roomwordsample.utilities.InjectorUtils;

import java.util.List;

public class AddWordActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;

    private WordRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        initViews();
        mRepository = InjectorUtils.provideRepository(this.getApplicationContext());
    }

    private void initViews() {
        mEditText = findViewById(R.id.add_word_et);
        mButton = findViewById(R.id.save_word_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    private void onSaveButtonClicked() {
        final String text = mEditText.getText().toString();

        final WordEntry word = new WordEntry(text);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!text.equals(""))
                    mRepository.insertWord(word);
                finish();
            }
        });
    }
}
