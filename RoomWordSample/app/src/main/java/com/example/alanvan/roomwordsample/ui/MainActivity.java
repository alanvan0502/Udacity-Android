package com.example.alanvan.roomwordsample.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alanvan.roomwordsample.AppExecutors;
import com.example.alanvan.roomwordsample.R;
import com.example.alanvan.roomwordsample.data.WordRepository;
import com.example.alanvan.roomwordsample.data.database.WordEntry;
import com.example.alanvan.roomwordsample.utilities.InjectorUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private WordAdapter mAdapter;
    private WordRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the RecyclerView to its corresponding view
        // Member variables for the adapter and RecyclerView
        RecyclerView mRecyclerView = findViewById(R.id.recyclerViewWords);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new WordAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        // Add a touch helper to the RecyclerView to recognize when a user swipes to deleteWord
        // an activity
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        WordEntry word = mAdapter.getWords().get(position);
                        mRepository.deleteWord(word);
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "Main Activity Created");
        mRepository = InjectorUtils.provideRepository(this);
        setupViewModel();
    }

    private void setupViewModel() {
        WordViewModel viewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        viewModel.getAllWords().observe(this, new Observer<List<WordEntry>>() {
            @Override
            public void onChanged(@Nullable List<WordEntry> wordEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mAdapter.setWords(wordEntries);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mRepository.deleteAll();
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
