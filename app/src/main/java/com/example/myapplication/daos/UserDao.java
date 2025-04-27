package com.example.myapplication.daos;
import androidx.room.*;
import com.example.myapplication.models.User;
@Dao
public interface UserDao {
    @Insert
    public void insertUser(User user);

    @Query("SELECT * FROM users WHERE email LIKE :email")
    public User getUserByEmail(String email);
}
