package com.example.myapplication.daos;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.myapplication.models.Book;
import com.example.myapplication.models.BookWithUser;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    public void insertBook(Book book);

    @Transaction
    @Query("SELECT * FROM books")
    LiveData<List<BookWithUser>> allBooks();


    @Transaction
    @Query("SELECT * FROM books WHERE id = :id")
    LiveData<BookWithUser> one(Long id);
}
