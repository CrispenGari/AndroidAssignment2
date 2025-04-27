package com.example.myapplication.models;
import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class BookWithUser {
    @Embedded
    public Book book;  // Embedded Book entity
    @Relation(
            parentColumn = "userId",
            entityColumn = "id"
    )
    public User user;
}
