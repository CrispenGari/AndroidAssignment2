package com.example.myapplication;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import com.google.gson.reflect.TypeToken;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.models.BookWithUser;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class BookActivity extends AppCompatActivity {

    private AppDatabase db;
    final String DB_NAME = "books-shop";
    String TAG = "Data";
    private ImageView bookCoverImage;
    private TextView bookTitle, bookAuthor, bookPrice, bookCopies, sellerName, sellerEmail, bankInfo;
    private Button addToBookmarksButton, removeFromBookMarksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.tertiary));

        bookCoverImage = findViewById(R.id.bookCoverImage);
        bookTitle = findViewById(R.id.bookTitle);
        bookAuthor = findViewById(R.id.bookAuthor);
        bookPrice = findViewById(R.id.bookPrice);
        bookCopies = findViewById(R.id.bookCopies);
        sellerName = findViewById(R.id.sellerName);
        sellerEmail = findViewById(R.id.sellerEmail);
        bankInfo = findViewById(R.id.bankInfo);
        addToBookmarksButton = findViewById(R.id.addToBookmarksButton);
        removeFromBookMarksButton = findViewById(R.id.removeFromBookMarksButton);



        db = Room.databaseBuilder(this, AppDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
        Intent intent = getIntent();
        Long bookId = Long.valueOf(Objects.requireNonNull(intent.getStringExtra("id")));

        if(isBookInBookmarks(bookId)){
            addToBookmarksButton.setVisibility(GONE);
            removeFromBookMarksButton.setVisibility(VISIBLE);
        }else{
            addToBookmarksButton.setVisibility(VISIBLE);
            removeFromBookMarksButton.setVisibility(GONE);
        }
        db.bookDao().one(bookId).observe(this, new Observer<BookWithUser>(){
            @Override
            public void onChanged(BookWithUser bookWithUser) {
                Log.d(TAG, "onChanged: " + bookWithUser);
                setTitle(bookWithUser.book.getTitle());
                populateBookDetails(bookWithUser);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @SuppressLint("SetTextI18n")
    private void populateBookDetails(BookWithUser bookWithUser) {
        bookTitle.setText(bookWithUser.book.getTitle());
        bookAuthor.setText("Author: " + bookWithUser.book.getAuthor());
        bookPrice.setText("Price: " + bookWithUser.book.getPrice());
        bookCopies.setText("Number of Copies: " + bookWithUser.book.getNumberOfCopies());
        sellerName.setText("Seller: " + bookWithUser.book.getSellerName());
        sellerEmail.setText("Email: " + bookWithUser.user.getEmail());
        bankInfo.setText("Bank Info: " + bookWithUser.book.getBankInfo());

        Glide.with(this)
                .load(bookWithUser.book.getCoverPage())
                .placeholder(Drawable.createFromPath("Book Cover")) // Placeholder image while loading
                .into(bookCoverImage);

        addToBookmarksButton.setOnClickListener(v -> {
            SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Gson gson = new Gson();
            String json = sharedPref.getString("bookmarks", null);
            Type type = new TypeToken<ArrayList<BookWithUser>>() {}.getType();
            ArrayList<BookWithUser> bookmarks;
            if (json == null) {
                bookmarks = new ArrayList<>();
            } else {
                bookmarks = gson.fromJson(json, type);
            }
            bookmarks.add(bookWithUser);
            String updatedJson = gson.toJson(bookmarks);
            editor.putString("bookmarks", updatedJson);
            editor.apply();
            addToBookmarksButton.setVisibility(View.GONE);  // Hide "Add to Bookmarks"
            removeFromBookMarksButton.setVisibility(View.VISIBLE);  // Show "Remove from Bookmarks"
            Toast.makeText(this, "Book added to bookmarks.", Toast.LENGTH_LONG).show();
        });
        removeFromBookMarksButton.setOnClickListener(v->{
            SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Gson gson = new Gson();
            String json = sharedPref.getString("bookmarks", null);
            Type type = new TypeToken<ArrayList<BookWithUser>>() {}.getType();
            ArrayList<BookWithUser> bookmarks;

            if (json == null) {
                bookmarks = new ArrayList<>();
            } else {
                bookmarks = gson.fromJson(json, type);
            }
            for (int i = 0; i < bookmarks.size(); i++) {
                if (Objects.equals(bookmarks.get(i).book.getId(), bookWithUser.book.getId())) {
                    bookmarks.remove(i);
                    break;
                }
            }
            String updatedJson = gson.toJson(bookmarks);
            editor.putString("bookmarks", updatedJson);
            editor.apply();
            addToBookmarksButton.setVisibility(View.VISIBLE);
            removeFromBookMarksButton.setVisibility(View.GONE);
            Toast.makeText(this, "Book removed from bookmarks.", Toast.LENGTH_LONG).show();
        });
    }

    private boolean isBookInBookmarks(Long bookId) {
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String json = sharedPref.getString("bookmarks", null);
        Gson gson = new Gson();
        BookWithUser book = null;
        if (json != null) {
            Type type = new TypeToken<ArrayList<BookWithUser>>() {}.getType();
            ArrayList<BookWithUser> bookmarks = gson.fromJson(json, type);
            for (BookWithUser bookmark : bookmarks) {
                if(Objects.equals(bookmark.book.getId(), bookId)){
                    book = bookmark;
                }
            }
        }
        return book != null;
    }
}