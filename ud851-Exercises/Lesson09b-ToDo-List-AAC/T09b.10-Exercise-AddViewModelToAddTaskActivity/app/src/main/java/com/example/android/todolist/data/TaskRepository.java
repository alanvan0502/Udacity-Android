package com.example.android.todolist.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.android.todolist.data.database.AppDatabase;
import com.example.android.todolist.data.database.TaskDao;
import com.example.android.todolist.data.database.TaskEntry;

import java.util.List;

public class TaskRepository {

    private final TaskDao mTaskDao;
    private final LiveData<List<TaskEntry>> mTasks;
    private static final Object LOCK = new Object();
    private static TaskRepository sInstance;

    private TaskRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mTaskDao = db.taskDao();
        mTasks = mTaskDao.loadAllTasks();
    }

    public synchronized static TaskRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new TaskRepository(context);
            }
        }
        return sInstance;
    }

    public synchronized LiveData<TaskEntry> getTaskById(int taskId) {
        return mTaskDao.loadTaskById(taskId);
    }

    public synchronized LiveData<List<TaskEntry>> getAllTasks() {
        return mTasks;
    }

    public synchronized void deleteTask(TaskEntry task) {
        mTaskDao.deleteTask(task);
    }

    public synchronized void insertTask(TaskEntry task) {
        mTaskDao.insertTask(task);
    }

    public synchronized void updateTask(TaskEntry task) {
        mTaskDao.updateTask(task);
    }
}
