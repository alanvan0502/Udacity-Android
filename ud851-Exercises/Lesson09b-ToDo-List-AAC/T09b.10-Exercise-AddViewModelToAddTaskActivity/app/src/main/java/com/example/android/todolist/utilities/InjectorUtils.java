package com.example.android.todolist.utilities;

import android.content.Context;

import com.example.android.todolist.ui.AddTaskViewModelFactory;
import com.example.android.todolist.data.TaskRepository;

public class InjectorUtils {

    public static TaskRepository provideRepository(Context context) {
        return TaskRepository.getInstance(context);
    }

    public static AddTaskViewModelFactory
    provideAddTaskViewModelFactory(Context context, int taskId) {
        TaskRepository repository = provideRepository(context);
        return new AddTaskViewModelFactory(repository, taskId);
    }
}