/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.udacity.example.quizexample;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.udacity.example.droidtermsprovider.DroidTermsExampleContract;

/**
 * Gets the data from the ContentProvider and shows a series of flash cards.
 */

public class MainActivity extends AppCompatActivity {

    // The current state of the app
    private int mCurrentState;

    private TextView mWordTv;
    private TextView mDefinitionTv;
    private int wordColumn;
    private int defColumn;

    private Cursor mData;

    private Button mButton;

    // This state is when the word definition is hidden and clicking the button will therefore
    // show the definition
    private final int STATE_HIDDEN = 0;

    // This state is when the word definition is shown and clicking the button will therefore
    // advance the app to the next word
    private final int STATE_SHOWN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the views
        mButton = (Button) findViewById(R.id.button_next);
        mWordTv = findViewById(R.id.text_view_word);
        mDefinitionTv = findViewById(R.id.text_view_definition);

        new ContentAsyncTask(this, mWordTv, mDefinitionTv).execute();
    }

    public void updateCursorData(Cursor data) {
        mData = data;

        mData.moveToFirst();
        wordColumn = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_WORD);
        defColumn = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_DEFINITION);
        mCurrentState = STATE_SHOWN;
    }

    /**
     * This is called from the layout when the button is clicked and switches between the
     * two app states.
     * @param view The view that was clicked
     */
    public void onButtonClick (View view){

        // Either show the definition of the current word, or if the definition is currently
        // showing, move to the next word.
        switch (mCurrentState) {
            case STATE_HIDDEN:
                showDefinition();
                break;
            case STATE_SHOWN:
                nextWord();
                break;
        }
    }

    public void nextWord () {
        if (mData != null) {
            if (!mData.moveToNext()) {
                mData.moveToFirst();
            }

            mDefinitionTv.setVisibility(View.INVISIBLE);
            mButton.setText(getString(R.string.show_definition));
            mWordTv.setText(mData.getString(wordColumn));
            mDefinitionTv.setText(mData.getString(defColumn));

            mCurrentState = STATE_HIDDEN;
        }

    }

    public void showDefinition () {
        if (mData != null) {
            mDefinitionTv.setVisibility(View.VISIBLE);
            mButton.setText(getString(R.string.next_word));
            mCurrentState = STATE_SHOWN;
        }
    }

    @Override
    protected void onDestroy() {
        if (mData != null)
            mData.close();
        super.onDestroy();
    }

    // TODO (1) Create AsyncTask with the following generic types <Void, Void, Cursor>
    // TODO (2) In the doInBackground method, write the code to access the DroidTermsExample
    // provider and return the Cursor object
    // TODO (4) In the onPostExecute method, store the Cursor object in mData

}
