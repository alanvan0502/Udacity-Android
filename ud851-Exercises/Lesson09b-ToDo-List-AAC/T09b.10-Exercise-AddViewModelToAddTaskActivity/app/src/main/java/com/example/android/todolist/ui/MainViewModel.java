package com.example.android.todolist.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.todolist.data.TaskRepository;
import com.example.android.todolist.data.database.TaskEntry;
import com.example.android.todolist.utilities.InjectorUtils;

import java.util.List;

class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private final TaskRepository repository;

    public MainViewModel(Application application) {
        super(application);
        repository = InjectorUtils.provideRepository(application.getApplicationContext());
    }

    LiveData<List<TaskEntry>> getTasks() {
        return repository.getAllTasks();
    }
}
