package com.example.myapplication.daos;

import androidx.room.*;
import com.example.myapplication.models.Book;

@Dao
public interface BookDao {
    @Insert
    public void insertBook(Book book);

}
