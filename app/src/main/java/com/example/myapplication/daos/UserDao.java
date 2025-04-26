package com.example.myapplication.daos;
import androidx.room.*;
import com.example.myapplication.models.User;
@Dao
public interface UserDao {
    @Insert
    public void insertUser(User user);
}
