package com.example.android.todolist.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.todolist.data.TaskRepository;

// COMPLETE (1) Make this class extend ViewModel ViewModelProvider.NewInstanceFactory
public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // COMPLETE (2) Add two member variables. One for the database and one for the taskId
    private final TaskRepository repository;
    private final int mTaskId;
    // COMPLETE (3) Initialize the member variables in the constructor with the parameters received

    public AddTaskViewModelFactory(final TaskRepository repository, final int taskId) {
        this.repository = repository;
        mTaskId = taskId;
    }

    // COMPLETE (4) Uncomment the following method
    // Note: This can be reused with minor modifications
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(repository, mTaskId);
    }
}
