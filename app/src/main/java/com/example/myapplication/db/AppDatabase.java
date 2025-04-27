package com.example.myapplication.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.daos.BookDao;
import com.example.myapplication.daos.UserDao;
import com.example.myapplication.models.Book;
import com.example.myapplication.models.User;


@Database(entities = {User.class, Book.class}, version = 1, exportSchema = false)
 public abstract  class AppDatabase extends RoomDatabase {
     public abstract UserDao userDao();
     public abstract BookDao bookDao();
}
