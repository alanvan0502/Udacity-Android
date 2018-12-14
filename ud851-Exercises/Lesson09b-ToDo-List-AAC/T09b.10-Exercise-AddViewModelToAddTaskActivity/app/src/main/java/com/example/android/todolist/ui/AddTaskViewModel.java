package com.example.android.todolist.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.todolist.data.TaskRepository;
import com.example.android.todolist.data.database.TaskEntry;

// COMPLETE (5) Make this class extend ViewModel
class AddTaskViewModel extends ViewModel {

    // COMPLETE (6) Add a task member variable for the TaskEntry object wrapped in a LiveData
    private final LiveData<TaskEntry> mTask;
    private final TaskRepository mRepository;
    // COMPLETE (8) Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId
    AddTaskViewModel(TaskRepository repository, int taskId) {
        this.mRepository = repository;
        mTask = mRepository.getTaskById(taskId);
    }

    // COMPLETE (7) Create a getter for the task variable
    LiveData<TaskEntry> getTask() {
        return mTask;
    }
}
